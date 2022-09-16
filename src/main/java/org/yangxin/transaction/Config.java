package org.yangxin.transaction;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
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
