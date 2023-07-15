package com.bjut.iras.service.position;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjut.iras.mapper.PositionMapper;
import com.bjut.iras.pojo.position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PositionServiceImpl implements PositionService{

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public Map<String, String> submit_position(String sex, String posname, String possalary, String title, String major,
                                               String workcity, String posdescription, String workexp, String projcetexp){
        Map<String, String> map = new HashMap<>();
        QueryWrapper<position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("posname", posname);
        List<position> position = positionMapper.selectList(queryWrapper);
        if (!position.isEmpty()) {
            map.put("error_message", "该岗位已存在");
            return map;
        }

        positionMapper.insert(new position(
                null,
                posname,
                possalary,
                sex,
                title,
                major,
                workcity,
                posdescription,
                workexp,
                projcetexp
        ));
        map.put("error_message", "success");
        return map;
    }
}
