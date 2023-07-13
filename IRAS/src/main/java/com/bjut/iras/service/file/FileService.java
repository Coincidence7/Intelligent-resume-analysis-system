package com.bjut.iras.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface FileService {
    public Map<String, String> getUserUploadFiles(String[] alias, MultipartFile[] file);
}
