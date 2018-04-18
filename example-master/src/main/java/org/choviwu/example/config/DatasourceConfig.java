package org.choviwu.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by ChoviWu on 2018/04/12
 * Description:DataSource事务支持
 */
@Configuration
@EnableAutoConfiguration
public class DatasourceConfig {

    private final DataSource dataSource;


    @Autowired
    DatasourceConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

}
