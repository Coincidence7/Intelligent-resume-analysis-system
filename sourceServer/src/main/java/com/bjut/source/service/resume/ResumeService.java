package com.bjut.source.service.resume;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

public interface ResumeService {
    public void getResumeByFilePath(String resumeKey, HttpServletResponse response);
    public byte[] getImage(String resumeKey);
    public HashMap<String, String> addFile(ArrayList<String> paths, ArrayList<String> keys);
}
