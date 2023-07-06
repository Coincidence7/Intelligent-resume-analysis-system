package com.bjut.iras.service.candidate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjut.iras.mapper.CandidateMapper;
import com.bjut.iras.pojo.candidate;
import com.bjut.iras.service.QueryConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CandidateServiceImpl implements CandidateService{

    @Autowired
    private CandidateMapper candidateMapper;

    private HashMap<String, String> getCandidatesFromQueryWrapper(QueryWrapper<candidate> qw, Integer page) {

        IPage<candidate> candidateIPage = new Page<>(page, QueryConstants.candidatePageCapacity);
        List<candidate> candidates = candidateMapper.selectPage(candidateIPage, qw).getRecords();

        if (candidates.size() == 0) {
            return null;
        }

        JSONObject jobj = new JSONObject();
        int i = 0;
        for (candidate c : candidates) {
            jobj.put("result" + i, c.toString());
            i++;
        }

        HashMap<String, String> retMap = new HashMap<>();
        retMap.put("resumes", jobj.toJSONString());
        retMap.put("size", Integer.toString(Math.min(QueryConstants.resumePageCapacity, candidates.size())));

        return retMap;
    }

    @Override
    public Map<String, String> getCandidateByName(String candname, Boolean isLike, Integer page) {
        QueryWrapper<candidate> qw = new QueryWrapper<>();

        if (isLike) {
            qw.like("candname", candname);
        } else {
            qw.eq("candname", candname);
        }

        return getCandidatesFromQueryWrapper(qw, page);
    }

    @Override
    public Map<String, String> getCandidateByKey(Integer candkey, Integer page) {
        QueryWrapper<candidate> qw = new QueryWrapper<>();
        qw.eq("candkey", candkey);

        return getCandidatesFromQueryWrapper(qw, page);
    }

    @Override
    public Map<String, String> getCandidateByFilterMap(Map<String, String> filters, Boolean isDesc, Integer page) {
        QueryWrapper<candidate> qw = new QueryWrapper<>();

        for (Map.Entry<String, String> e : filters.entrySet()) {
            if (QueryConstants.supportedCandidateFilters.get(e.getKey()) == null) {
                throw new RuntimeException("Unsupported filter type: " + e.getKey() + ".");

            }
            int filterType = QueryConstants.supportedCandidateFilters.get(e.getKey());
            if (filterType == QueryConstants.byKey) {
                qw.eq("candkey", e.getValue());

                if (isDesc) qw.orderByDesc("candkey");

            } else if (filterType == QueryConstants.byName) {
                qw.like("candname", e.getValue());

                if (isDesc) qw.orderByDesc("candkey");

            } else if (filterType == QueryConstants.byBetweenCandAge) {
                String[] ageStrs = e.getValue().split("&");

                qw.between("candage", ageStrs[0], ageStrs[1]);

                if (isDesc) qw.orderByDesc("candkey");

            } else if (filterType == QueryConstants.byFromCandAge) {
                qw.ge("candage", e.getValue());

                if (isDesc) qw.orderByDesc("candkey");

            } else if (filterType == QueryConstants.byCandGender){
                qw.eq("candgender", e.getValue());

                if (isDesc) qw.orderByDesc("candkey");

            } else if (filterType == QueryConstants.byCandLoc){
                qw.like("candloc", e.getValue());

                if (isDesc) qw.orderByDesc("candkey");

            } else if (filterType == QueryConstants.byCandTitle){
                qw.like("candtitle", e.getValue());

                if (isDesc) qw.orderByDesc("candkey");

            } else if (filterType == QueryConstants.byCandTarget){
                qw.like("candtarget", e.getValue());

                if (isDesc) qw.orderByDesc("candkey");
            }
        }
        return getCandidatesFromQueryWrapper(qw, page);
    }

    @Override
    public int writeCandidate(ArrayList<candidate> cands) {
        int successCnt = 0;
        for (candidate c : cands) {
            candidateMapper.insert(c);
            successCnt++;
        }
        return successCnt;
    }

    @Override
    public int deleteCandidate(ArrayList<Integer> candkeys) {
        return 0;
    }
}
