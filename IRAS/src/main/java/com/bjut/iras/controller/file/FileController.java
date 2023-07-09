package com.bjut.iras.controller.file;

import com.bjut.iras.pojo.result;
import com.bjut.iras.pojo.resume;
import com.bjut.iras.service.file.FileService;
import com.bjut.iras.service.result.ResultService;
import com.bjut.iras.service.resume.ResumeService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Log
@RestController
public class FileController {


    @Autowired
    FileService fileService;

    @Autowired
    private ResumeService resumeService;
    @Autowired
    ResultService resultService;

    @PostMapping("/upload/")
    public Map<String, String> saveFile(@RequestParam HashMap<String, MultipartFile> files){

        log.log(Level.INFO, "对上传文件进行解析");
        ArrayList<MultipartFile> fileList = new ArrayList<>(files.values());

        return fileService.getUserUploadFiles(files.keySet().toArray(new String[0]), fileList.toArray(new MultipartFile[0]));
    }

    @PostMapping("/parser/result/")
    public Map<String, String> saveResult(@RequestParam HashMap<String, String> data){
        HashMap<String, String> ret = new HashMap<>();
        ArrayList<result> list = new ArrayList<>();
        result res = new result();

        res.setResumekey(Integer.parseInt(data.get("resumekey").toString()));
        res.setState(data.get("state"));
        res.setResumename(data.get("resumename"));
        res.setParseresult(data.get("parseresult"));
        res.setCreatedtime(new Timestamp(System.currentTimeMillis()));

        list.add(res);
        // 加结果、改状态
        resultService.writeResult(list);

        resume resume = resumeService.getResumeByKey(res.getResumekey());
        resume.setState(res.getState());

        ret.put("error_message", "success");
        resumeService.updateResumeByKey(resume);

        return ret;
    }
}
