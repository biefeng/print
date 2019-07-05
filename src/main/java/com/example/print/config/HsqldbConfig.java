package com.example.print.config;
/*
 *@Author BieFeNg
 *@Date 2019/7/5 11:38
 *@DESC
 */

import org.hsqldb.Database;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class HsqldbConfig implements InitializingBean {

    @Autowired
    private DataSource dataSource;

    @Override
    public void afterPropertiesSet() throws Exception {
/*        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        //create table
        statement.execute(" CREATE TABLE jy_print_template ( id  INTEGER GENERATED BY DEFAULT AS  PRIMARY KEY, template_name VARCHAR(512),prop varchar(10240) ) ");*/

    }
}
