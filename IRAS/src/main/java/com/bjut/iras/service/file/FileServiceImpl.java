package com.bjut.iras.service.file;

import com.bjut.iras.controller.parser.ResumeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        FileServiceImpl.restTemplate = restTemplate;
    }


    @Override
    public Map<String, String> getUserUploadFiles(MultipartFile file) {

        HashMap<String, String> ret = new HashMap<>();

        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        String name = file.getName();

        Resource resource = file.getResource();

        long l = System.currentTimeMillis();

        String fileNames = l + "." + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String uploadDesk = "D:\\uploadFiles\\text\\";
        File finalFile = new File(uploadDesk + fileNames);

        if(!finalFile.getParentFile().exists()){
            finalFile.getParentFile().mkdirs();
        }
        try{
            file.transferTo(finalFile);
            ret.put("error_message", "上传成功");
            ret.put("file path", uploadDesk + fileNames);
            // 调用微服务
            MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
            data.add("input_path", "D:\\uploadFiles\\text\\" + fileNames);
            data.add("output_path", "output");
            restTemplate.postForObject("http://127.0.0.1:3001/", data, String.class);
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            ret.put("error_message", "上传失败");
            return ret;
        }
    }
}
