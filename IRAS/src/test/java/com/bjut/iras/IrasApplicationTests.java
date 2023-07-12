package com.bjut.iras;

import com.bjut.iras.service.QueryConstants;
import com.bjut.iras.service.candidate.CandidateService;
import com.bjut.iras.service.result.ResultService;
import com.bjut.iras.service.resume.ResumeService;
import com.bjut.iras.utils.Cal;
import org.junit.jupiter.api.Test;
import org.python.icu.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class IrasApplicationTests {

    @Autowired
    ResumeService resumeService;
    @Autowired
    ResultService resultService;
    @Autowired
    CandidateService candidateService;

    @Test
    void CalWorkYearTest(){

        String res = "[\"2020-01至2021-10\"]";
        System.out.println(Cal.CalWorkYear(res));
        System.out.println(Cal.CalWorkYear("[\"2010/01/02至2014/10-10\"]"));
        System.out.println(Cal.CalWorkYear("[\"2010.01/02 2014-10.10\"]"));
        System.out.println(Cal.CalWorkYear("[\"2010.01/02 至今\"]"));
        System.out.println(Cal.CalWorkYear("[\"2010.01/02\"]"));
        System.out.println(Cal.CalWorkYear("[]"));
    }
    @Test
    void CalAgeTest(){
        System.out.println(Cal.CalAge("2004-"));
        System.out.println(Cal.CalAge("2004-12"));
        System.out.println(Cal.CalAge("2004-1"));
        System.out.println(Cal.CalAge("12"));
    }
    public static String ascii2native(String ascii) {

        int n = ascii.length() / 6;

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0, j = 2; i < n; i++, j += 6) {

            String code = ascii.substring(j, j + 4);

            char ch = (char) Integer.parseInt(code, 16);

            sb.append(ch);

        }

        return sb.toString();

    }

    @Test
    void resumeSqlTests() {
        QueryConstants.initFilters();

        try {
            Map<String, String> appliedFilters = new HashMap<>();
            appliedFilters.put("byBetweenDate", "2023-07-01&2023-07-04");
            System.out.println(resumeService.getResumeByFilterMap(appliedFilters, true, 1).toString());

            appliedFilters.clear();
            appliedFilters.put("byFromDate", "2023-07-04");
            System.out.println(resumeService.getResumeByFilterMap(appliedFilters, true, 1).toString());

            appliedFilters.clear();
            appliedFilters.put("byResumeName", "V");
            appliedFilters.put("byFromDate", "2023-07-05");
            System.out.println(resumeService.getResumeByFilterMap(appliedFilters, true, 1).toString());

        } catch (RuntimeException e) {
            e.printStackTrace();

        }
    }

    @Test
    void resultSqlTests() {
        QueryConstants.initFilters();

        try {
            Map<String, String> appliedFilters = new HashMap<>();
            appliedFilters.put("byBetweenDate", "2023-07-04&2023-07-06");
            System.out.println(resultService.getResultByFilterMap(appliedFilters, false, 1).toString());

            appliedFilters.clear();
            appliedFilters.put("byFromDate", "2023-07-07");
            System.out.println(resultService.getResultByFilterMap(appliedFilters, true, 1).toString());

            appliedFilters.clear();
            appliedFilters.put("byResultName", "1-1");
            appliedFilters.put("byBetweenDate", "2023-07-04&2023-07-06");
            System.out.println(resultService.getResultByFilterMap(appliedFilters, true, 1).toString());

        } catch (RuntimeException e) {
            e.printStackTrace();

        }
    }

    @Test
    void candSqlTests() {
        QueryConstants.initFilters();

        try {
            Map<String, String> appliedFilters = new HashMap<>();
            appliedFilters.put("byBetweenCandAge", "21&25");
            appliedFilters.put("byCandLoc", "西安");
            System.out.println(candidateService.getCandidateByFilterMap(appliedFilters, true, 1).toString());

            appliedFilters.clear();
            appliedFilters.put("byCandTitle", "本科生");
            appliedFilters.put("byFromCandAge", "23");
            System.out.println(candidateService.getCandidateByFilterMap(appliedFilters, true, 1).toString());

            appliedFilters.clear();
            appliedFilters.put("byCandName", "cand1");
            appliedFilters.put("byCandGender", "0");
            System.out.println(candidateService.getCandidateByFilterMap(appliedFilters, true, 1).toString());

        } catch (RuntimeException e) {
            e.printStackTrace();

        }
    }
}
