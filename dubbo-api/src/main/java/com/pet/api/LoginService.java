package com.pet.api;

import com.pet.api.model.UserAdmin;

/**
 * Created by liyut on 2017-09-14.
 */
public interface LoginService {

    public String login(int id);

    public UserAdmin loginAdmin(String userName,String password);

}
