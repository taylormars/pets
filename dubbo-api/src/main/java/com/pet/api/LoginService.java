package com.pet.api;

import com.pet.api.model.UserAdmin;
import com.pet.api.model.UserMain;

/**
 * Created by liyut on 2017-09-14.
 */
public interface LoginService {

    public String login(int id);

    public UserAdmin loginAdmin(String userName,String password);

    public UserMain loginUserMain(String userName,String password);


    public UserMain exsitUserName(String userName);

    public Integer register(UserMain userMain);

    public void insertVerifyCode(String userVerifyCode,String code);

    public String queryVerifyCode(String userVerifyCode);
}
