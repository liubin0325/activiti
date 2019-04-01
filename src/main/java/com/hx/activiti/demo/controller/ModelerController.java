package com.hx.activiti.demo.controller;

import com.hx.activiti.demo.service.ActivitiService;
import com.hx.activiti.demo.util.HxResponse;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/models")
public class ModelerController {

    @Autowired
    private ActivitiService activitiService;

    @RequestMapping("list")
    public HxResponse<List<Model>> getList() {
        return new HxResponse<>(activitiService.getModelList());
    }


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void createModel(@RequestParam("name") String name,
                            @RequestParam("desc") String desc,
                            @RequestParam("form") String form,
                            HttpServletResponse response) throws Exception {
        String id = activitiService.createModel(name, desc, form);
        response.sendRedirect("/editor?modelId=" + id);
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public void edit(@RequestParam("id") String id,
                            HttpServletResponse response) throws Exception {
        response.sendRedirect("/editor?modelId=" + id);
    }

    @RequestMapping("deploy")
    public HxResponse deploy(@RequestParam("id") String id) throws Exception {

        activitiService.deployProcess(id);
        return new HxResponse();
    }
}
