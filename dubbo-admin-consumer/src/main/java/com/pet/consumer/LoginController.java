package com.pet.consumer;

import com.pet.api.DemoService;
import com.pet.api.LoginService;
import com.pet.api.model.User;
import com.pet.api.model.UserAdmin;
import com.pet.consumer.utils.GraphicUtils;
import com.pet.consumer.utils.ResponseJsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
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
    @RequestMapping("/login.do")
    @ResponseBody
    public String loginAdmin(HttpServletRequest request, HttpServletResponse response) {
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        String verifyCode=request.getParameter("verify");
        HttpSession session = request.getSession(true);
        // 从请求中获得 URI ( 统一资源标识符 )
        String uri = "/verify.do";
       String verfyCodeService=session.getAttribute(uri).toString();
        System.out.println(session.getAttribute(uri));


        try{
       if (verifyCode.equals(verfyCodeService)){
           UserAdmin userAdmin=login.loginAdmin(userName,password);
           if (null!=userAdmin){
               return "1";
           }else {
               return "2";
           }
       }}catch (Exception e) {
            e.printStackTrace();
        }
        return "3";
    }
    @RequestMapping("/json.do")
    @ResponseBody
    public void json(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put("hh","sd");
        data2.put("hh2","sd3");

        data2.put("hh3","sd2");

        data.put("date", new Date());
        data.put("email", "accountwcx@qq.com");
        data.put("age", 30);
        data.put("name", "csdn");
        data.put("array", new int[]{1,2,3,4});
        data.put("data2", data2);

        ResponseJsonUtils.json(response, data);
    }

    @RequestMapping("/verify.do")
    @ResponseBody
    public void verify(HttpServletRequest request, HttpServletResponse response) {
        // 获得 当前请求 对应的 会话对象
        HttpSession session = request.getSession();

        // 从请求中获得 URI ( 统一资源标识符 )
        String uri = request.getRequestURI();
        System.out.println("hello : " + uri);

        final int width = 180; // 图片宽度
        final int height = 40; // 图片高度
        final String imgType = "jpeg"; // 指定图片格式 (不是指MIME类型)
        final OutputStream output; // 获得可以向客户端返回图片的输出流
        try {
            output = response.getOutputStream();
            String code = GraphicUtils.create(width, height, imgType, output);
            // 创建验证码图片并返回图片上的字符串
            System.out.println("验证码内容: " + code);

            // 建立 uri 和 相应的 验证码 的关联 ( 存储到当前会话对象的属性中 )
            session.setAttribute(uri, code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // (字节流)

        System.out.println(session.getAttribute(uri));

}

}
