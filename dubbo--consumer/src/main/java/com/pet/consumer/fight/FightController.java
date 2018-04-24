package com.pet.consumer.fight;


import com.pet.api.other.IOther;
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
public class FightController {

    private Logger logger = LoggerFactory.getLogger(FightController.class);

    @Resource
    IOther other;

    @Resource
    LoginService login;

    @RequestMapping("fight")
    @ResponseBody
    public void dailyWords (HttpServletRequest request, HttpServletResponse response){
        logger.info("---进入fight方法----");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId = request.getParameter("userId");
        String 	enemyUserId = request.getParameter("enemyUserId");

        Map adoptMap = new HashMap();
        Map<String, Object> data = new HashMap<String, Object>();

        Map<String ,Object> userAttribute = login.queryAdoptInfo(userId);
        Map<String ,Object> enemyAttribute = login.queryAdoptInfo(enemyUserId);



        ResponseJsonUtils.json(response, data);
    }


}
