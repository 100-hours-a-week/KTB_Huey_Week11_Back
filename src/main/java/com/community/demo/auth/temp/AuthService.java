package com.community.demo.auth.temp;

import com.community.demo.auth.temp.dto.service.LoginServiceRequestDto;
import com.community.demo.auth.temp.dto.service.LoginServiceResponseDto;
import com.community.demo.auth.temp.dto.service.SignupAuthServiceRequestDto;
import com.community.demo.auth.temp.dto.service.SignupAuthServiceResponseDto;
import com.community.demo.auth.temp.dto.service.ModifyPasswordServiceRequestDto;
import com.community.demo.auth.temp.dto.service.ModifyPasswordServiceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public SignupAuthServiceResponseDto signupAuth(SignupAuthServiceRequestDto dto) {
        Auth auth = new Auth(dto.getUserId(), dto.getEmail(), dto.getPassword());
        authRepository.save(auth);
        return new SignupAuthServiceResponseDto();
    }

    public LoginServiceResponseDto login(LoginServiceRequestDto dto) {
        //이메일 주소로 유저가 존재하는 지 검사
        Auth auth = authRepository.findByEmail(dto.getEmail());
        if (auth == null) {
            return new LoginServiceResponseDto(false);
        }

        //비밀번호 검사하여 유저의 비밀번호와 일치하는지 검사
        //일치 시 성공
        if (auth.getPassword().equals(dto.getPassword())) {
            return new LoginServiceResponseDto(true, auth.getUserId());
        } else {
            return new LoginServiceResponseDto(false);
        }
    }

    public ModifyPasswordServiceResponseDto modifyPassword(ModifyPasswordServiceRequestDto dto) {
        Auth auth = authRepository.findById(dto.getUserId()).orElseThrow();
        auth.modifyPassword(dto.getModifiedPassword());
        authRepository.save(auth);
        return new ModifyPasswordServiceResponseDto();
    }
}
