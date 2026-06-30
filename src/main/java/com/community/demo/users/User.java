package com.community.demo.users;

import com.community.demo.files.File;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private long id;
    private String nickname;
    private boolean is_deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File profileImage;

    public User() {
    }

    public User(String nickname, File profileImage) {
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

    public void updateProfileImage(File profileImage) {
        this.profileImage = profileImage;
    }
}
