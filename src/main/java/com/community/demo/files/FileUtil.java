package com.community.demo.files;

import com.community.demo.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class FileUtil {
    private FileUtil() {
        throw new BusinessException("internal_server_error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static String extractPathFromUrl(String url) {
        if (url == null || url.isBlank()) {
            return null;
        }

        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            URI uri = new URI(url);
            String path = uri.getPath();
            return path != null ? path : url;
        } catch (URISyntaxException e) {
            return url;
        }

    }

    public static String toFullUrl(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            return null;
        }

        if (relativePath.startsWith("http")) {
            return relativePath;
        }

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();

        return baseUrl + relativePath;
    }

    public static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + "B";
        int z = (63 - Long.numberOfLeadingZeros(bytes)) / 10;
        return String.format("%.1f %sB", (double) bytes / (1L << (z * 10)), " KMGTPE".charAt(z));
    }
}
