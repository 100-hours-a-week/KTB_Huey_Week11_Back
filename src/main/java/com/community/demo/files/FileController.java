package com.community.demo.files;

import com.community.demo.ApiResponse;
import com.community.demo.auth.Login;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/users/me/profile-image")
    public ResponseEntity<ApiResponse<FileUploadResponseDto>> uploadProfileImage(
            @RequestPart("profileImage") MultipartFile file
    ) throws FileUploadException {
        File savedFile = fileService.uploadProfileImage(file, 0L);

        return ResponseEntity
                .created(null)
                .body(ApiResponse.of(
                        "profile_image_uploaded",
                        FileUploadResponseDto.from(savedFile)
                ));
    }

    @PostMapping("/public/attachments")
    public ResponseEntity<ApiResponse<FileUploadResponseDto>> uploadPostImage(
            @Login Long userId,
            @RequestPart("image") MultipartFile file
    ) throws FileUploadException {
        File savedFile = fileService.uploadPostAttachment(file, userId);

        return ResponseEntity
                .created(null)
                .body(ApiResponse.of(
                        "post_attachment_uploaded",
                        FileUploadResponseDto.from(savedFile)
                ));
    }
}
