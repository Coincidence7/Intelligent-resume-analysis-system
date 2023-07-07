package com.bjut.iras.service.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjut.iras.controller.parser.ResumeController;
import com.bjut.iras.pojo.resume;
import com.bjut.iras.service.resume.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        FileServiceImpl.restTemplate = restTemplate;
    }

    @Autowired
    private ResumeService resumeService;


    @Override
    public Map<String, String> getUserUploadFiles(MultipartFile[] files) {

        HashMap<String, String> ret = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        List<FileSystemResource> UpLoadFiles = new ArrayList<>();
        ArrayList<resume> resumes = new ArrayList<>();


        for(int i = 0; i < files.length; i++){
            JSONObject jsonObject = new JSONObject();
            MultipartFile file = files[i];
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            String name = file.getName();

            long l = System.currentTimeMillis();

            String fileNames = l + "." + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String uploadDesk = "D:\\uploadFiles\\raw\\";
            File finalFile = new File(uploadDesk + fileNames);
            if(!finalFile.getParentFile().exists()){
                finalFile.getParentFile().mkdirs();
            }
            try{
                jsonObject.put("original name", originalFilename);
                jsonObject.put("error_message", "success");
                jsonObject.put("file path", uploadDesk + fileNames);
                // 获取文件名
                try (OutputStream out = new FileOutputStream(finalFile)) {
                    // 输入流和输出流之间的拷贝
                    FileCopyUtils.copy(file.getInputStream(), out);
                } catch (IOException e) {
                    System.out.println("上传的文件转换异常" + e.getMessage());
                }
                resume myResume = new resume();
                myResume.setPath(uploadDesk + fileNames);
                myResume.setResumename("简历");
                myResume.setFilename(originalFilename);
                myResume.setState("doing");
                myResume.setUploadtime(new Timestamp(System.currentTimeMillis()));
                resumes.add(myResume);
                FileSystemResource resource = new FileSystemResource(finalFile);
                UpLoadFiles.add(resource);

            }catch (Exception e){
                e.printStackTrace();
                jsonObject.put("error_message", "fail");
                jsonArray.add(jsonObject);
            }
        }
        for (resume item: resumes) {
            System.out.println(item);
        }
        resumeService.writeResume(resumes);
        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("appCode", "platform-device");
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        // 构建请求体
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("upload_files_num", UpLoadFiles.size());
        System.out.println(UpLoadFiles.size());
        requestBody.addAll("upload_files[]", UpLoadFiles);

        // 发送上传请求
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(requestBody, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://10.18.25.4:3001",
                requestEntity, String.class);

        System.out.println(responseEntity.getBody());

        ret.put("error_message", "success");
        ret.put("data", responseEntity.getBody());
        ret.put("orginal data", jsonArray.toString());

        return ret;
    }
}
