package ru.theflampu.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class UploadFileUtil {

    @Value("${upload.path}")
    private String uploadPath;

    public String saveFile(MultipartFile file, String path) throws IllegalArgumentException, IOException {
        String contentType = file.getContentType();

        if (contentType == null) {
            throw new IllegalArgumentException("ContentType пустой");
        }

        String[] contentTypeParts = contentType.split("/");

        String type = contentTypeParts[0];

        if (!type.equals("image")) {
            throw new IllegalArgumentException("Файл должен быть изображением");
        }

        String extension = contentTypeParts[1];

        String fileName = String.valueOf(System.currentTimeMillis());

        String localFileName = String.format("%s/%s.%s", path, fileName, extension);
        String fullFileName = uploadPath + localFileName;

        file.transferTo(new File(fullFileName));

        return localFileName;
    }
}
