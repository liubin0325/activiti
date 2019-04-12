package com.hx.activiti.demo.controller;

import com.hx.activiti.demo.model.vo.ActivitiProcessModel;
import com.hx.activiti.demo.service.ActivitiService;
import com.hx.activiti.demo.util.HxException;
import com.hx.activiti.demo.util.HxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                                    @RequestParam(value = "extraData", required = false) String extraData,
                                    @RequestParam(value = "inst_key", required = false) String inst_key,
                                    @RequestParam(value = "keyvalue", required = false) String keyvalue){
        activitiService.startWorkFlow(procId, data, listData, extraData, inst_key, keyvalue);
        return new HxResponse<>();
    }

    @RequestMapping("taskForm")
    public HxResponse<Map> getTaskForm(@RequestParam("id") String taskId) throws Exception {
        Map<String, Object> map = new HashMap<>(2);
        map.put("form", activitiService.getTaskFormData(taskId));
        map.put("comments", activitiService.getTaskComments(taskId));
        return new HxResponse<>(map);
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


    @RequestMapping("taskImage")
    public void getFlowImgByInstantId(@RequestParam("id") String processInstanceId,
                                      HttpServletResponse response) throws Exception{
        InputStream stream = activitiService.getFlowImgByInstantId(processInstanceId);
        response.setContentType("image/jpeg");//设置输出流内容格式为图片格式
        response.setCharacterEncoding("utf-8");
        OutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        while ((len = stream.read(b, 0, 1024)) != -1) {
            outputStream.write(b, 0, len);
        }
        stream.close();
        outputStream.close();
    }
}
