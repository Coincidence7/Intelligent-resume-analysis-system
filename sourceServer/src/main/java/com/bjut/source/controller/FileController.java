package com.bjut.source.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bjut.source.service.resume.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
    public HashMap<String, String> upload(@RequestParam HashMap<String, String> files){
        JSONArray paths_json = JSON.parseArray(files.get("upload_paths[]"));
        JSONArray key_json = JSON.parseArray(files.get("upload_keys[]"));
        ArrayList<String> paths = new ArrayList<>();
        ArrayList<String> key = new ArrayList<>();
        for(int i = 0; i < paths_json.size(); i++){
            paths.add(paths_json.get(i).toString());
            key.add(key_json.get(i).toString());
        }
        return resumeService.addFile(paths, key);
    }

}
