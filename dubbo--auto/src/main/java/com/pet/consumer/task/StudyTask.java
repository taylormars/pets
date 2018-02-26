package com.pet.consumer.task;

import com.pet.api.study.IStudy;
import com.pet.api.userLogin.LoginService;
import com.pet.consumer.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Set;

public class StudyTask {

    private Logger logger = LoggerFactory.getLogger(StudyTask.class);

    @Resource
    RedisUtils redis;

    @Resource
    IStudy study;


    public void  studyOne(){
        logger.info("------==学习进度监控定时器开始-----");
        Set<String> redisArg=redis.keys("study*");
        if (redisArg.size()!=0){
            String redisArgs = redisArg.toString().substring(1,redisArg.toString().length()-1);
        String redisArgss[]= redisArgs.split(",");
        logger.info(String.valueOf(redisArgss.length));
        for(int i =0; i<redisArgss.length;i++){
          if (redis.ttl(redisArgss[i])<16){
              logger.info(redisArgss[i]);
              String userId=redisArgss[i].trim().substring(5,redisArgss[i].trim().length());
              String lAS[]=redis.get(redisArgss[i].trim()).split(",");
              String lesson = lAS[0];
              String studyTime = lAS[1];
              logger.info("用户"+userId+"学习"+lesson+studyTime+"min即将完成");
            int status= study.updateStudyInfo(userId,lesson, 2 * Integer.valueOf(studyTime) / 30);
             if(status==1){
                 logger.info("用户"+userId+"学习"+lesson+studyTime+"min完成 ");
             }else {
                 logger.info("用户"+userId+"学习"+lesson+studyTime+"min异常");
             }
          }
        }
        }

            logger.info("------==学习进度监控定时器结束-----");

    }

}
