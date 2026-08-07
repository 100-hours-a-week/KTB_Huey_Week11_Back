package com.community.demo.files;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileUploadResponseDto {

    private String fileUrl;

    public static FileUploadResponseDto of(String fileUrl) {
        return new FileUploadResponseDto(fileUrl);
    }

    public static FileUploadResponseDto from(File file) {
        String fullUrl = FileUtil.toFullUrl(file.getFilePath());

        return of(file.getFilePath());
    }
}
