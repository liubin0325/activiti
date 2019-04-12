package com.hx.activiti.demo.util;

import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;


public class CustomDbSqlSession extends DbSqlSession {
    public CustomDbSqlSession(DbSqlSessionFactory dbSqlSessionFactory) {
        super(dbSqlSessionFactory);
    }

    @Override
    protected void dbSchemaCreateEngine() {
        super.dbSchemaCreateEngine();
    }
    @Override
    public String getResourceForDbOperation(String directory, String operation,
                                            String component) {
        String databaseType = dbSqlSessionFactory.getDatabaseType();
        return "org/activiti/db/" + directory + "/activiti." + databaseType
                + "." + operation + "." + component + ".sql";
    }
}
