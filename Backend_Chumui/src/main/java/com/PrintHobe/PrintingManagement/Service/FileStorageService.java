package com.PrintHobe.PrintingManagement.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private final String uploadDir = "D:\\CUET\\L3_T1\\CSE_326\\Backend_Chumui\\Uploaded_Files";
    public String saveFile(MultipartFile file){
        try {
            // Create uploads directory if not exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Create unique filename
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                throw new RuntimeException("File name is invalid");
            }
            String fileExtension = getFileExtension(originalFilename);
            String baseName = originalFilename.replace(fileExtension, "")
                    .replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

            String timestamp = String.valueOf(System.currentTimeMillis());
            String finalFilename = baseName + "_" + timestamp + fileExtension;

            // Save the file
            Path filePath = uploadPath.resolve(finalFilename);
            file.transferTo(filePath.toFile());

            // Return relative path or absolute path based on your need
            return filePath.toString();  // Or filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage(), e);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
