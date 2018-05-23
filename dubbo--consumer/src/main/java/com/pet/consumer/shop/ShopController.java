package com.pet.consumer.shop;


import com.pet.api.shop.IShop;
import com.pet.api.userLogin.LoginService;
import com.pet.consumer.petInfo.ToolInfoController;
import com.pet.consumer.utils.ResponseJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
public class ShopController {
    private Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Resource
    IShop shop;
    @Resource
    LoginService login;
    @RequestMapping("shop")
    @ResponseBody
    public void queryShopOnSellProduct(HttpServletRequest request, HttpServletResponse response){
        logger.info(">>>>>>>>>进入queryShopOnSellProduct方法》》》》》》》》");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> data = new HashMap<String, Object>();
        try{
            List<Map<String,Object>> productMap = shop.queryShopOnSellProduct();
            data.put("productMap",productMap);
            data.put("code",1);
        }catch(Exception e){
            e.printStackTrace();
            data.put("code",0);
        }
        ResponseJsonUtils.json(response, data);
    }


    @RequestMapping("buy")
    @ResponseBody
    public void buyProduct(HttpServletRequest request, HttpServletResponse response){
        logger.info(">>>>>>>>>进入buyProduct方法》》》》》》》》");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId = request.getParameter("userId");
        String toolId = request.getParameter("toolId");
        String sums = request.getParameter("sums");

        Map<String, Object> data = new HashMap<String, Object>();
        try{
           Map<String,Object> userInfoMap = login.queryLoadingInfo(userId);
           Map<String,Object> toolInfoMap = shop.queryShopInfoByToolId(toolId);
           Integer userCoin = (Integer) userInfoMap.get("coin");
           Integer toolCoin = (Integer) toolInfoMap.get("productPrice");
           if (userCoin<toolCoin*Integer.parseInt(sums)){
               data.put("code",2);
           }else if ("e".equals(toolInfoMap.get("sums"))){
                int status = shop.updateUserCoin(userId,toolCoin*Integer.parseInt(sums));
                if (status==1){
                    status = shop.insertToolRecord(userId,toolId,sums);
                    if (status==1){
                        data.put("code",1);
                    }else {
                        data.put("code",0);
                    }
                }else {
                    data.put("code",0);
                }
           }else if(Integer.parseInt(toolInfoMap.get("sums").toString())<Integer.parseInt(sums)){
               data.put("code",3);
           }else {
               int psum = shop.updateProductSums(sums, toolId);
               if (psum == 1) {
                   int status = shop.updateUserCoin(userId, toolCoin * Integer.parseInt(sums));
                   if (status == 1) {
                       status = shop.insertToolRecord(userId, toolId,sums);
                       if (status == 1) {
                           data.put("code", 1);
                       } else {
                           data.put("code", 0);
                       }
                   } else {
                       data.put("code", 0);
                   }
               }else {
                   data.put("code", 0);
               }
           }
        }catch(Exception e){
            e.printStackTrace();
            data.put("code",0);
        }
        ResponseJsonUtils.json(response, data);
    }


    @RequestMapping("coin")
    @ResponseBody
    public void coin(HttpServletRequest request,HttpServletResponse response){
        logger.info(">>>>>>>>>进入coin方法》》》》》》》》");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId = request.getParameter("userId");
        Map<String, Object> data = new HashMap<String, Object>();
        try{
            int coin = shop.queryCoinByUserId(userId);
            data.put("coin",coin);
            data.put("code",1);
        }catch(Exception e){
            e.printStackTrace();
            data.put("code",0);
        }
        ResponseJsonUtils.json(response, data);

    }





}
