package com.bjut.iras.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {
    public Map<String, String> getUserUploadFiles(MultipartFile[] file);
}
