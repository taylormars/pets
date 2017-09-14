package com.mindflow.motan.provider.serice;

import com.mindflow.api.DemoService;
import com.mindflow.api.model.User;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2017-02-28 23:49
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "hello, "+name;
    }

    @Override
    public User findUserById(long id) {
        User user = new User();
        user.setId(id);
        user.setName("ricky_"+id);
        return user;
    }
}
