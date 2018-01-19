package com.pet.consumer.task;

//import com.pet.api.petInfo.IPetInfo;

import com.pet.api.petInfo.IPetInfo;
import com.pet.api.userLogin.LoginService;
import com.pet.consumer.utils.ResponseJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

//@Component
//@Lazy(value=false)
public class PetInfoTask {
    private Logger logger = LoggerFactory.getLogger(PetInfoTask.class);
    @Resource
    IPetInfo petInfo;

    //    @Scheduled(cron = "0 0/1 * * * ?")
    public void petInfoUpate() {
        logger.info("-----宠物状态自动削减定时器开始-----");

        try {
            petInfo.updatePetInfo();
            logger.info("-----宠物状态自动削减定时器结束-----");

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("-----宠物状态自动削减定时器异常-----");
        }
    }

}
