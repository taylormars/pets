package com.pet.consumer.fight;


import com.pet.api.model.PetFightInfo;
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
import java.util.Random;

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

        PetFightInfo userAtt =new PetFightInfo();
        PetFightInfo enemyAtt =new PetFightInfo();

        userAtt.setUserId(userAttribute.get("userId").toString());
        userAtt.setHp((((Integer) userAttribute.get("strength"))*5+100));
        userAtt.setAttack(((Integer) userAttribute.get("strength"))+10);
        userAtt.setSkill(((Integer) userAttribute.get("intelligence"))+20);
        userAtt.setSpeed(((Integer) userAttribute.get("swift"))+10);

        enemyAtt.setUserId(enemyAttribute.get("userId").toString());
        enemyAtt.setHp((((Integer) enemyAttribute.get("strength"))*5+100));
        enemyAtt.setAttack(((Integer) enemyAttribute.get("strength"))+10);
        enemyAtt.setSkill(((Integer) enemyAttribute.get("intelligence"))+20);
        enemyAtt.setSpeed(((Integer) enemyAttribute.get("swift"))+10);


        int j = 1;
        int k = 1;
        for(int i=0;i<i+1;i++){
            if(userAtt.getSpeed() >=enemyAtt.getSpeed()){
                if(userAtt.getSpeed() *j >= 100){
                    attack(userAtt,enemyAtt);
                    j = 1;
                    if(!liveOrDie(enemyAtt)){
                        break;
                    }
                }
                if(enemyAtt.getSpeed()*k >= 100){
                    attack(enemyAtt,userAtt);
                    k = 1;
                    if(!liveOrDie(userAtt)){
                        break;
                    }
                }
            }else{
                if(enemyAtt.getSpeed()*j >= 100){
                    attack(enemyAtt,userAtt);
                    j = 1;
                    if(!liveOrDie(userAtt)){
                        break;
                    }
                }
                if(userAtt.getSpeed() *k >= 100){
                    attack(userAtt,enemyAtt);
                    k = 1;
                    if(!liveOrDie(enemyAtt)){
                        break;
                    }
                }
            }
            j++;
            k++;
//            if(userAtt.getHp()<=0){
//                System.out.println("a win");
//                break;
//            }else if(enemyAtt.getHp()<=0){
//                System.out.println("b win");
//                break;
//            }

        }


        ResponseJsonUtils.json(response, data);
    }

    public void attack(PetFightInfo p1, PetFightInfo p2){
        Random rand = new Random();
      int aORS =  rand.nextInt(2);
        if (aORS ==0){
            p2.setHp(p2.getHp()-p1.getAttack());
            System.out.println(p1.getUserId()+"对"+p2.getUserId()+"造成了"+p1.getAttack()+"点伤害");
            System.out.println(p1.getUserId()+"当前血量为:"+p1.getHp());
            System.out.println(p2.getUserId()+"当前血量为:"+p2.getHp());
        }else {
            p2.setHp(p2.getHp()-p1.getSkill());
            System.out.println(p1.getUserId()+"对"+p2.getUserId()+"造成了"+p1.getSkill()+"点伤害");
            System.out.println(p1.getUserId()+"当前血量为:"+p1.getHp());
            System.out.println(p2.getUserId()+"当前血量为:"+p2.getHp());
        }

    }
    public boolean liveOrDie(PetFightInfo p2){
        if(p2.getHp()<=0) {
            System.out.println(p2.getUserId()+"已死亡"+p2.getHp());
            return false;
        }
        return true;
    }


}
