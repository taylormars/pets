package com.pet.provider.service;

import com.pet.api.DemoService;
import com.pet.api.model.User;
import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoServiceImpl implements DemoService {
 
	@Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

	@Override
	public User findUserById(long id) {
		
		User user = new User();
		user.setId(id);
		user.setName("Ricky");
		user.setAge(26);
		
		return user;
	}
 
}