package com.bjut.iras.controller.parser;

import com.bjut.iras.consumer.ResumeParserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ResumeController {
    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        ResumeController.restTemplate = restTemplate;
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
