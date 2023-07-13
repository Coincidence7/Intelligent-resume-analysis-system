package com.bjut.source.service.resume;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjut.source.mapper.ResumeMapper;
import com.bjut.source.pojo.resume;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;


@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    ResumeMapper resumeMapper;



    public void showImg(String path, HttpServletResponse response){

        if(path != null && !path.equals("")){

            File file = new File(path);
            try {
                response.setHeader("Content-Disposition", "inline; filename*=UTF-8''" + URLEncoder.encode(file.getName(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            ServletOutputStream os = null;
            try {
                os = response.getOutputStream();
                os.write(FileUtils.readFileToByteArray(file));
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    close(os);
                }
            }
        }
    }
    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public HashMap<String, Object> getResumeByFilePath(String path, HttpServletResponse response) {
        showImg(path, response);
        HashMap<String, Object> ret = new HashMap<>();
        QueryWrapper<resume> qw = new QueryWrapper<>();
        qw.eq("path", path);
        List<resume> resp = resumeMapper.selectList(qw);
        if(resp.isEmpty()){
            ret.put("error_message", "error");
            ret.put("data", "没有找到相应文件");
        }else {
            ret.put("error_message", "success");
            // 获取文件

            File file = new File(path);
            ret.put("data", file);
        }
        return ret;
    }
}
