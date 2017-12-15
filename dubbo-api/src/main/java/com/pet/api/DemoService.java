package com.pet.api;

import com.pet.api.model.User;

public interface DemoService {
 
	public String sayHello(String name);
 
    public User findUserById(long id);
}