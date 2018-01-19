package com.pet.consumer.petInfo;

//import com.pet.api.petInfo.IPetInfo;

import com.pet.api.petInfo.IPetInfo;
import com.pet.api.userLogin.LoginService;
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
public class PetInfoController {
    private Logger logger = LoggerFactory.getLogger(PetInfoController.class);
    @Resource
    LoginService login;
    @Resource
    IPetInfo petInfo;


    @RequestMapping("petInfo")
    @ResponseBody
    public void petInfo(HttpServletRequest request, HttpServletResponse response) {
        logger.info("进入petinfo方法 -------");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId = request.getParameter("userId");
        Map adoptMap = new HashMap();
        Map educationMap = new HashMap();
        Map<String, Object> data = new HashMap<String, Object>();

        adoptMap = login.queryAdoptInfo(userId);
        if (null == adoptMap) {
            logger.info("用户Id" + userId + "无领养记录");
            data.put("code", 0);
        } else {
            logger.info("开始获取宠物教育信息");
            educationMap = petInfo.queryeducation(adoptMap.get("petId").toString());
            if (null != educationMap) {
                logger.info("开始返回宠物信息");
                data.put("petNickName", adoptMap.get("petNickName"));
                data.put("adoptTime", adoptMap.get("adoptTime"));
                data.put("years", adoptMap.get("years"));
                data.put("mood", adoptMap.get("mood"));
                data.put("clean", adoptMap.get("clean"));
                data.put("hunger", adoptMap.get("hunger"));
                data.put("tired", adoptMap.get("tired"));
                data.put("math", educationMap.get("math"));
                data.put("chinese", educationMap.get("chinese"));
                data.put("politics", educationMap.get("politics"));
                data.put("pe", educationMap.get("pe"));
                data.put("code", 1);
            } else {
                logger.info("获取宠物教育信息出错");
                data.put("code", 0);
            }
        }
        ResponseJsonUtils.json(response, data);
    }
}
