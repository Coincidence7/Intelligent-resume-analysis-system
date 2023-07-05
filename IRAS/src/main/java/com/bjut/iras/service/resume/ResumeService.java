package com.bjut.iras.service.resume;

import com.bjut.iras.pojo.result;
import com.bjut.iras.pojo.resume;

import java.util.ArrayList;
import java.util.Map;

public interface ResumeService {

    public Map<String, String> getResumeByName(String resumename, Boolean isLike, Integer page);
    public Map<String, String> getResumeByKey(Integer resumekey, Integer page);
    public Map<String, String> getResumeByFilterMap(Map<String, String> filters, Integer page);
    public int writeResume(ArrayList<resume> resumes);
    public int deleteResumes(ArrayList<Integer> resumekeys);
}
