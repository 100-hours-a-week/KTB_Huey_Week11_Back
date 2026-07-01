package com.community.demo.auth.temp;

import com.community.demo.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;

@Entity
@Getter
public class Auth {
    @Id @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String email;
    private String password;

    protected Auth() {
    }

    public Auth(User user, String email, String password) {
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

}
