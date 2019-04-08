package com.hx.activiti.demo.dao;

import com.hx.activiti.demo.model.ActCustomModelCallback;

/**
 * @description:
 * @author: liubin
 * @date: 2019-04-08
 */
public interface ActCustomModelCallbackDao {
    int save(ActCustomModelCallback customModelCallback);

    ActCustomModelCallback getByModel(String model);
}
