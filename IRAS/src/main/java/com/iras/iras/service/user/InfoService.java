package com.iras.iras.service.user;

import java.util.Map;

public interface InfoService {
    Map<String, String> getInfo();
    Map<String, String> getOthers(String userKey);
}
