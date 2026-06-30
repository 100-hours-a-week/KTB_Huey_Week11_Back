package com.community.demo.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id @GeneratedValue
    private long id;
    private String nickname;
    private String profileImage;
    private boolean is_deleted;

    public User() {
    }

    public User(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        is_deleted = false;
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void signout() {
        is_deleted = true;
    }
}
