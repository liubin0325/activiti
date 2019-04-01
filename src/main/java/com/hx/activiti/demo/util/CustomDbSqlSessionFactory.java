package com.hx.activiti.demo.util;

import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.interceptor.Session;

public class CustomDbSqlSessionFactory extends DbSqlSessionFactory {
    public Class<?> getSessionType() {
        return CustomDbSqlSessionFactory.class;
    }

    public Session openSession() {
        return new CustomDbSqlSession(this);
    }
}
