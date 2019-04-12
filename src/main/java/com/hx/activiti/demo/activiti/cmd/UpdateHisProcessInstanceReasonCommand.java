package com.hx.activiti.demo.activiti.cmd;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;

/**
 * @description: 更新流程结束原因
 * @author: liubin
 * @date: 2019-04-08
 */
public class UpdateHisProcessInstanceReasonCommand implements Command<Void> {

    protected String procinstId;
    protected String deleteReason;

    public UpdateHisProcessInstanceReasonCommand(String procinstId, String deleteReason) {
        this.procinstId = procinstId;
        this.deleteReason = deleteReason;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        HistoricProcessInstanceEntity entity = commandContext
                .getDbSqlSession().selectById(HistoricProcessInstanceEntity.class, procinstId);
        if (entity != null) {
            entity.markEnded(deleteReason);
        }
        commandContext.getDbSqlSession().update(entity);
        return null;
    }
}
