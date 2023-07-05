package com.bjut.iras.service.candidate;

import com.bjut.iras.mapper.CandidateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl {

    @Autowired
    private CandidateMapper candidateMapper;


}
