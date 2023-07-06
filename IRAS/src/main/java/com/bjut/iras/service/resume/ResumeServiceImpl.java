package com.bjut.iras.service.resume;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjut.iras.mapper.ResumeMapper;
import com.bjut.iras.pojo.resume;
import com.bjut.iras.service.QueryConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeMapper resumeMapper;

    private HashMap<String, String> getResumesFromQueryWrapper(QueryWrapper<resume> qw, Integer page) {

        IPage<resume> resumeIPage = new Page<>(page, QueryConstants.resumePageCapacity);
        List<resume> resumes = resumeMapper.selectPage(resumeIPage, qw).getRecords();

        if (resumes.size() == 0) {
            return null;
        }

        JSONObject jobj = new JSONObject();
        int i = 0;
        for (resume r : resumes) {
            jobj.put("result" + i, r.toString());
            i++;
        }

        HashMap<String, String> retMap = new HashMap<>();
        retMap.put("resumes", jobj.toJSONString());
        retMap.put("size", Integer.toString(Math.min(QueryConstants.resumePageCapacity, resumes.size())));

        return retMap;
    }

    @Override
    public Map<String, String> getResumeByName(String resumename, Boolean isLike, Integer page) {

        QueryWrapper<resume> qw = new QueryWrapper<>();

        if (isLike) {
            qw.like("resumename", resumename);
        } else {
            qw.eq("resumename", resumename);
        }

        return getResumesFromQueryWrapper(qw, page);
    }

    @Override
    public Map<String, String> getResumeByKey(Integer resumekey, Integer page) {
        QueryWrapper<resume> qw = new QueryWrapper<>();
        qw.eq("resumekey", resumekey);

        return getResumesFromQueryWrapper(qw, page);
    }

    public Map<String, String> getResumeByFilterMap(Map<String, String> filters, Boolean isDesc, Integer page) {

        QueryWrapper<resume> qw = new QueryWrapper<>();

        for (Map.Entry<String, String> e : filters.entrySet()) {
            if (QueryConstants.supportedResumeFilters.get(e.getKey()) == null) {
                throw new RuntimeException("Unsupported filter type: " + e.getKey() + ".");

            }
            int filterType = QueryConstants.supportedResumeFilters.get(e.getKey());
            if (filterType == QueryConstants.byKey) {
                qw.eq("resumekey", e.getValue());

                if (isDesc) qw.orderByDesc("resumekey");

            } else if (filterType == QueryConstants.byName) {
                qw.like("resumename", e.getValue());

                if (isDesc) qw.orderByDesc("resumekey");

            } else if (filterType == QueryConstants.byBetweenDate) {
                String[] dateStrs = e.getValue().split("&");
                qw.apply("DATE(uploadtime) >= STR_TO_DATE('"
                        + dateStrs[0] + "', '%Y-%m-%d')");
                qw.apply("DATE(uploadtime) <= STR_TO_DATE('"
                        + dateStrs[1] + "', '%Y-%m-%d')");

                if (isDesc) qw.orderByDesc("uploadtime");

            } else if (filterType == QueryConstants.byFromDate) {
                qw.ge("uploadtime", e.getValue());

                if (isDesc) qw.orderByDesc("uploadtime");

            }
        }
        return getResumesFromQueryWrapper(qw, page);
    }

    @Override
    public int writeResume(ArrayList<resume> resumes) {
        int successCnt = 0;
        for (resume r : resumes) {
            resumeMapper.insert(r);
            successCnt++;
        }
        return successCnt;
    }

    @Override
    public int deleteResume(ArrayList<Integer> resumekeys) {
        QueryWrapper<resume> qw = new QueryWrapper<>();
        qw.in("resumekey", resumekeys);
        return resumeMapper.delete(qw);
    }

}
