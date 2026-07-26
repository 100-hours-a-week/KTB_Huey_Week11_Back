package com.community.demo.auth.security;

import com.community.demo.users.User;
import com.community.demo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByIsDeletedFalseAndEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user_not_found"));

        return new CustomUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getId()
        );
    }
}
