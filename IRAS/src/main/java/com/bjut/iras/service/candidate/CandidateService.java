package com.bjut.iras.service.candidate;

import com.bjut.iras.pojo.candidate;
import com.bjut.iras.pojo.resume;

import java.util.ArrayList;
import java.util.Map;

public interface CandidateService {

    public Map<String, String> getCandidateByName(String candname, Boolean isLike, Integer page);
    public Map<String, String> getCandidateByKey(Integer candkey, Integer page);
    public Map<String, String> getCandidateByFilterMap(Map<String, String> filters, Boolean isDesc, Integer page);
    public int writeCandidate(ArrayList<candidate> cands);
    public int deleteCandidate(ArrayList<Integer> candkeys);

}
