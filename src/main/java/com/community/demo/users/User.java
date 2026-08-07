package com.community.demo.users;

import com.community.demo.files.File;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "\"user\"")
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String nickname;
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File profileImage;

    private String email;
    private String password;

    protected User() {
    }

    public User(String nickname, File profileImage, String email, String password) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        isDeleted = false;
        this.email = email;
        this.password = password;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void signout() {
        isDeleted = true;
    }

    public void updateProfileImage(File profileImage) {
        this.profileImage = profileImage;
    }
}
