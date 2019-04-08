package com.hx.activiti.demo.service.impl;

import com.hx.activiti.demo.service.ActivitiUserService;
import com.hx.activiti.demo.util.SpringUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-02
 */
@Service
public class ActivitiUserServiceImpl  implements ActivitiUserService, java.io.Serializable {


    @Override
    public List<String> getByGroup() {

        IdentityService identityService = SpringUtil.getBean(IdentityService.class);
        List<User> user = identityService.createUserQuery().memberOfGroup("2").list();
        List<String> strs = new ArrayList<>();

        user.forEach(user1 ->
            strs.add(user1.getId())
        );
        return strs;
    }
}
