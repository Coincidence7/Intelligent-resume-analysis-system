package com.bjut.iras.controller.file;

import com.bjut.iras.controller.parser.ResumeController;
import com.bjut.iras.service.file.FileService;
import org.python.modules._hashlib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FileController {


    @Autowired
    FileService fileService;

    @PostMapping("/upload/")
    public Map<String, String> saveFile(@RequestParam HashMap<String, MultipartFile> files){
        System.out.println("对上传文件进行解析");
        System.out.println();

        ArrayList<MultipartFile> fileList = new ArrayList<>(files.values());

        return fileService.getUserUploadFiles(fileList.toArray(new MultipartFile[0]));
    }
    
}
