package com.hx.activiti.demo.controller;

import com.hx.activiti.demo.model.ActCustomForm;
import com.hx.activiti.demo.service.ActFormService;
import com.hx.activiti.demo.util.HxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-28
 */
@RestController
@RequestMapping("/forms")
public class FormController {

    @Autowired
    private ActFormService formService;

    @RequestMapping("list")
    public HxResponse<List<ActCustomForm>> getList() {
        return new HxResponse<>(formService.getList());
    }


    @RequestMapping("create")
    public HxResponse createForm(@RequestParam("name") String name,
                                 @RequestParam(value = "desc", required = false) String desc) {
        formService.saveActForm(name, 1, desc, 1);
        return new HxResponse();
    }

    @RequestMapping("detail")
    public HxResponse<ActCustomForm> getDetail(@RequestParam("id") String id) {
        return new HxResponse<>(formService.getDetail(id));
    }
}
