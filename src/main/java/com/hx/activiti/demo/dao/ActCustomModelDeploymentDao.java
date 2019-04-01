package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomModelDeployment;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-29
 */
public interface ActCustomModelDeploymentDao {
    int save(ActCustomModelDeployment modelDeployment);

    ActCustomModelDeployment getByDeployment(String deployment);
}
