package com.pet.provider.service;

import com.pet.api.HelloService;
import org.springframework.stereotype.Service;

@Service("helloService")
public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHi(String msg) {
		
		return "Hello "+msg;
	}

}
