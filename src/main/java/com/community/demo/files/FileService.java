package com.community.demo.files;

import com.community.demo.exception.BusinessException;
import com.community.demo.exception.InvalidFileException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.community.demo.files.File.createProfileImage;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private static final Path PROJECT_ROOT = Paths.get(System.getProperty("user.dir"));
    private static final Path PROFILE_DIR = PROJECT_ROOT.resolve("uploads/profile");
    private static final Path POST_ATTACHMENT_DIR = PROJECT_ROOT.resolve("uploads/attachment");
    private static final String PROFILE_URL = "/public/profile/";
    private static final String POST_ATTACHMENT_URL = "/public/attachment/";
    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif");

    public File uploadProfileImage(MultipartFile file, Long userId) throws FileUploadException {
        return uploadFile(file, userId, PROFILE_DIR, PROFILE_URL);
    }

    public File uploadPostAttachment(MultipartFile file, Long userId) throws FileUploadException {
        return uploadFile(file, userId, POST_ATTACHMENT_DIR, POST_ATTACHMENT_URL);
    }

    private File uploadFile(MultipartFile file, Long userId, Path path, String url) throws FileUploadException {
        String extension = extractAndValidateExtension(file);
        String fileName = generateFileName("profile", extension);
        Path savePath = path.resolve(fileName);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            file.transferTo(savePath.toFile());
        } catch (IOException exception) {
            throw new BusinessException("internal_server_error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String dbFilePath = url + fileName;

        return fileRepository.save(createProfileImage(dbFilePath, userId));
    }

    private String extractAndValidateExtension(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            throw new InvalidFileException(file.getName(), "file_name_required");
        }

        String extension = StringUtils.getFilenameExtension(originalName);
        if (extension == null || !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new InvalidFileException(file.getName(), "invalid_file_extension");
        }

        return extension;
    }

    private String generateFileName(String prefix, String extension) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String uuid = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        return prefix + "-" + timestamp + "-" + uuid + "." + extension;
    }
}
