package com.hx.activiti.demo.controller;

import com.hx.activiti.demo.model.vo.ActivitiProcessModel;
import com.hx.activiti.demo.service.ActivitiService;
import com.hx.activiti.demo.util.HxException;
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
@RequestMapping("/activiti")
public class ActivitiController {

    @Autowired
    private ActivitiService activitiService;

    @RequestMapping("processList")
    public HxResponse<List<ActivitiProcessModel>> getList() {
        return new HxResponse<>(activitiService.getProcessDefinition());
    }

    @RequestMapping("startForm")
    public HxResponse<Object> getStartForm(@RequestParam("id") String procId) throws Exception {
        return new HxResponse<>(activitiService.getStartFormData(procId));
    }

    @RequestMapping("startWorkflow")
    public HxResponse startWorkflow(@RequestParam("id") String procId,
                                    @RequestParam("data") String data,
                                    @RequestParam(value = "listData", required = false) String listData,
                                    @RequestParam(value = "extraData", required = false) String extraData){
        activitiService.startWorkFlow(procId, data, listData, extraData);
        return new HxResponse<>();
    }

    @RequestMapping("taskForm")
    public HxResponse<Object> getTaskForm(@RequestParam("id") String taskId) throws Exception {
        return new HxResponse<>(activitiService.getTaskFormData(taskId));
    }

    @RequestMapping("checkWorkFlow")
    public HxResponse checkWorkFlow(@RequestParam("id") String taskId,
                                    @RequestParam("status") Integer status,
                                    @RequestParam("comment") String comment,
                                    @RequestParam(value = "data", required = false) String data,
                                    @RequestParam(value = "listData", required = false) String listData,
                                    @RequestParam(value = "extraData", required = false) String extraData) throws HxException {
        activitiService.checkWorkFlow(taskId, status, comment, data);
        return new HxResponse<>();
    }
}
