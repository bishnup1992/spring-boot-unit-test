package com.bishnu.codewithme.util;



import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
package com.example.fragmentservice.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static void storeFile(MultipartFile file, String uploadDir) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }

            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir, fileName);
            Files.createDirectories(uploadPath.getParent());
            Files.write(uploadPath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
}

