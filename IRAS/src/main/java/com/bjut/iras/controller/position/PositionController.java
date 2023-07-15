package com.bjut.iras.controller.position;


import com.bjut.iras.service.position.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PositionController {
    @Autowired
    PositionService positionService;
    @PostMapping("/position/submit/")
    public Map<String, String> submit_position(@RequestParam Map<String, String> map){
        String posname = map.get("posname");
        String possalary = map.get("possalary");
        String sex = map.get("sex");
        String title = map.get("title");
        String major = map.get("major");
        String workcity = map.get("workcity");
        String posdescription = map.get("posdescription");
        String workexp = map.get("workexp");
        String projcetexp = map.get("projcetexp");
        return positionService.submit_position(posname, possalary, sex, title, major, workcity, posdescription, workexp, projcetexp);
    }
}
