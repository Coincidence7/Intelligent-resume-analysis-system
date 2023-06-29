package com.iras.iras.service.user;

import java.util.Map;

public interface LoginService {
    public Map<String, String> getToken(String username, String password);
}


