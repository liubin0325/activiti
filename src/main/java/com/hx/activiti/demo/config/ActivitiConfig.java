package com.hx.activiti.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hx.activiti.demo.activiti.GlobalEventListener;
import com.hx.activiti.demo.activiti.HxFormEngine;
import org.activiti.engine.*;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.form.FormEngine;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.rest.common.application.ContentTypeResolver;
import org.activiti.rest.common.application.DefaultContentTypeResolver;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ActivitiConfig {
    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource,
                                                                 PlatformTransactionManager transactionManager) {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setDatabaseType("mysql");
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        processEngineConfiguration.setTransactionManager(transactionManager);
        List<FormEngine> customFormEngines = new ArrayList<>();
        customFormEngines.add(new HxFormEngine());
        processEngineConfiguration.setCustomFormEngines(customFormEngines);
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        //ID 高并发生成策略
//        processEngineConfiguration.setIdGenerator(new StrongUuidGenerator());
        List<ActivitiEventListener> listeners = new ArrayList<>();
        listeners.add(getGlobalEventListener());
        processEngineConfiguration.setEventListeners(listeners);

        return processEngineConfiguration;
    }

    //流程引擎，与spring整合使用factoryBean
    @Bean
    public ProcessEngineFactoryBean processEngine(ProcessEngineConfiguration processEngineConfiguration) {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        return processEngineFactoryBean;
    }

    //八大接口
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    public FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Bean
    public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine) {
        return processEngine.getDynamicBpmnService();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public HxFormEngine formEngine() {
        return new HxFormEngine();
    }


    //rest service
    @Bean
    public RestResponseFactory restResponseFactory() {
        return new RestResponseFactory();
    }

    @Bean
    public ContentTypeResolver contentTypeResolver() {
        return new DefaultContentTypeResolver();
    }

    /**
     * 全局事件监听配置
     *
     * @return
     */
    private ActivitiEventListener getGlobalEventListener() {
        GlobalEventListener listener = new GlobalEventListener();
        Map<String, String> handlers = new HashMap<>();
        handlers.put("TASK_CREATED", "taskCreateListener");
        handlers.put("TASK_COMPLETED", "taskCompleteListener");
        handlers.put("TASK_ASSIGNED", "taskAssignedListener");
        handlers.put("PROCESS_COMPLETED", "processCompleteListener");
        listener.setHandlers(handlers);
        return listener;
    }

}
