package com.bjut.iras.controller.position;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjut.iras.mapper.PositionMapper;
import com.bjut.iras.service.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PositionController {
    @Autowired
    PositionService positionService;
    @Autowired
    PositionMapper positionMapper;

    @PostMapping("/position/submit/")
    public Map<String, String> submitPosition(@RequestParam Map<String, String> map){
        String name = map.get("name");
        String salary = map.get("salary");
        String title = map.get("title");
        String sex = map.get("sex");
        String major = map.get("major");
        String workCity = map.get("workCity");
        String workTime = map.get("workTime");
        String skill = map.get("skill");
        String responsibility = map.get("responsibility");
        return positionService.submitPosition(name, salary, title, sex, major, workCity, workTime, skill, responsibility);
    }

    @PostMapping("/position/list/")
    private HashMap<String, String> getPositionList(){
        HashMap<String, String> ret = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(positionMapper.selectList(new QueryWrapper<>()));
        ret.put("error_message", "success");
        ret.put("data", jsonArray.toString());
        return ret;
    }
}

