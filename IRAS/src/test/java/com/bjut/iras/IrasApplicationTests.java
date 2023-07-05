package com.bjut.iras;

import com.bjut.iras.service.QueryConstants;
import com.bjut.iras.service.resume.ResumeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class IrasApplicationTests {

    @Autowired
    ResumeService resumeService;

    @Test
    void resumeSqlTests() {
        QueryConstants.initFilters();

        try {
            Map<String, String> appliedFilters = new HashMap<>();
            appliedFilters.put("byBetweenDate", "2023-07-01&2023-07-04");
            System.out.println(resumeService.getResumeByFilterMap(appliedFilters, 1).toString());

            appliedFilters.clear();
            appliedFilters.put("byFromDate", "2023-07-04");
            System.out.println(resumeService.getResumeByFilterMap(appliedFilters, 1).toString());

            appliedFilters.clear();
            appliedFilters.put("byResumeName", "V");
            System.out.println(resumeService.getResumeByFilterMap(appliedFilters, 1).toString());

        } catch (RuntimeException e) {
            e.printStackTrace();

        }
    }
}
