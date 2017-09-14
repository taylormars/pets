package com.mindflow.api;

import com.mindflow.api.model.User;

public interface DemoService {
 
	public String sayHello(String name);
 
    public User findUserById(long id);
}