package com.bjut.iras.service;

import java.util.HashMap;
import java.util.Map;

public class QueryConstants {
    public static int resultPageCapacity = 15;
    public static int resumePageCapacity = 15;
    public static int ticketPageCapacity = 50;

    public static int byKey = 0x01;
    public static int byName = 0x02;
    public static int byBetweenDate = 0x10;
    public static int byFromDate = 0x011;

    public static final Map<String, Integer> supportedResumeFilters = new HashMap<>();
    public static final Map<String, Integer> supportedCandidateFilters = new HashMap<>();

    public static void initFilters() {
        initSupportedResumeFilters();
        initSupportedCandidateFilters();
    }

    private static void initSupportedResumeFilters() {
        supportedResumeFilters.put("byResumeKey", byKey);
        supportedResumeFilters.put("byResumeName", byName);
        supportedResumeFilters.put("byBetweenDate", byBetweenDate);
        supportedResumeFilters.put("byFromDate", byFromDate);
    }

    private static void initSupportedCandidateFilters() {
        // TODO: Do something
    }

}
