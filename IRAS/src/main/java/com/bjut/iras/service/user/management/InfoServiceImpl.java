package com.bjut.iras.service.user.management;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjut.iras.mapper.UserMapper;
import com.bjut.iras.pojo.user;
import com.bjut.iras.service.user.detail.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Map<String, String> getInfo() {
        Map<String, String> map = new HashMap<>();

        return map;
    }

    @Override
    public Map<String, String> getOthers(String userKey) {
        QueryWrapper<user> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userKey);
        List<user> users = userMapper.selectList(queryWrapper);
        Map<String, String> map = new HashMap<>();
        if(users == null || users.isEmpty()){
            map.put("error_message", "not found");
            return map;
        }
        map.put("error_message", "success");
        map.put("id", users.get(0).getUserkey().toString());
        map.put("username", users.get(0).getUsername());
        map.put("photo", users.get(0).getAvataruri());
        return map;
    }
}
