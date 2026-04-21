package com.example.karaoke.domain.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStoreService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("사업자등록증 이미지는 필수입니다.");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = extractExtension(originalFilename);
            String storedFileName = UUID.randomUUID() + "." + extension;

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path savePath = uploadPath.resolve(storedFileName);
            file.transferTo(savePath.toFile());

            return "/uploads/" + storedFileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.", e);
        }
    }

    private String extractExtension(String originalFilename) {
        if (!StringUtils.hasText(originalFilename) || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("파일 확장자가 올바르지 않습니다.");
        }
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

}
