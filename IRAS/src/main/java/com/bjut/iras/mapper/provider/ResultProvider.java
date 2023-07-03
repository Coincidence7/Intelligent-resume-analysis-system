package com.bjut.iras.mapper.provider;

public class ResultProvider {
    // TODO
    public String procudeResult(int resumekey) {
        return "select * from resume where resumekey=" + resumekey;
    }
}