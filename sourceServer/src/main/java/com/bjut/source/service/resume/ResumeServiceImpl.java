package com.bjut.source.service.resume;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjut.source.mapper.ResumeMapper;
import com.bjut.source.pojo.file;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
                response.setContentType("application/pdf;charset=UTF-8");
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
    public void getResumeByFilePath(String resumeKey, HttpServletResponse response) {

        HashMap<String, Object> ret = new HashMap<>();
        QueryWrapper<file> qw = new QueryWrapper<>();
        qw.eq("resumekey", resumeKey);
        List<file> resp = resumeMapper.selectList(qw);
        if(resp.isEmpty()){
            ret.put("error_message", "error");
            ret.put("data", "没有找到相应文件");
        }else {
            ret.put("error_message", "success");
            // 获取文件
            String path = resp.get(0).getPath();
            showImg(path.substring(0, path.lastIndexOf('.')) + ".pdf", response);
        }
    }

    @Override
    public byte[] getImage(String resumeKey){
        QueryWrapper<file> qw = new QueryWrapper<>();
        qw.eq("resumekey", resumeKey);
        List<file> resp = resumeMapper.selectList(qw);
        if(resp.isEmpty()){

        }else {
            try{
                // 获取文件
                String path = resp.get(0).getPath();
                File file = new File(path);
                FileInputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes, 0, inputStream.available());
                return bytes;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new byte[0];


    }

    @Override
    public HashMap<String, String> addFile(String path) {

        file file = new file();
        file.setPath(path);
        resumeMapper.insert(file);
        HashMap<String, String> ret = new HashMap<>();
        ret.put("error_message", "success");
        return ret;
    }
}
