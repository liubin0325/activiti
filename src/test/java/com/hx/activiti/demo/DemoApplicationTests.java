package com.hx.activiti.demo;

import com.google.gson.Gson;
import com.hx.activiti.demo.dao.ActCustomFormDao;
import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ActCustomFormDao formDao;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private FormService formService;

    private Gson gson = new Gson();

    @Test
    public void contextLoads() {
//        identityService.setAuthenticatedUserId("0");
//        taskService.addComment("57507", "57501", "哈哈哈1");
        List<Comment> comments = taskService.getProcessInstanceComments("25001");
        System.out.println(gson.toJson(comments));

//        ((RuntimeServiceImpl)runtimeService).getCommandExecutor().execute(new TaskJumpCmd("65002", "flow4", null, "jump"));
//        runtimeService.g
//        taskService.create

//        Group group = identityService.newGroup("1");
//        group.setName("普通用户");
//        identityService.saveGroup(group);
//
//        Group group1 = identityService.newGroup("2");
//        group1.setName("领导");
//        identityService.saveGroup(group1);
//
//        Group group2 = identityService.newGroup("3");
//        group2.setName("hr");
//        identityService.saveGroup(group2);
//        for(int i = 0;i<10;i++){
//            User user = identityService.newUser(i+"");
//            user.setFirstName("普通用户_"+i);
//            identityService.saveUser(user);
//            identityService.createMembership(i+"", "1");
//
//        }
//
//        for(int i = 11;i<16;i++){
//            User user = identityService.newUser(i+"");
//            user.setFirstName("领导_"+i);
//            identityService.saveUser(user);
//            identityService.createMembership(i+"", "2");
//
//        }
//
//        for(int i = 16;i<20;i++){
//            User user = identityService.newUser(i+"");
//            user.setFirstName("hr_"+i);
//            identityService.saveUser(user);
//            identityService.createMembership(i+"", "3");
//
//        }


//        historyService.createHistoricActivityInstanceQuery().


    }

}
