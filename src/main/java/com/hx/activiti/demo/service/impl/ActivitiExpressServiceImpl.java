package com.hx.activiti.demo.service.impl;

import com.hx.activiti.demo.service.ActivitiExpressService;
import com.hx.activiti.demo.util.SpringUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.Execution;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-02
 */
@Service
public class ActivitiExpressServiceImpl implements ActivitiExpressService, java.io.Serializable {

    /**
     * nrOfInstances：实例总数
     *
     * nrOfActiveInstances：当前活动的，比如，还没完成的，实例数量。 对于顺序执行的多实例，值一直为1。
     *
     * nrOfCompletedInstances：已经完成实例的数目。
     * @return
     */
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

    @Override
    public boolean testExclusiveGateWay(String key, String value, Execution execution) {
        execution.getProcessInstanceId();

        if("100".equals(value))
            return false;
        return true;


    }
}
