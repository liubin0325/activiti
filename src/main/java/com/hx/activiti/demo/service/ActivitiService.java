package com.hx.activiti.demo.service;

import com.hx.activiti.demo.model.vo.ActivitiProcessModel;
import com.hx.activiti.demo.util.HxException;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @description:
 * @author: liubin
 * @date: 2019-03-28
 */
public interface ActivitiService {
    /**
     * 获取模型列表
     *
     * @return
     */
    List<Model> getModelList();

    /**
     * @return
     */
    List<Deployment> getDeployment();

    /**
     * 获取已部署列表
     *
     * @return
     */
    List<ActivitiProcessModel> getProcessDefinition();

    /**
     * 创建流程图
     *
     * @param name
     * @param desc
     * @param form
     * @return
     * @throws HxException
     */
    String createModel(String name, String desc, String form) throws HxException;

    /**
     * 部署流程图
     *
     * @param id
     * @throws Exception
     */
    void deployProcess(String id) throws Exception;

    /**
     * 获取流程表单
     *
     * @param procId
     * @return
     * @throws Exception
     */
    Object getStartFormData(String procId) throws Exception;

    /**
     * 获取流程表单
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    Object getTaskFormData(String taskId) throws Exception;
    @Deprecated
    List<Task> getTaskList();

    /**
     * 启动流程 TODO 返回内容
     *
     * @param procId    流程ID
     * @param formData  表单内容
     * @param listData  list表单内容
     * @param extraData 扩展数据
     */
    void startWorkFlow(String procId, String formData, String listData, String extraData);


    /**
     * 审核流程
     *
     * @param taskId
     * @param status   1 同意 2 退回上一步 3 退回第一步
     * @param comments
     */
    void checkWorkFlow(String taskId, Integer status, String comments, String formData) throws HxException;

}
