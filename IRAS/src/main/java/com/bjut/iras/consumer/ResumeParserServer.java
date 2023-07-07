package com.bjut.iras.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ResumeParserServer {


    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        ResumeParserServer.restTemplate = restTemplate;
    }

    private void demo(){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        restTemplate.postForObject("127.0.0.1/3000/", data, String.class);
    }
}
