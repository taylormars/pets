package com.pet.consumer;

import com.pet.api.DemoService;
import com.pet.api.LoginService;
import com.pet.api.model.User;
import com.pet.api.model.UserAdmin;
import com.pet.api.model.UserMain;
import com.pet.consumer.utils.GraphicUtils;
import com.pet.consumer.utils.ResponseJsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/test.do")
    @ResponseBody
    public String login() {
        String hello = demoService.sayHello("ricky"); // 执行远程方法
        System.out.println(hello); // 显示调用结果

        User user = demoService.findUserById(15);
        System.out.println(user); // 显示调用结果
        String tis = login.login(8);
        System.out.println(tis + "woshi"); // 显示调用结果
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
//        try {
//            request.setCharacterEncoding("utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        response.setContentType("text/html;charset=utf-8");
//        HttpSession session = request.getSession();
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
            login.insertVerifyCode(userVerifyCode,code);
//            session.setAttribute(userVerifyCode, code);
//            logger.info(String.valueOf(session.getAttribute(userVerifyCode)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/testverify.do")
    @ResponseBody
    public String testverify(HttpServletRequest request,HttpServletResponse response){
        logger.info("进入testverify方法》《《《");

        HttpSession session = request.getSession();
        String userVerifyCode = request.getParameter("userVerifyCode");
        String code=session.getAttribute(userVerifyCode).toString();
        logger.info(code);
        return code;
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public void loginAdmin(HttpServletRequest request, HttpServletResponse response) {
        logger.info("进入loginAdmin方法》《《《");
        Map<String, Object> data = new HashMap<String, Object>();

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

//
//    @RequestMapping("/exsitUserName")
//    @ResponseBody
//    public void exsitUserName(HttpServletRequest request, HttpServletResponse response) {
//        logger.info("进入exsitUserName方法》《《《");
//        Map<String, Object> data = new HashMap<String, Object>();
//        try {
//            request.setCharacterEncoding("utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        response.setContentType("text/html;charset=utf-8");
//        String userName = request.getParameter("userName");
//        UserMain userMain = login.exsitUserName(userName);
//        if (null != userMain) {
//            logger.info("用户名存在");
//            data.put("code", "1");
//        } else {
//            logger.info("用户名不存在");
//            data.put("code", "0");
//        }
//        ResponseJsonUtils.json(response, data);
//    }

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
//        logger.info(String.valueOf(session.getAttribute(userVerifyCode)));
//        logger.info(String.valueOf(session.getAttribute(userVerifyCode)));
//
//        String verfyCodeService = session.getAttribute(userVerifyCode).toString();
        String verfyCodeService =login.queryVerifyCode(userVerifyCode);
        UserMain userMainExsitName = login.exsitUserName(userName);
        if (null != userMainExsitName) {
            logger.info("用户名存在");
            data.put("code", "3");
        } else {
            logger.info("用户名不存在");
        try {
            if (verify.equals(verfyCodeService)) {
                UserMain userMain= new UserMain();
                userMain.setUserName(userName);
                userMain.setPassword(password);
                userMain.setUserPicId(1);
                Integer userID = login.register(userMain);
                if (0!= userID) {
                    logger.info("注册用户名" + userName + "注册成功");
                    data.put("code",1);
                    data.put("userId",userID);
                } else {
                    logger.info("注册用户名" + userName + "注册失败.");
                    data.put("code",2);
                    data.put("userId",userID);
                }
            }else {
                logger.info("注册用户名" + userName + "验证码错误.");
                data.put("code",4);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("注册用户名" + userName + "注册失败.");
            data.put("code",2);
        }
        }
        ResponseJsonUtils.json(response, data);
    }


}
