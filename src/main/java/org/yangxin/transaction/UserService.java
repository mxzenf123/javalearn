package org.yangxin.transaction;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService implements IUserService {

    @Autowired
    private DruidDataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserService() {
    }


    @Override
    public String getUser(Integer id) throws Exception {
//        Connection connection = dataSource.getConnection();
//        String name = getUser(id, connection);
//
//        connection.close();
        String sql = "select name from users where id = " + id;
//        String name = jdbcTemplate.queryForObject(sql, String.class);
        List<String> arrayLists = jdbcTemplate.queryForList(sql, String.class);
        if (null != arrayLists && arrayLists.size() > 0) {
            return arrayLists.get(0);
        }

        return null;
    }

    @Override
    public String getUser(Integer id, Connection connection) throws Exception {

        connection.setAutoCommit(false);
        Statement s = connection.createStatement();
        String sql = "select * from users where id = " + id;
        ResultSet resultSet = s.executeQuery(sql);
        while (resultSet.next()) {
            return resultSet.getString("name");
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(Integer id, String name) throws Exception {
//        Connection connection = dataSource.getConnection();
//        addUser(id, name, connection);


//        connection.commit();
//        connection.close();
        String sql = "INSERT INTO USERS(ID, NAME) VALUES (" + id + ", '" + name + "');";

        jdbcTemplate.update(sql);
//        if (1==1) {
//            throw new RuntimeException("回滚");
//        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(Integer id, String name, Connection connection) throws Exception {

        addUser(id, name);
        if (1==1) {
            throw new RuntimeException("回滚");
        }
//        connection.setAutoCommit(false);
//        Statement s = connection.createStatement();
//
//        String sql = "INSERT INTO USERS(ID, NAME) VALUES (" + id + ", '" + name + "');";
//        s.execute(sql);
    }

    @PreDestroy
    public void destory() {

        dataSource.close();
    }
}
