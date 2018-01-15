package com.pet.consumer.petInfo;

//import com.pet.api.petInfo.IPetInfo;
import com.pet.api.petInfo.IPetInfo;
import com.pet.api.petInfo.IToolInfo;
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
import java.util.List;
import java.util.Map;

@Controller
public class ToolInfoController {
    private Logger logger = LoggerFactory.getLogger(ToolInfoController.class);
    @Resource
    IToolInfo toolInfo;
    @Resource
    IPetInfo petInfo;


    @RequestMapping("toolInfo")
    @ResponseBody
    public void toolInfo(HttpServletRequest request, HttpServletResponse response){
        logger.info("进入toolInfo方法 -------");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId = request.getParameter("userId");
        Map<String, Object> data = new HashMap<String, Object>();
        logger.info("开始获取宠物道具信息");
        try{
            List<Map<String,Object>> toolMap=toolInfo.querytoolByuserId(userId);
            data.put("toolMap",toolMap);
            data.put("code",1);
            logger.info("获取宠物道具信息成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.info("获取宠物道具失败");
            data.put("code",0);
        }
        ResponseJsonUtils.json(response,data);
    }


    @RequestMapping("toolUse")
    @ResponseBody
    public void toolUse(HttpServletRequest request, HttpServletResponse response){
        logger.info("进入toolUse方法 -------");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId = request.getParameter("userId");
        String toolId =request.getParameter("toolId");
        String sums = request.getParameter("sums");
        Map<String, Object> data = new HashMap<String, Object>();
        Integer status = 0;
        try{
            status = toolInfo.querToolSumsByToolIdUserId(userId,toolId);
            if (status>0){
            status=  toolInfo.updateToolUse(userId,toolId,sums);
            if (status==1){
                logger.info("Step1--用户"+userId+"使用道具成功"+toolId+"次数"+sums);
                Map<String, Object> toolInfo2=  toolInfo.queryToolInfoByToolId(toolId);
                if(null !=toolInfo2){
                    status=toolInfo.updatePetInfoByUseTool(userId,toolInfo2.get("hunger").toString(),toolInfo2.get("clean").toString(),toolInfo2.get("mood").toString(),toolInfo2.get("tired").toString());
                    if (status==1){
                        logger.info("Step2--用户"+userId+"使用道具成功"+toolId+"次数"+sums);
                        data.put("code",1);
                    }else {
                        data.put("code",0);
                        logger.info("用户"+userId+"使用道具失败"+toolId+"次数"+sums);
                    }
                }else {
                    data.put("code",0);
                    logger.info("用户"+userId+"使用道具失败"+toolId+"次数"+sums);
                }
            }else {
                data.put("code",0);
                logger.info("用户"+userId+"使用道具失败"+toolId+"次数"+sums);
            }
            }else {
                data.put("code",2);
                logger.info("用户"+userId+"使用道具失败"+toolId+"已使用完");
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.info("用户"+userId+"使用道具失败"+toolId+"次数"+sums);
            data.put("code",0);
        }
        ResponseJsonUtils.json(response,data);
    }
  }
