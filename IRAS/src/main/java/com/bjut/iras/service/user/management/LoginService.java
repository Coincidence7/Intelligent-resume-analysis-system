package com.bjut.iras.service.user.management;

import java.util.Map;

public interface LoginService {
    public Map<String, String> getToken(String username, String password);
}


