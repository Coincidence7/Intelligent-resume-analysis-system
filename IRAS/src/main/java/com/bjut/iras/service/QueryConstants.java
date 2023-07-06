package com.bjut.iras.service;

import java.util.HashMap;
import java.util.Map;

public class QueryConstants {
    public static int resultPageCapacity = 15;
    public static int resumePageCapacity = 15;
    public static int candidatePageCapacity = 15;
    public static int ticketPageCapacity = 50;

    public static int byKey = 0x01;
    public static int byName = 0x02;
    public static int byBetweenDate = 0x10;
    public static int byFromDate = 0x11;
    public static int byBetweenInt = 0x12;
    public static int byFromInt = 0x13;
    public static int byBeforeInt = 0x14;

    public static int byBetweenCandAge = 0xF0;
    public static int byFromCandAge = 0xF1;
    public static int byCandGender = 0xF2;
    public static int byCandLoc = 0xF3;
    public static int byCandTitle = 0xF4;
    public static int byCandTarget = 0xF5;

    public static int byTicketJobType = 0xE0;
    public static int byTicketJobStatus = 0xE1;
    public static int byTicketInvokerKey = 0xE2;

    public static final Map<String, Integer> supportedResumeFilters = new HashMap<>();
    public static final Map<String, Integer> supportedCandidateFilters = new HashMap<>();
    public static final Map<String, Integer> supportedResultFilters = new HashMap<>();
    public static final Map<String, Integer> supportedTicketFilters = new HashMap<>();

    public static void initFilters() {
        initSupportedResumeFilters();
        initSupportedResultFilters();
        initSupportedCandidateFilters();
        initSupportedTicketFilters();
    }

    private static void initSupportedResumeFilters() {
        supportedResumeFilters.put("byResumeKey", byKey);
        supportedResumeFilters.put("byResumeName", byName);
        supportedResumeFilters.put("byBetweenDate", byBetweenDate);
        supportedResumeFilters.put("byFromDate", byFromDate);
    }

    private static void initSupportedResultFilters() {
        supportedResultFilters.put("byResultKey", byKey);
        supportedResultFilters.put("byResultName", byName);
        supportedResultFilters.put("byBetweenDate", byBetweenDate);
        supportedResultFilters.put("byFromDate", byFromDate);
    }

    private static void initSupportedCandidateFilters() {
        supportedCandidateFilters.put("byCandKey", byKey);
        supportedCandidateFilters.put("byCandName", byName);
        supportedCandidateFilters.put("byBetweenCandAge", byBetweenCandAge);
        supportedCandidateFilters.put("byFromCandAge", byFromCandAge);
        supportedCandidateFilters.put("byCandGender", byCandGender);
        supportedCandidateFilters.put("byCandLoc", byCandLoc);
        supportedCandidateFilters.put("byCandTitle", byCandTitle);
        supportedCandidateFilters.put("byCandTarget", byCandTarget);

    }

    private static void initSupportedTicketFilters() {
        supportedTicketFilters.put("byTicketKey", byKey);
        supportedTicketFilters.put("byTicketName", byKey);
        supportedTicketFilters.put("byBetweenTicketTime", byBetweenDate);
        supportedTicketFilters.put("byFromTicketTime", byFromDate);
        supportedTicketFilters.put("byTicketJobType", byTicketJobType);
        supportedTicketFilters.put("byTicketJobStatus", byTicketJobStatus);
        supportedTicketFilters.put("byTicketInvokerKey", byTicketInvokerKey);
    }
}
