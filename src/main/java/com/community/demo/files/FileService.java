package com.community.demo.files;

import com.community.demo.exception.BusinessException;
import com.community.demo.exception.InvalidFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static com.community.demo.files.File.createImage;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private static final Path PROJECT_ROOT = Paths.get(System.getProperty("user.dir"));
    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif");

    public File uploadProfileImage(MultipartFile file, Long userId) throws FileUploadException {
        return uploadFile(file, userId, FileCategory.PROFILE_IMAGE);
    }

    public File uploadPostAttachment(MultipartFile file, Long userId) throws FileUploadException {
        return uploadFile(file, userId, FileCategory.POST_ATTACHMENT);
    }

    private File uploadFile(MultipartFile file, Long userId, FileCategory category) throws FileUploadException {
        //log.info(category.getDir());
        Path path = PROJECT_ROOT.resolve(category.getDir());
        //log.info(path.toString());

        String extension = extractAndValidateExtension(file);
        String fileName = generateFileName(category.getDisplayName(), extension);
        Path savePath = path.resolve(fileName);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            file.transferTo(savePath.toFile());
        } catch (IOException exception) {
            log.info("internal server error during file upload, because of IOException");
            throw new BusinessException("internal_server_error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String dbFilePath = category.getUrl() + fileName;

        return fileRepository.save(createImage(dbFilePath, category, userId));
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
