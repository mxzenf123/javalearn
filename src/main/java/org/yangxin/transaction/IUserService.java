package org.yangxin.transaction;

import java.sql.Connection;

public interface IUserService {

    String getUser(Integer id) throws Exception;
    String getUser(Integer id, Connection connection) throws Exception;

    void addUser(Integer id, String name) throws Exception;
    void addUser(Integer id, String name, Connection connection) throws Exception;
}
