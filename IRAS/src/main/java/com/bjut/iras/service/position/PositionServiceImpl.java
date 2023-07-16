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
    public Map<String, String> submitPosition(String name, String salary, String title, String sex, String major,
                                              String workCity, String workTime, String skill, String responsibility){
        Map<String, String> map = new HashMap<>();
        QueryWrapper<position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("posname", name);
        List<position> position = positionMapper.selectList(queryWrapper);
        if (!position.isEmpty()) {
            map.put("error_message", "该岗位已存在");
            return map;
        }

        positionMapper.insert(new position(
                null,
                name,
                salary,
                title,
                sex,
                major,
                workCity,
                workTime,
                skill,
                responsibility
        ));
        map.put("error_message", "success");
        return map;
    }
}
