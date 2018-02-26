package com.pet.consumer.other;


import com.pet.consumer.petInfo.PetInfoController;
import com.pet.consumer.utils.ResponseJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pet.api.other.IOther;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OtherController {

    private Logger logger = LoggerFactory.getLogger(OtherController.class);

    @Resource
    IOther other;



    @RequestMapping("dailyWords")
    @ResponseBody
    public void dailyWords (HttpServletRequest request, HttpServletResponse response){
        logger.info("---进入dailtWords方法----");
        Map<String, Object> data = new HashMap<String, Object>();
        try{
        Map<String,Object> dailyWords=other.queryDailyWords();
        data.put("code",1);
        data.put("dailyWords",dailyWords);
        }catch (Exception e){
            data.put("code","0");
        }
        ResponseJsonUtils.json(response, data);
    }


}
