package com.community.demo.files;

import lombok.Getter;

@Getter
public enum FileCategory {
    PROFILE_IMAGE("profile", "uploads/profile", "/public/profile/"),
    POST_ATTACHMENT("attachment", "uploads/attachment", "/public/attachment/");

    private final String displayName;
    private final String dir;
    private final String url;

    FileCategory(String displayName, String dir, String url) {
        this.displayName = displayName;
        this.dir = dir;
        this.url = url;
    }
}
