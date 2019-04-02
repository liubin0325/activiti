package com.hx.activiti.demo.service.impl;

import com.hx.activiti.demo.service.ActivitiUserService;
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
        List<String> strs = new ArrayList<>();
        strs.add("领导_1");
        strs.add("领导_2");
        strs.add("领导_3");
        return strs;
    }
}
