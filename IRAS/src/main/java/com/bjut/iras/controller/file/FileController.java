package com.bjut.iras.controller.file;

import com.bjut.iras.controller.parser.ResumeController;
import com.bjut.iras.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileController {


    @Autowired
    FileService fileService;

    @PostMapping("/upload/")
    public Map<String, String> saveFile(@RequestParam("file") MultipartFile file){
            return fileService.getUserUploadFiles(file);
    }
    
}
