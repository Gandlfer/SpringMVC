package com.example.personal_project_1_darrylmingsenlee.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JDBCConfig {
    @Value(value = "${spring.datasource.url}")
    private String db_url;
    @Value(value = "${spring.datasource.username}")
    private String db_username;
    @Value(value = "${spring.datasource.password}")
    private String db_password;
    @Value(value = "${spring.datasource.driver-class-name}")
    private String db_driver;

    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource(db_url,db_username,db_password);
        ds.setDriverClassName(db_driver);
        return ds;
    }
}
