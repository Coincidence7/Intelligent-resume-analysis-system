package com.iras.iras.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iras.iras.mapper.UserMapper;
import com.iras.iras.pojo.User;
import com.iras.iras.service.UserDetailsImpl;
import com.iras.iras.service.user.InfoService;
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
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("id", user.getUserKey().toString());
        map.put("username", user.getUserName());
        map.put("photo", user.getAvatarURI());
        return map;
    }

    @Override
    public Map<String, String> getOthers(String userKey) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userKey);
        List<User> users = userMapper.selectList(queryWrapper);
        Map<String, String> map = new HashMap<>();
        if(users == null || users.isEmpty()){
            map.put("error_message", "not found");
            return map;
        }
        map.put("error_message", "success");
        map.put("id", users.get(0).getUserKey().toString());
        map.put("username", users.get(0).getUserName());
        map.put("photo", users.get(0).getAvatarURI());
        return map;
    }
}
