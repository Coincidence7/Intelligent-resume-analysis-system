package com.bjut.iras.service.resume;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjut.iras.mapper.ResumeMapper;
import com.bjut.iras.pojo.result;
import com.bjut.iras.pojo.resume;
import com.bjut.iras.service.ServiceConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResumeServiceImpl implements ResumeService{

    @Autowired
    private ResumeMapper resumeMapper;

    private HashMap<String, String> getResumesFromQueryWrapper(QueryWrapper<resume> qw, Integer page) {

        IPage<resume> resumeIPage = new Page<>(page, ServiceConsts.resumePageCapacity);
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
        retMap.put("size", Integer.toString(Math.max(ServiceConsts.resumePageCapacity, resumes.size())));

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
    public int deleteResumes(ArrayList<Integer> resumekeys) {
        QueryWrapper<resume> qw = new QueryWrapper<>();
        qw.in("resumekey", resumekeys);
        return resumeMapper.delete(qw);
    }

}
