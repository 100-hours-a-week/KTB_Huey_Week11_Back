package com.community.demo.users;

import com.community.demo.auth.temp.Auth;
import com.community.demo.auth.temp.AuthRepository;
import com.community.demo.exception.NotFoundException;
import com.community.demo.users.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        User user = new User(request.getNickname(), request.getProfileImage());
        userRepository.save(user);

        Auth auth = new Auth(user, request.getEmail(), request.getPassword());
        authRepository.save(auth);

        return UserResponseDto.fromEntity(user);
    }

    public ReadUserResponseDto readUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("not_found"));
        Auth auth = authRepository.findById(user).orElseThrow(() -> new NotFoundException("not_found"));

        return ReadUserResponseDto.fromEntity(user, auth);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("not_found"));
        user.signout();
        userRepository.save(user);
    }

    @Transactional
    public void updateNickname(Long userId, UpdateNicknameRequestDto request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("not_found"));
        user.updateNickname(request.getNewNickname());
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequestDto request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("not_found"));
        Auth auth = authRepository.findById(user).orElseThrow(() -> new NotFoundException("not_found"));
        auth.updatePassword(request.getNewPassword());
        authRepository.save(auth);
    }
}
