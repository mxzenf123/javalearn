package org.yangxin.transaction;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Demo1 {

    public static DruidDataSource getDs() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
//        Thread.currentThread().getContextClassLoader().getResourceAsStream("hsqldb.properties");
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hsqldb.properties"));

        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setDriverClassName(properties.getProperty("driver"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
//        dataSource.setDefaultAutoCommit(false);
        dataSource.init();

        return dataSource;
    }

    public static void preparedDataBase(DataSource dataSource) throws Exception {
        Connection connection = dataSource.getConnection();
        String sql = "CREATE TABLE USERS(ID INTEGER, NAME VARCHAR);";
        Statement s = connection.createStatement();
        s.execute(sql);

        sql = "INSERT INTO USERS(ID, NAME) VALUES (1, '杨大哥');";
        s.execute(sql);
        connection.commit();
        connection.close();
    }

    @Test
    public void test_datasource() throws Exception {
//        System.out.println(getDs());
        DruidDataSource dataSource = getDs();
        Assert.notNull(dataSource, "DataSource不能为空");
        Assert.notNull(dataSource.getConnection(), "获取连接不能为空");

        dataSource.close();
    }

    @Test
    public void test_transaction() throws Exception {
        DruidDataSource dataSource = getDs();
        preparedDataBase(dataSource);
        Connection connection = dataSource.getConnection();
        Statement s = connection.createStatement();
        String sql = "select * from users where id = 1";
        ResultSet resultSet = s.executeQuery(sql);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));

        }
    }

    @Test
    public void test_ddl() throws Exception {
        DruidDataSource dataSource = getDs();
        Connection connection = dataSource.getConnection();
        String sql = "CREATE TABLE USERS(ID INTEGER, NAME VARCHAR);";
        Statement s = connection.createStatement();
        s.execute(sql);

        sql = "INSERT INTO USERS(ID, NAME) VALUES (1, '杨大哥');";
        s.execute(sql);

        sql = "select * from users where id = 1";
        ResultSet resultSet = s.executeQuery(sql);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));

        }

        connection.commit();
        connection.close();
        dataSource.close();
    }

    @Test
    public void test_spring_annotation_transactional() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        System.out.println(AnnotationConfigApplicationContext.class.getName());
        IUserService userService = context.getBean(IUserService.class);
        DataSource dataSource = context.getBean(DataSource.class);
//        Connection conn1 = dataSource.getConnection();
//        Connection conn2 = dataSource.getConnection();
//        System.out.println(userService.getUser(1));

        try {
            userService.addUser(2, "杨二哥");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("查询结果："+userService.getUser(2));
//        System.out.println(userService.getUser(2));

        context.close();

    }
}



