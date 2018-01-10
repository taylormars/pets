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
  }
