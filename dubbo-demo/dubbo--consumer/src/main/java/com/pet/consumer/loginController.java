package com.pet.consumer;

import com.mindflow.api.DemoService;
import com.mindflow.api.LoginService;
import com.mindflow.api.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * Created by liyut on 2017-12-14.
 */
@Controller

public class loginController {
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

}
