package com.bjut.iras.controller.match;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.bjut.iras.pojo.candidate;
import com.bjut.iras.service.candidate.CandidateService;
import com.bjut.iras.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class PositionPostMatchController {

    @Autowired
    MatchService matchService;

    @Autowired
    CandidateService candidateService;


    @PostMapping("/position/matching/")
    public HashMap<String, String> matching(@RequestParam HashMap<String, String> data){
        String jobName = data.get("jobName");
        return matchService.matchJob(jobName);
    }


    @PostMapping("/position/matching/select/")
    public HashMap<String, String> select(@RequestParam HashMap<String, String> data){
        HashMap<String, String> params = new HashMap<>();
        if(data.get("workYear") != null){
            params.put("byCandJob", data.get("workYear"));
        }
        if(data.get("maxDegree") != null){
            params.put("byCandTitle", data.get("maxDegree"));
        }
        if(data.get("keyword") != null){
            params.put("byCandName", data.get("keyword"));
        }

        Map<String, String> resp = candidateService.getCandidateByFilterMap(params, true, 1);

        HashMap<String, String> ret = new HashMap<>();
        if(resp == null){
            ret.put("error_message", "success");
            ret.put("data", "[]");
            return ret;
        }


        ret.put("error_message", "success");
        ret.put("data", resp.get("resumes"));

        return ret;
    }
}
