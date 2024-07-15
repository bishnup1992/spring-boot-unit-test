package com.bishnu.codewithme;

package com.example.demo.controller;

import com.example.demo.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FragmentControllerTest {

    @Test
    void uploadFile_success() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("test.png");
        when(file.getBytes()).thenReturn("test data".getBytes());

        try (MockedStatic<FileUtil> mockedStatic = Mockito.mockStatic(FileUtil.class)) {
            FragmentController controller = new FragmentController();
            ResponseEntity<String> response = controller.uploadFile(file);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("File uploaded successfully.", response.getBody());

            mockedStatic.verify(() -> FileUtil.storeFile(file, "uploads/"), times(1));
        }
    }

    @Test
    void uploadFile_noFile() {
        FragmentController controller = new FragmentController();
        ResponseEntity<String> response = controller.uploadFile(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("File is required.", response.getBody());
    }

    @Test
    void uploadFile_emptyFile() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        FragmentController controller = new FragmentController();
        ResponseEntity<String> response = controller.uploadFile(file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("File is required.", response.getBody());
    }

    @Test
    void uploadFile_ioException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("test.png");
        when(file.getBytes()).thenThrow(new IOException("IO Error"));

        try (MockedStatic<FileUtil> mockedStatic = Mockito.mockStatic(FileUtil.class)) {
            mockedStatic.when(() -> FileUtil.storeFile(file, "uploads/")).thenThrow(new RuntimeException("Failed to store file."));

            FragmentController controller = new FragmentController();
            ResponseEntity<String> response = controller.uploadFile(file);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("Failed to store file.", response.getBody());
        }
    }
}

