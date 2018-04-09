package com.pet.consumer.studyWork;

import com.pet.api.study.IStudy;
import com.pet.api.userLogin.LoginService;
import com.pet.consumer.utils.RedisUtils;
import com.pet.consumer.utils.ResponseJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StudyController {
    private Logger logger = LoggerFactory.getLogger(StudyController.class);

    @Resource
    RedisUtils redis;

    @Resource
    IStudy study;

    @Resource
    LoginService login;

    @RequestMapping("studyOne")
    @ResponseBody
    public void studyOne(HttpServletRequest request, HttpServletResponse response) {

        logger.info("--===--进入studyOne方法---===");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId = request.getParameter("userId");
        String lesson = request.getParameter("lesson");
        String studyTime = request.getParameter("studyTime");
        Integer hungerStatus=4;
        Integer tiredStatus=4;
        String benefit = "coin";
        Integer reward = 10;
        Integer cost = 8;
        Integer code = 1;
        Integer wC = 0;

//        Set<String> redisArgs = redis.keys("studyWork*");
//        logger.info(redisArgs.toString());
        Map<String, Object> data = new HashMap<String, Object>();
        if (redis.exists("studyWork" + userId)) {
            logger.info("宠物正在学习工作中");
            String lAS[]=redis.get("studyWork"+userId).split(",");
            String lessonW = lAS[0];
            Long studyWTime =redis.ttl("studyWork"+userId);
            data.put("code", "5");
            data.put("lessonW",lessonW);
            data.put("studyWTime",studyWTime);
        } else {
            Map petInfo = login.queryAdoptInfo(userId);
            if (petInfo != null) {
                if(lesson.equals("w11")||lesson.equals("w21")||lesson.equals("w31")){
                    hungerStatus = 4;
                    tiredStatus = 4;
                    benefit = "coin";
                    reward =  15;
                }else if(lesson.equals("w12")||lesson.equals("w22")||lesson.equals("w32")){
                    hungerStatus = 5;
                    tiredStatus = 5;
                    benefit = "coin";
                    reward =  25;
                }else if (lesson.equals("w13")||lesson.equals("w23")||lesson.equals("w33")){
                    hungerStatus = 6;
                    tiredStatus = 6;
                    benefit = "coin";
                    reward =  35;
                }else if (lesson.equals("chinese")){
                    benefit = "intelligence";
                    reward =  1;
                    wC = 1;
                }else if (lesson.equals("math")){
                    benefit = "swift";
                    reward =  1;
                    wC = 1;
                }else if (lesson.equals("politics")){
                    benefit = "all";
                    reward =  1;
                    wC = 1;
                }else if (lesson.equals("pe")){
                    benefit = "strength";
                    reward =  1;
                    wC = 1;
                }
                Map<String,Object> userInfoMap = login.queryLoadingInfo(userId);
                Integer userCoin = (Integer) userInfoMap.get("coin");
                if (wC==1){
                    if (Integer.valueOf(petInfo.get(lesson).toString())>79&&Integer.valueOf(petInfo.get(lesson).toString())<160){
                      cost =12;
                    }else   if (Integer.valueOf(petInfo.get(lesson).toString())>159&&Integer.valueOf(petInfo.get(lesson).toString())<200){
                        cost = 20;
                    }else if (Integer.valueOf(petInfo.get(lesson).toString())>199){
                        cost = 35;
                    }
                  if (cost * Integer.valueOf(studyTime) / 30>userCoin){
                        code =6;
                      data.put("code", "6");
                  }
                }
                if (code ==1){
                int hunger = Integer.valueOf(petInfo.get("hunger").toString());
                int tired = Integer.valueOf(petInfo.get("tired").toString());
                if (studyTime.equals("30") || studyTime.equals("60") || studyTime.equals("90") || studyTime.equals("120")) {
                    if (hunger < hungerStatus * Integer.valueOf(studyTime) / 30) {
                        data.put("code", 2);
                    } else if (tired < tiredStatus * Integer.valueOf(studyTime) / 30) {
                        data.put("code", 3);
                    } else {
                        int status=study.updateStudyStatus(userId, hungerStatus * Integer.valueOf(studyTime) / 30,cost * Integer.valueOf(studyTime) / 30);
                        if (status==1){
                        redis.set("studyWork" + userId, lesson + "," +  Integer.valueOf(studyTime) / 30 + "," + benefit + "," +reward , 60 * Integer.valueOf(studyTime));
                        study.insertStudyRecord(userId, lesson, studyTime);
                        logger.info("userId" + userId + "lesson" + lesson + "studyTime" + studyTime);
                        data.put("code", "1");
                        }else {
                            data.put("code", "0");
                        }
                    }
                }else {
                    data.put("code","4");
                }
            }
            } else {
                data.put("code", 0);
            }
        }
        ResponseJsonUtils.json(response, data);
    }

}
