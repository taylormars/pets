package com.pet.consumer;

import com.pet.api.DemoService;
import com.pet.api.model.AdoptInfo;
import com.pet.api.model.EducationInfo;
import com.pet.api.userLogin.LoginService;
import com.pet.api.model.UserMain;
import com.pet.consumer.utils.GraphicUtils;
import com.pet.consumer.utils.RedisUtils;
import com.pet.consumer.utils.ResponseJsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by liyut on 2017-12-14.
 */
@Controller

public class LoginController {
    @Resource
    DemoService demoService;
    @Resource
    LoginService login;
    @Resource
    RedisUtils redisUtils;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/test.do")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
       String test=request.getParameter("test");

//       logger.info(redissUtils.ping());
//       redissUtils.set("test1","text");
////        RedissUtils redissUtils =(RedissUtils)factory.getBean("");
////       redisUtils.set("test",test);
//       logger.info(redissUtils.get("test").toString());
        return "1";
    }

    @RequestMapping("/json.do")
    @ResponseBody
    public void json(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put("hh", "sd");
        data2.put("hh2", "sd3");

        data2.put("hh3", "sd2");

        data.put("date", new Date());
        data.put("email", "accountwcx@qq.com");
        data.put("age", 30);
        data.put("name", "csdn");
        data.put("array", new int[]{1, 2, 3, 4});
        data.put("data2", data2);

        ResponseJsonUtils.json(response, data);
    }

    @RequestMapping("/verify.do")
    @ResponseBody
    public void verify(HttpServletRequest request, HttpServletResponse response) {
        logger.info("进入verify方法》《《《");
        // 获得 当前请求 对应的 会话对象

        // 从请求中获得 URI ( 统一资源标识符 )
        String userVerifyCode = request.getParameter("userVerifyCode");
        System.out.println("hello : " + userVerifyCode);
        final int width = 180; // 图片宽度
        final int height = 40; // 图片高度
        final String imgType = "jpeg"; // 指定图片格式 (不是指MIME类型)
        final OutputStream output; // 获得可以向客户端返回图片的输出流
        ;
        try {
            output = response.getOutputStream();
            String code = GraphicUtils.create(width, height, imgType, output);
            // 创建验证码图片并返回图片上的字符串
            logger.info("验证码内容: " + code);
            // 建立 uri 和 相应的 验证码 的关联 ( 存储到当前会话对象的属性中 )
//            redissUtils.set(userVerifyCode,code,60*3);

            redisUtils.set("verify"+userVerifyCode,code,60*3);
            logger.info("生成redis缓存"+"verify"+userVerifyCode);
//            login.insertVerifyCode(userVerifyCode,code);
//            logger.info(String.valueOf(session.getAttribute(userVerifyCode)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/login.do")
    @ResponseBody
    public void loginAdmin(HttpServletRequest request, HttpServletResponse response) {
        logger.info("进入loginUserMain方法》《《《");
        Map<String, Object> data = new HashMap<String, Object>();
        Integer loginRecord=0;
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        logger.info("登录用户名" + userName + "登录密码" + password);

        UserMain userMainExsitName = login.exsitUserName(userName);
        if (null == userMainExsitName) {
            logger.info("用户名不存在存在");
            data.put("code", "4");
        } else {
        try {
            UserMain userMain = login.loginUserMain(userName, password);
            if (null != userMain) {
                logger.info("登录用户名" + userName + "登陆成功");
                data.put("code", "1");
                data.put("userId",userMain.getUserId());
                logger.info("开始写入登录记录userId"+userMain.getUserId()+"IP地址为"+getIpAddr(request));
                loginRecord=login.inserLoginRecord(String.valueOf(userMain.getUserId()),getIpAddr(request));
                if(loginRecord!=0){
                    logger.info("开始写入登录记录userId"+userMain.getUserId()+"IP地址为"+getIpAddr(request)+"成功");
                    redisUtils.set("Ip"+String.valueOf(userMain.getUserId()),getIpAddr(request),60*5);
                    logger.info("将用户ip地址存入redis");
                }else {
                    logger.info("开始写入登录记录userId"+userMain.getUserId()+"IP地址为"+getIpAddr(request)+"失败");
                }
            } else {
                logger.info("登录用户名" + userName + "密码错误");
                data.put("code", "2");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("登录用户名" + userName + "系统异常");
            data.put("code", "3");
        }
        }

        ResponseJsonUtils.json(response, data);
    }


    @RequestMapping("/register")
    @ResponseBody
    public void register(HttpServletRequest request,HttpServletResponse response){
        logger.info("进入register方法》《《《");
        Map<String, Object> data = new HashMap<String, Object>();
//        HttpSession session = request.getSession(true);
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String verify = request.getParameter("verify");
        String userVerifyCode = request.getParameter("userVerifyCode");
        logger.info(userVerifyCode);

        String verfyCodeService =redisUtils.get("verify"+userVerifyCode);
        if (verfyCodeService!=null) {
            UserMain userMainExsitName = login.exsitUserName(userName);
            if (null != userMainExsitName) {
                logger.info("用户名存在");
                data.put("code", "3");
            } else {
                logger.info(userName+"用户名不存在");
                try {
                    if (verify.equals(verfyCodeService)) {
                        UserMain userMain = new UserMain();
                        userMain.setUserName(userName);
                        userMain.setPassword(password);
                        userMain.setUserPicId(1);
                        userMain.setCoin(0);
                        userMain.setDiamond(0);
                        Integer userID = login.register(userMain);
                        if (0 != userID) {
                            logger.info("注册用户名" + userName + "注册成功");
                            data.put("code", 1);
                            data.put("userId", userID);
                        } else {
                            logger.info("注册用户名" + userName + "注册失败.");
                            data.put("code", 2);
                            data.put("userId", userID);
                        }
                    } else {
                        logger.info("注册用户名" + userName + "验证码错误.");
                        data.put("code", 4);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("注册用户名" + userName + "注册失败.");
                    data.put("code", 2);
                }
            }
        }else {
            logger.info("注册用户名" + userName + "注册失败.验证码过期");
            data.put("code", 5);
        }
        ResponseJsonUtils.json(response, data);
    }

    @RequestMapping("/loading")
    @ResponseBody
    public void loading(HttpServletRequest request,HttpServletResponse response){
        logger.info("进入loading方法》《《《");
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        String userId=request.getParameter("userId");
        Map loadingMap=new HashMap();
        try{
        loadingMap=login.queryLoadingInfo(userId);
        data.put("loadingMap",loadingMap);
        data.put("code",1);
        }catch (Exception e)
        {
            e.printStackTrace();
            data.put("code",0);
        }
        ResponseJsonUtils.json(response, data);
    }

    @RequestMapping("adopt")
    @ResponseBody
    public void adopt(HttpServletRequest request,HttpServletResponse response){
        logger.info("进入adopt方法》》》》》《《");
        try {
            request.setCharacterEncoding("utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, Object> data = new HashMap<String, Object>();
        response.setContentType("text/html;charset=utf-8");
        String userId=request.getParameter("userId");
        String petKindId=request.getParameter("petKindId");
        String petNickName=request.getParameter("petNickName");
        Map adoptMap=new HashMap();
        Integer adoptId=0;
        Integer educationId=0;

        adoptMap=login.queryAdoptInfo(userId);
        if(null==adoptMap){
            logger.info("用户Id"+userId+"无领养记录");
            AdoptInfo adoptInfo=new AdoptInfo();
            adoptInfo.setUserId(Integer.valueOf(userId));
            adoptInfo.setPetKindId(Integer.valueOf(petKindId));
            adoptInfo.setPetNickName(petNickName);

            adoptId=login.insertAdoptInfo(adoptInfo);
            if(0!=adoptId){
                logger.info("领养关系创建完成");
                EducationInfo educationInfo=new EducationInfo();
                educationInfo.setUserId(Integer.valueOf(userId));
                educationInfo.setPetId(adoptId);
                educationId=login.insertEducationInfo(educationInfo);
                if (0!=educationId){
                    logger.info("学历系统创建完成");
                    data.put("petKindId",petKindId);
                    data.put("petNickName",petNickName);
                    data.put("code",1);
                }else {
                    logger.info("学历系统创建失败");
                    data.put("code",0);
                }
            }else {
                logger.info("领养关系创建失败");
                data.put("code",0);
            }
        }else {
            logger.info("已存在领养记录");
            data.put("code",0);
        }
        ResponseJsonUtils.json(response,data);
        //http://localhost:8010/adopt.do?userId=asdasd&petKindId=1&petNickName=khdk
    }

    @RequestMapping("online")
    void online(HttpServletRequest request){
        try {
            request.setCharacterEncoding("utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        String userId=request.getParameter("userId");

        if(!getIpAddr(request).equals(redisUtils.get("Ip"+userId))){
                Integer loginRecord=login.inserLoginRecord(userId,getIpAddr(request));
            logger.info(loginRecord.toString());
            }
            redisUtils.set("Ip"+userId,getIpAddr(request),60*5);
    }


    /**
     * 得到IP
     *
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip;
    }


}
