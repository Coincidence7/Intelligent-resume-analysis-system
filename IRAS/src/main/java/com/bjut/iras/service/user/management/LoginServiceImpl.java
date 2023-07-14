package com.bjut.iras.service.user.management;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjut.iras.mapper.UserMapper;
import com.bjut.iras.pojo.user;
import com.bjut.iras.service.user.detail.UserDetailsImpl;
import com.bjut.iras.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, String> getToken(String username, String password) {
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
//        user user = loginUser.getUser();
//        String jwt = JwtUtil.createJWT(user.getUserkey().toString());
        QueryWrapper<user> qw = new QueryWrapper<>();
        qw.eq("username", username);
        qw.eq("userpwd", password);
        List<user> res = userMapper.selectList(qw);
        Map<String, String> map = new HashMap<>();

        if(res.isEmpty()){
            map.put("error_message", "fail");
        }else {
            map.put("error_message", "success");
        }

        return map;
    }
}