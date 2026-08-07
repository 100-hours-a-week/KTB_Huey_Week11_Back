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

    public static CsrfDto of(CsrfToken token) {
        return new CsrfDto(
                token.getHeaderName(),
                token.getToken());
    }

    public static CsrfDto ofAnonymous(CsrfToken token) {
        return new CsrfDto(
                token.getHeaderName(),
                token.getToken()
        );
    }
}
