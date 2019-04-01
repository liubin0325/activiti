package com.hx.activiti.demo.activiti;

import com.hx.activiti.demo.activiti.listener.ProcessCompleteListener;
import com.hx.activiti.demo.util.SpringUtil;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: Activiti的全局事件监听器，即所有事件均需要在这里统一分发处理
 * ENGINE_CREATED
 * 监听器监听的流程引擎已经创建完毕，并准备好接受API调用。
 * ENGINE_CLOSED
 * 监听器监听的流程引擎已经关闭，不再接受API调用。
 * ENTITY_CREATED
 * 创建了一个新实体。实体包含在事件中。
 * ENTITY_INITIALIZED
 * 创建了一个新实体，初始化也完成了。如果这个实体的创建会包含子实体的创建，这个事件会在子实体都创建/初始化完成后被触发，这是与ENTITY_CREATED的区别。
 * ENTITY_UPDATED
 * 更新了已存在的实体。实体包含在事件中。
 * ENTITY_DELETED
 * 删除了已存在的实体。实体包含在事件中。
 * ENTITY_SUSPENDED
 * 暂停了已存在的实体。实体包含在事件中。会被ProcessDefinitions, ProcessInstances 和 Tasks抛出。
 * ENTITY_ACTIVATED
 * 激活了已存在的实体，实体包含在事件中。会被ProcessDefinitions, ProcessInstances 和 Tasks抛出。
 * JOB_EXECUTION_SUCCESS
 * 作业执行成功。job包含在事件中。
 * JOB_EXECUTION_FAILURE
 * 作业执行失败。作业和异常信息包含在事件中。
 * JOB_RETRIES_DECREMENTED
 * 因为作业执行失败，导致重试次数减少。作业包含在事件中。
 * TIMER_FIRED
 * 触发了定时器。job包含在事件中。
 * JOB_CANCELED
 * 取消了一个作业。事件包含取消的作业。作业可以通过API调用取消，   任务完成后对应的边界定时器也会取消，在新流程定义发布时也会取消。
 * ACTIVITY_STARTED
 * 一个节点开始执行
 * ACTIVITY_COMPLETED
 * 一个节点成功结束
 * ACTIVITY_SIGNALED
 * 一个节点收到了一个信号
 * ACTIVITY_MESSAGE_RECEIVED
 * 一个节点收到了一个消息。在节点收到消息之前触发。收到后，会触发ACTIVITY_SIGNAL或ACTIVITY_STARTED，这会根据节点的类型（边界事件，事件子流程开始事件）
 * ACTIVITY_ERROR_RECEIVED
 * 一个节点收到了一个错误事件。在节点实际处理错误之前触发。   事件的activityId对应着处理错误的节点。 这个事件后续会是ACTIVITY_SIGNALLED或ACTIVITY_COMPLETE， 如果错误发送成功的话。
 * UNCAUGHT_BPMN_ERROR
 * 抛出了未捕获的BPMN错误。流程没有提供针对这个错误的处理器。   事件的activityId为空。
 * ACTIVITY_COMPENSATE
 * 一个节点将要被补偿。事件包含了将要执行补偿的节点id。
 * VARIABLE_CREATED
 * 创建了一个变量。事件包含变量名，变量值和对应的分支或任务（如果存在）。
 * VARIABLE_UPDATED
 * 更新了一个变量。事件包含变量名，变量值和对应的分支或任务（如果存在）。
 * VARIABLE_DELETED
 * 删除了一个变量。事件包含变量名，变量值和对应的分支或任务（如果存在）。
 * TASK_ASSIGNED
 * 任务被分配给了一个人员。事件包含任务。
 * TASK_CREATED
 * 创建了新任务。它位于ENTITY_CREATE事件之后。当任务是由流程创建时，     这个事件会在TaskListener执行之前被执行。
 * TASK_COMPLETED
 * 任务被完成了。它会在ENTITY_DELETE事件之前触发。当任务是流程一部分时，事件会在流程继续运行之前，   后续事件将是ACTIVITY_COMPLETE，对应着完成任务的节点。
 * TASK_TIMEOUT
 * 任务已超时，在TIMER_FIRED事件之后，会触发用户任务的超时事件，     当这个任务分配了一个定时器的时候。
 * PROCESS_COMPLETED
 * 流程已结束。在最后一个节点的ACTIVITY_COMPLETED事件之后触发。 当流程到达的状态，没有任何后续连线时， 流程就会结束。
 * MEMBERSHIP_CREATED
 * 用户被添加到一个组里。事件包含了用户和组的id。
 * MEMBERSHIP_DELETED
 * 用户被从一个组中删除。事件包含了用户和组的id。
 * MEMBERSHIPS_DELETED
 * 所有成员被从一个组中删除。在成员删除之前触发这个事件，所以他们都是可以访问的。   因为性能方面的考虑，不会为每个成员触发单独的MEMBERSHIP_DELETED事件。
 * @author: liubin
 * @date: 2019-03-29
 */
public class GlobalEventListener implements ActivitiEventListener {
    private Map<String, String> handlers = new HashMap<String, String>();

    private static final Logger logger = LoggerFactory.getLogger(ProcessCompleteListener.class);

    @Override
    public void onEvent(ActivitiEvent event) {
        String eventType = event.getType().name();
        //根据事件的类型ID,找到对应的事件处理器
        logger.info("eventType is ========>" + eventType);
        String eventHandlerBeanId = handlers.get(eventType);
        if (eventHandlerBeanId != null) {
            EventHandler handler = (EventHandler) SpringUtil.getBean(eventHandlerBeanId);
            handler.handle(event);
        } else {
            logger.warn(eventType + " handler is not fount.");
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    public Map<String, String> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<String, String> handlers) {
        this.handlers = handlers;
    }


}
