package com.bjut.iras.controller.parser;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjut.iras.consumer.ResumeParserServer;
import com.bjut.iras.mapper.ResumeMapper;
import com.bjut.iras.pojo.resume;
import com.bjut.iras.service.resume.ResumeService;
import org.python.modules._hashlib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
public class ResumeController {

    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        ResumeController.restTemplate = restTemplate;
    }

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeMapper resumeMapper;

    @PostMapping("/resume/List/")
    private HashMap<String, String> getResumeList(){
        HashMap<String, String> ret = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(resumeMapper.selectList(new QueryWrapper<>()));
        ret.put("error_message", "success");
        ret.put("data", jsonArray.toString());
        return ret;
    }

    @PostMapping("resume/record/delete/")
    private HashMap<String, String> deleteResume(@RequestParam HashMap<String, String> data){
        HashMap<String, String> ret = new HashMap<>();
        ret.put("error_message", "success");
        String[] res = data.get("resumeKey").split(",");

        ret.put("ret_code", String.valueOf(resumeMapper.deleteBatchIds(Arrays.asList(res))));
        return ret;
    }
    /**
     * @createTime 2023-07-04
     * @comment 微服务调用演示实例
     */
    @GetMapping("/test/")
    private void demo(){
        System.out.println("");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("file_path", "F:/软件杯/demo/");
//        data.add("file_path", "D:\\uploadFiles\\raw\\");
        data.add("file_name", "test.txt");
        String str = restTemplate.postForObject("http://10.18.25.194:3001/", data, String.class);
        try{
            System.out.println(new String(str.getBytes("utf-8"),"utf-8"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
