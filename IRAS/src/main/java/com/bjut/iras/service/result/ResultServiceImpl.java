package com.bjut.iras.service.result;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjut.iras.mapper.ResultMapper;
import com.bjut.iras.pojo.result;
import com.bjut.iras.service.QueryConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResultServiceImpl implements ResultService{

    @Autowired
    private ResultMapper resultMapper;

    private HashMap<String, String> getResultsFromQueryWrapper(QueryWrapper<result> qw, Integer page) {

        IPage<result> resultIPage = new Page<>(page, QueryConstants.resultPageCapacity);
        List<result> results = resultMapper.selectPage(resultIPage, qw).getRecords();

        if (results.size() == 0) {
            return null;
        }

        JSONObject jobj = new JSONObject();
        int i = 0;
        for (result r : results) {
            jobj.put("result" + i, r.toString());
            i++;
        }
        HashMap<String, String> retMap = new HashMap<>();
        retMap.put("results", jobj.toJSONString());
        retMap.put("size", Integer.toString(Math.min(QueryConstants.resultPageCapacity, results.size())));

        return retMap;
    }

    @Override
    public Map<String, String> getResultByResumeName(String resumename, Boolean isLike, Integer page) {
        QueryWrapper<result> qw = new QueryWrapper<>();
        if (isLike) {
            qw.like("resumename", resumename);
        } else {
            qw.eq("resumename", resumename);
        }

        return getResultsFromQueryWrapper(qw, page);
    }

    @Override
    public Map<String, String> getResultByResumeKey(Integer resumekey, Integer page) {
        QueryWrapper<result> qw = new QueryWrapper<>();
        qw.eq("resumekey", resumekey);

        return getResultsFromQueryWrapper(qw, page);
    }

    @Override
    public int writeResult(ArrayList<result> results) {
        int successCnt = 0;
        for (result r : results) {
            resultMapper.insert(r);
            successCnt++;
        }
        return successCnt;
    }

    @Override
    public int deleteResults(ArrayList<Integer> resultkeys) {
        QueryWrapper<result> qw = new QueryWrapper<>();
        qw.in("resultkey", resultkeys);
        return resultMapper.delete(qw);
    }
}
