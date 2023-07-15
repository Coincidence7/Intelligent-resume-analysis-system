package com.bjut.iras.service.position;

import java.util.Map;

public interface PositionService {
    public Map<String, String> submit_position(String sex, String posname, String possalary, String title, String major,
                                                   String workcity, String posdescription, String workexp, String projcetexp);
}
