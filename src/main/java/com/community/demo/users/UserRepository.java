package com.community.demo.users;

import com.community.demo.auth.temp.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByIsDeletedFalseAndEmail(String email);

    public Boolean existsByEmail(String email);

    public Boolean existsByNickname(String nickname);
}
