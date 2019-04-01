package com.hx.activiti.demo.service;

import com.google.gson.Gson;
import com.hx.activiti.demo.DemoApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import static org.junit.Assert.*;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-28
 */
public class ActivitiServiceTest extends DemoApplicationTests {

    @Autowired
    private ActivitiService activitiService;

    private Gson gson = new Gson();

    @Test
    public void getModelList() {
        System.out.println(gson.toJson(activitiService.getModelList()));
    }

    @Test
    public void getDeployment() {
        System.out.println(gson.toJson(activitiService.getDeployment()));
    }

    @Test
    public void getProcessDefinition() {
        System.out.println(gson.toJson(activitiService.getProcessDefinition()));
    }

    @Test
    public void createModel() throws Exception{
        Object o  = activitiService.getTaskFormData("50005");
        System.out.println(o);
    }

    @Test
    public void deployProcess() {
    }
}