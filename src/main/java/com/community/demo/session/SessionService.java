package com.community.demo.session;

import com.community.demo.auth.security.CustomUserDetails;
import com.community.demo.exception.NotFoundException;
import com.community.demo.exception.UnauthorizedException;
import com.community.demo.users.User;
import com.community.demo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserRepository userRepository;

    public UserInfoDto getUser(Authentication authentication) {

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException("anonymous_user");
        }

        CustomUserDetails principal = (CustomUserDetails) Objects.requireNonNull(authentication).getPrincipal();

        User user = userRepository.findByIsDeletedFalseAndEmail(principal.getUsername()).orElseThrow(
                () -> new NotFoundException("user_not_found")
        );

        log.info("retreive profile image of userId: " + user.getId()
                + ", filePath: " + Optional.ofNullable(user.getProfileImage().getFilePath()).orElse("null"));

        return UserInfoDto.from(user);
    }

}
