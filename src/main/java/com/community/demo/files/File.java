package com.community.demo.files;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {
    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;
    private String filePath = "/public/images/default.jpg";

    @Enumerated(EnumType.STRING)
    private FileCategory fileCategory;

    private Long uploaderId;

    public File(String filePath, FileCategory fileCategory, Long uploaderId) {
        this.filePath = filePath;
        this.fileCategory = fileCategory;
        this.uploaderId = uploaderId;
    }

    public static File createProfileImage(String filePath, Long uploaderId) {
        return new File(filePath, FileCategory.PROFILE_IMAGE, uploaderId);
    }

    public static File createPostAttachment(String filePath, Long uploaderId) {
        return new File(filePath, FileCategory.POST_ATTACHMENT, uploaderId);
    }
}
