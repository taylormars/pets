package com.pet.consumer.friends;


import com.pet.api.friends.IFriends;
import com.pet.consumer.petInfo.PetInfoController;
import com.pet.consumer.utils.ResponseJsonUtils;
import org.omg.PortableInterceptor.INACTIVE;
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
public class FriendsController {
    private Logger logger = LoggerFactory.getLogger(FriendsController.class);
@Resource
    IFriends friends;

    @RequestMapping("friendsList")
    @ResponseBody
    public void friendsList(HttpServletRequest request, HttpServletResponse response){
    logger.info(">>>>>>>>>>进入FriendsList方法》》》》》》》》》");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId = request.getParameter("userId");
//        Map educationMap = new HashMap();
        Map<String, Object> data = new HashMap<String, Object>();
        List friendsList = friends.queryFriendsListByuserId(userId);
        data.put("friendsList",friendsList);
        data.put("code","1");
        ResponseJsonUtils.json(response, data);
    }



    @RequestMapping("addFriend")
    @ResponseBody

    public  void  addFriend(HttpServletRequest request,HttpServletResponse response){
        logger.info(">>>>>>>>>进入addFriend方法》》》》》》");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId = request.getParameter("userId");
        String friendName = request.getParameter("friendName");
        Integer status = 0;
        Integer eStatus =0;
        Integer uStatus = 0;
        Map adoptMap = new HashMap();
        Map<String, Object> data = new HashMap<String, Object>();

        status = friends.exsitPetNickName(friendName);
        if (status ==-1){
            data.put("code",2);
        } else {
            List<Map<String,Object>> friendsList = friends.queryFriendsListByuserId(userId);
            for (Map<String,Object> friendsMap : friendsList){
                if (friendName.equals(friendsMap.get("petNickName"))){
                    eStatus =1;
                    data.put("code",3);
                    break;
                }
            }
            if (eStatus==0){
                uStatus = friends.updateFriends(userId,status.toString());
                if (uStatus ==1){
                    data.put("code",1);
                }else {
                    data.put("code",0);
                }
            }
        }
        ResponseJsonUtils.json(response, data);
    }

    @RequestMapping("deleteFriend")
    @ResponseBody
    public void deleteFriend(HttpServletRequest request,HttpServletResponse response){
        logger.info(">>>>>>>>>进入deleteFriend方法》》》》》》");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId = request.getParameter("userId");
        String friendId = request.getParameter("friendId");
        Integer status = 0;
        Map<String, Object> data = new HashMap<String, Object>();
        status = friends.deleteFriend(userId,friendId);
            if (status ==1){
                data.put("code",1);
            }else {
                data.put("code",0);
            }
        ResponseJsonUtils.json(response, data);
    }



}
