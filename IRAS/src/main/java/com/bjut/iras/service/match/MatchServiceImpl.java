package com.bjut.iras.service.match;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjut.iras.mapper.ResultMapper;
import com.bjut.iras.pojo.result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    ResultMapper resultMapper;

    @Override
    public HashMap<String, String> matchJob(String jobName) {
        QueryWrapper<result> qw = new QueryWrapper<>();
        qw.eq("jobname", jobName);
        qw.eq("state", "finish");
        HashMap<String, String> resp = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(resultMapper.selectList(qw));
        resp.put("data", jsonArray.toString());
        resp.put("error_message", "success");
        return resp;
    }
}
