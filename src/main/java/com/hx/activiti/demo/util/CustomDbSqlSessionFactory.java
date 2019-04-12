package com.hx.activiti.demo.util;

import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.Session;

public class CustomDbSqlSessionFactory extends DbSqlSessionFactory {
    @Override
    public Class<?> getSessionType() {
        return CustomDbSqlSessionFactory.class;
    }
    @Override
    public Session openSession() {
        return new CustomDbSqlSession(this);
    }
}
