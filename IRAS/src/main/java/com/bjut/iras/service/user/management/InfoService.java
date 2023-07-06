package com.bjut.iras.service.user.management;

import java.util.Map;

public interface InfoService {
    Map<String, String> getInfo();
    Map<String, String> getOthers(String userKey);
}
