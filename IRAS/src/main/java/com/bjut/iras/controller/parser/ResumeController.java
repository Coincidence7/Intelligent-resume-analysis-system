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
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("input_path", "iras.json");
        data.add("output_path", "output");
        System.out.println(restTemplate.postForObject("http://127.0.0.1:3001/", data, String.class));
    }

}
