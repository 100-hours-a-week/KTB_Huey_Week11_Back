package com.community.demo.auth.temp;

import com.community.demo.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, User> {
    public Optional<Auth> findByEmail(String email);
}
