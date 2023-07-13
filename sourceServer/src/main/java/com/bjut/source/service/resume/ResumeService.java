package com.bjut.source.service.resume;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public interface ResumeService {
    public HashMap<String, Object> getResumeByFilePath(String path, HttpServletResponse response);
}
