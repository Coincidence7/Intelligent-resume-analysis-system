package com.bjut.iras.service.user.management;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjut.iras.mapper.UserMapper;
import com.bjut.iras.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if (password == null || confirmedPassword == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }
        username = username.trim();
        if (username.length() == 0) {
            map.put("error_message", "用户名不能为空");
            return map;
        }
        if (password.length() == 0 || confirmedPassword.length() == 0) {
            map.put("error_message", "密码不能为空");
            return map;
        }
        if (username.length() > 64) {
            map.put("error_message", "用户名长度不能大于64");
            return map;
        }
        if (password.length() > 64 || confirmedPassword.length() > 64) {
            map.put("error_message", "密码长度不能大于64");
            return map;
        }
        if (!password.equals(confirmedPassword)) {
            map.put("error_message", "两次密码输入不一致");
            return map;
        }

        QueryWrapper<user> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<user> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "用户名已存在");
            return map;
        }

        String avatarURI = "https://cdn.acwing.com/media/user/profile/photo/74595_lg_14adcae966.jpg";
        userMapper.insert(new user(
                null,
                Integer.parseInt(Long.toString(System.currentTimeMillis()).substring(0, 6)),
                username,
                password,
                avatarURI
        ));
        map.put("error_message", "success");
        return map;
    }


}
