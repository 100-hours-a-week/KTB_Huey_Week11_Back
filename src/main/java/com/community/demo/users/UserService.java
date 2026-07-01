package com.community.demo.users;

import com.community.demo.auth.temp.Auth;
import com.community.demo.auth.temp.AuthRepository;
import com.community.demo.exception.NotFoundException;
import com.community.demo.files.File;
import com.community.demo.files.FileRepository;
import com.community.demo.files.FileUtil;
import com.community.demo.users.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final FileRepository fileRepository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        File profileImage = resolveProfileImage(request.getProfileImageUrl());
        User user = new User(request.getNickname(), profileImage);
        User savedUser = userRepository.save(user);

        Auth auth = new Auth(user, request.getEmail(), request.getPassword());
        authRepository.save(auth);

        return UserResponseDto.of(savedUser.getId());
    }

    public ReadUserResponseDto readUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user_not_found"));
        Auth auth = authRepository.findById(user).orElseThrow(() -> new NotFoundException("not_found"));

        return ReadUserResponseDto.fromEntity(user, auth);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user_not_found"));
        user.signout();
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, UpdateUserRequestDto request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user_not_found"));
        user.updateNickname(request.getNewNickname());
        applyProfileImage(user, request.getNewProfileImageUrl());
    }

    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequestDto request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user_not_found"));
        Auth auth = authRepository.findById(user).orElseThrow(() -> new NotFoundException("not_found"));
        auth.updatePassword(request.getNewPassword());
        authRepository.save(auth);
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
}
