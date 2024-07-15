package com.example.demo.util;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileUtilTest {

    @Mock
    MultipartFile file;

    @Test
    void storeFile_success() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("test.png");
        when(file.getBytes()).thenReturn("test data".getBytes());

        FileUtil.storeFile(file, "uploads/");

        assertTrue(Files.exists(Paths.get("uploads/test.png")));
        Files.delete(Paths.get("uploads/test.png"));
    }

    @Test
    void storeFile_emptyFile() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            FileUtil.storeFile(file, "uploads/");
        });

        assertEquals("Failed to store empty file.", exception.getMessage());
    }

    @Test
    void storeFile_ioException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("test.png");
        when(file.getBytes()).thenThrow(new IOException("IO Error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            FileUtil.storeFile(file, "uploads/");
        });

        assertEquals("Failed to store file.", exception.getMessage());
    }
}
