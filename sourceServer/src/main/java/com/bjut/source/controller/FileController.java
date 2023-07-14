package com.bjut.source.controller;

import com.bjut.source.service.resume.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

@RestController
public class FileController {

    @Autowired
    ResumeService resumeService;

    @GetMapping(value = "/image/",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@RequestParam String resumeKey){
        return resumeService.getImage(resumeKey);
    }

    @GetMapping("/resume/path/")
    public void getResumeByPath(@RequestParam String resumeKey, HttpServletResponse response){
        resumeService.getResumeByFilePath(resumeKey, response);
    }

    @PostMapping("/resume/upload/")
    public HashMap<String, String> upload(@RequestParam String path){
        return resumeService.addFile(path);
    }

}
