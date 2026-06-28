package com.community.demo.auth.temp;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;

@Entity
@Getter(value = AccessLevel.PROTECTED)
public class Auth {
    private long userId;
    private String email;
    private String password;

    public Auth() {
    }

    public Auth(long userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public void modifyPassword(String modifiedPassword) {
        this.password = modifiedPassword;
    }

}
