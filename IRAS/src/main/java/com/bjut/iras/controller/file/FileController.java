package com.bjut.iras.controller.file;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.bjut.iras.pojo.candidate;
import com.bjut.iras.pojo.result;
import com.bjut.iras.pojo.resume;
import com.bjut.iras.service.candidate.CandidateService;
import com.bjut.iras.service.file.FileService;
import com.bjut.iras.service.result.ResultService;
import com.bjut.iras.service.resume.ResumeService;
import com.bjut.iras.utils.Cal;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    CandidateService candidateService;

    @PostMapping("/upload/")
    public Map<String, String> saveFile(@RequestParam HashMap<String, MultipartFile> files){

        log.log(Level.INFO, "对上传文件进行解析");
        ArrayList<MultipartFile> fileList = new ArrayList<>(files.values());

        return fileService.getUserUploadFiles(files.keySet().toArray(new String[0]), fileList.toArray(new MultipartFile[0]));
    }

    @PostMapping("/parser/result/")
    public Map<String, String> saveResult(@RequestParam HashMap<String, String> data){
        HashMap<String, String> ret = new HashMap<>();

        if("fail".equals(data.get("error_message"))){
            ret.put("error_message", "fail");
            ret.put("reason", "解析错误");
            return ret;
        }
        ArrayList<result> list = new ArrayList<>();
        result res = new result();
        res.setResumekey(Integer.parseInt(data.get("resumekey")));
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

        JSONObject jsonObject = JSON.parseArray(data.get("parseresult")).getJSONObject(0);

        // 将分析结果加入 candidate 表
        candidate candidate = new candidate();
        JSONArray age = jsonObject.getJSONArray("年龄");
        if(age.size() == 0){
            candidate.setCandage(Cal.CalAge(""));
        }else {
            candidate.setCandage(Cal.CalAge(age.getString(0)));
        }

        JSONArray name = jsonObject.getJSONArray("姓名");
        if(name.size() == 0){
            candidate.setCandname("");
        }else {
            candidate.setCandname(name.getString(0));
        }

        JSONArray gender = jsonObject.getJSONArray("性别");
        if(gender.size() == 0){
            candidate.setCandgender("男");
        }else {
            candidate.setCandgender(gender.getString(0));
        }

        JSONArray email = jsonObject.getJSONArray("邮箱");
        if(gender.size() == 0){
            candidate.setCandemail("");
        }else {
            candidate.setCandemail(email.getString(0));
        }
        JSONArray award = jsonObject.getJSONArray("所获奖项");
        if(award.size() == 0){
            candidate.setCandemail("");
        }else {
            candidate.setCandemail(award.getString(0));
        }
        JSONArray tele = jsonObject.getJSONArray("电话");
        if(tele.size() == 0){
            candidate.setCandtel("");
        }else {
            candidate.setCandtel(tele.getString(0));
        }
        JSONArray po = jsonObject.getJSONArray("政治面貌");
        if(po.size() == 0){
            candidate.setCandpo("");
        }else {
            candidate.setCandpo(po.getString(0));
        }
        JSONArray address = jsonObject.getJSONArray("住址");
        if(address.size() == 0){
            candidate.setCandloc("");
        }else {
            candidate.setCandloc(address.getString(0));
        }
        JSONArray school = jsonObject.getJSONArray("学校");
        if(school.size() == 0){
            candidate.setCandschool("");
        }else {
            candidate.setCandschool(school.getString(0));
        }
        JSONArray skill = jsonObject.getJSONArray("技能");
        if(skill.size() == 0){
            candidate.setCandskill("");
        }else {
            candidate.setCandskill(skill.getString(0));
        }
        JSONArray target = jsonObject.getJSONArray("目标岗位");
        if(target.size() == 0){
            candidate.setCandtarget("");
        }else {
            candidate.setCandtarget(target.getString(0));
        }
        JSONArray title = jsonObject.getJSONArray("学历");
        if(title.size() == 0){
            candidate.setCandtitle("");
        }else {
            candidate.setCandtitle(title.getString(0));
        }
        JSONArray self = jsonObject.getJSONArray("自我评价");
        if(self.size() == 0){
            candidate.setCandeval("");
        }else {
            candidate.setCandeval(self.getString(0));
        }
        JSONArray work_date = jsonObject.getJSONArray("工作日期");
        if(work_date.size() == 0){
            candidate.setCandjob(0);
        }else {
            candidate.setCandjob(Cal.CalWorkYear(work_date.toString()));
        }
        candidate.setCandresume(res.getResumekey());
        ArrayList<candidate> candidates = new ArrayList<>();
        candidates.add(candidate);
        candidateService.writeCandidate(candidates);

        return ret;
    }
}
