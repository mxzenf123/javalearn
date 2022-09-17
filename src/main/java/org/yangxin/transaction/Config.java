package org.yangxin.transaction;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author 杨大哥
 * @date 2022/9/17 9:47
 * Configuration和Configurable属性配置等效对于EnableTransactionManagement来说
 * ，只要配置类有Import注解就会被ConfigurationClassPostProcessor解析，网上文章很多说法有误
 */
//@Configuration
@Configurable
@EnableTransactionManagement
public class Config {
    @Bean
    public IUserService userService() throws Exception {
        return new UserService();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DruidDataSource dataSource() {
        try {
            DruidDataSource dataSource = Demo1.getDs();
            dataSource.setDefaultAutoCommit(false);
            Demo1.preparedDataBase(dataSource);

            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Bean
    @Autowired
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
