package com.bjut.iras.service.position;

import java.util.Map;

public interface PositionService {
    public Map<String, String> submitPosition(String name, String salary, String title, String sex, String major,
                                                   String workCity, String workTime, String skill, String responsibility);
}
