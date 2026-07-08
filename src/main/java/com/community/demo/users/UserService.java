package com.community.demo.users;

import com.community.demo.auth.temp.Auth;
import com.community.demo.auth.temp.dto.LoginRequestDto;
import com.community.demo.auth.temp.dto.LoginResponseDto;
import com.community.demo.exception.BadRequestException;
import com.community.demo.exception.NotFoundException;
import com.community.demo.files.File;
import com.community.demo.files.FileRepository;
import com.community.demo.files.FileUtil;
import com.community.demo.users.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        File profileImage = resolveProfileImage(request.getProfileImageUrl());
        User user = new User(request.getNickname(), profileImage, request.getEmail(), request.getPassword());
        User savedUser = userRepository.save(user);

        return UserResponseDto.of(savedUser.getId());
    }

    public ReadUserResponseDto readUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user_not_found"));

        return ReadUserResponseDto.fromEntity(user);
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
        user.updatePassword(request.getNewPassword());
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto request, HttpServletRequest httpRequest) {
        User user = userRepository.findByIsDeletedFalseAndEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("user_not_found"));
        //log.info("id: " + user.getId());
        if (!request.getPassword().equals(user.getPassword())) {
            throw new BadRequestException("bad_request");
        }

        //세션 생성
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        session = httpRequest.getSession(true);
        log.info(session.getId());

        //세션에 userId 주입
        session.setAttribute("loginUser", user.getId());
        session.setMaxInactiveInterval(1800);

        return LoginResponseDto.of(user.getId(), user.getEmail(), user.getNickname(), user.getProfileImage().getFilePath());
    }

    public void logout() {

    }

    public Boolean authenticate(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user_not_found"));
        return true;
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
