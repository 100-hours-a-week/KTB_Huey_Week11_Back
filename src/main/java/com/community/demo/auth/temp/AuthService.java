package com.community.demo.auth.temp;

import com.community.demo.auth.temp.dto.LoginRequestDto;
import com.community.demo.exception.BadRequestException;
import com.community.demo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public void login(LoginRequestDto request) {
        Auth auth = authRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("not_found"));

        if (!request.getPassword().equals(auth.getPassword())) {
            throw new BadRequestException("bad_request");
        }
    }
}
