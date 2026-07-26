package com.community.demo.users;

import com.community.demo.auth.temp.dto.LoginResponseDto;
import com.community.demo.exception.BusinessException;
import com.community.demo.exception.NotFoundException;
import com.community.demo.files.File;
import com.community.demo.files.FileRepository;
import com.community.demo.files.FileUtil;
import com.community.demo.users.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {

        if (request.getEmail().isEmpty() || !request.getEmail().matches("^[A-Za-z._%+-]+@[A-Za-z.-]+\\.[A-Za-z]{2,}$")) {
            throw new BusinessException("email validation failed", null);
        } else if (request.getPassword().isEmpty() || !request.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,20}$")) {
            throw new BusinessException("password validation failed", null);
        } else if (request.getNickname().isEmpty() || !request.getNickname().matches("^\\S+$")) {
            throw new BusinessException("nickname validation failed", null);
        }

        File profileImage = resolveProfileImage(request.getProfileImageUrl());
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getNickname(), profileImage, request.getEmail(), encodedPassword);
        User savedUser = userRepository.save(user);

        return UserResponseDto.of(savedUser.getId());
    }

    public ReadUserResponseDto readUser(Long userId) {
        User user = findUser(userId);

        return ReadUserResponseDto.fromEntity(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = findUser(userId);
        user.signout();
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, UpdateUserRequestDto request) {
        User user = findUser(userId);
        user.updateNickname(request.getNewNickname());
        applyProfileImage(user, request.getNewProfileImageUrl());
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequestDto request) {
        User user = findUser(userId);
        String newPassword = passwordEncoder.encode(request.getNewPassword());
        user.updatePassword(newPassword);
        userRepository.save(user);
    }

    public UserInfoDto getUser(String email) {
        User user = userRepository.findByIsDeletedFalseAndEmail(email).orElseThrow();
        return UserInfoDto.from(user);
    }

    private File resolveProfileImage(String profileImageUrl) {
        if (profileImageUrl == null || profileImageUrl.isBlank()) {
            return null;
        }

        String relativePath = extractPathFromUrl(profileImageUrl);

        return fileRepository.findByFilePath(relativePath)
                .orElseThrow(() -> new NotFoundException("profile_image_not_found"));
    }

    private String extractPathFromUrl(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();

            return path != null ? path : url;
        } catch (URISyntaxException exception) {
            return url;
        }
    }

    private void applyProfileImage(User user, String requestedPath) {
        if (requestedPath == null) {
            user.updateProfileImage(null);
            return;
        }

        String relativePath = FileUtil.extractPathFromUrl(requestedPath);

        String currentPath = Optional.ofNullable(user.getProfileImage())
                .map(File::getFilePath)
                .orElse(null);

        if (relativePath.equals(currentPath)) {
            return;
        }

        File newProfileImage = fileRepository.findByFilePath(relativePath)
                .orElseThrow(() -> new NotFoundException("profile_image_not_found"));

        user.updateProfileImage(newProfileImage);
    }

    public void isValidEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("email_duplicate", HttpStatus.CONFLICT);
        }
    }

    public void isValidNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new BusinessException("nickname_duplicate", HttpStatus.CONFLICT);
        }
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user_not_found"));
    }
}
