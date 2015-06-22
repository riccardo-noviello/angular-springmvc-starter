package com.redmonkeysoftware.myapp.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
*
* @author novier
*/
@Configuration
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = "com.redmonkeysoftware.myapp.config")
@PropertySource("classpath:com/redmonkeysoftware/myapp/config/defaults.properties")
public class AppConfiguration {

    @Autowired
    private Environment env;
    
    @Bean()
    @Qualifier("dataSource")
    public DataSource getDataSource() throws PropertyVetoException {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        config.addDataSourceProperty("user", env.getProperty("db.user"));
        config.addDataSourceProperty("password", env.getProperty("db.password"));
        config.addDataSourceProperty("databaseName", env.getProperty("db.name"));
        config.addDataSourceProperty("serverName", env.getProperty("db.host"));
        config.addDataSourceProperty("portNumber", env.getProperty("db.port"));
        return new HikariDataSource(config);
    }

     
    @Bean
    public DataSourceTransactionManager getTransactionManager() throws PropertyVetoException {
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(getDataSource());
        return dstm;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new Resource[1];
        resources[0] = new ClassPathResource("/com/redmonkeysoftware/myapp/config/defaults.properties");
        pspc.setLocations(resources);
        pspc.setIgnoreUnresolvablePlaceholders(true);
        return pspc;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        jms.setHost(env.getProperty("smtp.host"));
        jms.setPort(Integer.parseInt(env.getProperty("smtp.port")));
        jms.setProtocol(env.getProperty("smtp.protocol"));
        jms.setUsername(env.getProperty("smtp.user"));
        jms.setPassword(env.getProperty("smtp.password"));
        return jms;
    }
    
}
