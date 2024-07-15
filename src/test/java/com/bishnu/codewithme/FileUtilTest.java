package com.bishnu.codewithme;

package com.example.fragment;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilTest {

    @Test
    public void testSaveFile() throws IOException {
        String uploadDir = "test-uploads";
        MockMultipartFile file = new MockMultipartFile("logo", "test.png", "image/png", "test data".getBytes());

        FileUtil.saveFile(uploadDir, file);

        File destFile = new File(uploadDir, file.getOriginalFilename());
        assertTrue(destFile.exists());
        assertEquals("test data", new String(java.nio.file.Files.readAllBytes(destFile.toPath())));

        // Clean up the test file
        destFile.delete();
        new File(uploadDir).delete();
    }
}
