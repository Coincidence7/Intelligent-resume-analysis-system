package com.bjut.iras.service.result;

import com.bjut.iras.pojo.result;

import java.util.ArrayList;
import java.util.Map;

public interface ResultService {

    public Map<String, String> getResultByResumeName(String resumename, Boolean isLike, Integer page);
    public Map<String, String> getResultByResumeKey(Integer resumekey, Integer page);

    public Map<String, String> getResultByFilterMap(Map<String, String> filters, Boolean isDesc, Integer page);
    public int writeResult(ArrayList<result> results);
    public int deleteResult(ArrayList<Integer> resultkeys);

}
