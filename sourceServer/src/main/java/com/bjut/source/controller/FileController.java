package com.bjut.source.controller;

import com.bjut.source.service.resume.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    @PostMapping(value = "/image/",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(HttpServletResponse response, @RequestParam String path) throws Exception {
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }
    @PostMapping("/resume/path/")
    public HashMap<String, Object> getResumeByPath(@RequestParam String path, HttpServletResponse response){
        return resumeService.getResumeByFilePath(path, response);
    }


}
