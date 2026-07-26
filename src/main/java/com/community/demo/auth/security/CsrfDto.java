package com.community.demo.auth.security;

import com.community.demo.users.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.web.csrf.CsrfToken;

@Getter
@AllArgsConstructor
public class CsrfDto {
    private String name;
    private String token;
    private Long userId;
    private String email;
    private String nickname;
    private String profileImage;

    public static CsrfDto of(CsrfToken token, UserInfoDto userInfoDto) {
        return new CsrfDto(
                token.getHeaderName(),
                token.getToken(),
                userInfoDto.getUserId(),
                userInfoDto.getEmail(),
                userInfoDto.getNickname(),
                userInfoDto.getProfileImage());
    }

    public static CsrfDto ofAnonymous(CsrfToken token) {
        return new CsrfDto(
                token.getHeaderName(),
                token.getToken(),
                null, null, null, null
        );
    }
}
