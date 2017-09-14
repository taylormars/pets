package com.mindflow.dubbo.consumer;

import com.mindflow.api.DemoService;
import com.mindflow.api.LoginService;
import com.mindflow.api.model.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Dubbo Consumer client
 *
 */
public class App {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				 "classpath:applicationContext.xml");
		context.start();

		DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
		LoginService login = (LoginService) context.getBean("login"); // 获取远程服务代理

		String hello = demoService.sayHello("ricky"); // 执行远程方法
		System.out.println(hello); // 显示调用结果
		
		User user = demoService.findUserById(15);
		System.out.println(user); // 显示调用结果
		String tis=login.login(8);
		System.out.println(tis+"woshi"); // 显示调用结果

		try {
			System.in.read();	// 按任意键退出
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
