package com.pet.provider.service;

import com.pet.api.LoginService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by liyut on 2017-09-14.
 */
@Service("login")
public class LoginImpl implements LoginService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public String login(int id) {
        String sql="select username,password from user_main where username = 'lyt'";
      //  String sql="select 1";

        //  Object[] args = new Object[0];
       // int list = jdbcTemplate.queryForInt(sql);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> loan : list) {
                String a = loan.get("username").toString();
                String b = loan.get("password").toString();
                if(a.equals("lyt")&& b.equals("123"))
                {
                    return "登陆成功"+id;
                }

            }
       }
//        if (list > 0) {
//
//                    return "登陆成功"+id;
//
//        }
        return "登陆失败"+id;
    }
}
