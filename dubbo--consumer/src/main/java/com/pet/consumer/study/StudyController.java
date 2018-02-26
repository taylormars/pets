package com.pet.consumer.study;

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
import java.util.Set;

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

//        Set<String> redisArgs = redis.keys("study*");
//        logger.info(redisArgs.toString());
        Map<String, Object> data = new HashMap<String, Object>();
        if (redis.exists("study" + userId)) {
            logger.info("宠物正在学习中");
            data.put("code", "0");
        } else {
            Map petInfo = login.queryAdoptInfo(userId);
            if (petInfo != null) {
                int hunger = Integer.valueOf(petInfo.get("hunger").toString());
                int tired = Integer.valueOf(petInfo.get("tired").toString());
                if (studyTime.equals("30") || studyTime.equals("60") || studyTime.equals("90") || studyTime.equals("120")) {
                    if (hunger < 4 * Integer.valueOf(studyTime) / 30) {
                        data.put("code", 2);
                    } else if (tired < 4 * Integer.valueOf(studyTime) / 30) {
                        data.put("code", 3);
                    } else {
                        int status=study.updateStudyStatus(userId, 4 * Integer.valueOf(studyTime) / 30);
                        if (status==1){
                        redis.set("study" + userId, lesson + "," + 4 * Integer.valueOf(studyTime) / 30, 60 * Integer.valueOf(studyTime));
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
            } else {
                data.put("code", 0);
            }
        }
        ResponseJsonUtils.json(response, data);
    }

}
