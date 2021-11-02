package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
@Configuration
@PropertySource("classpath:com/douzone/mysite/config/app/jdbc.properties") // 리소스를 읽어 프로퍼티들을 Environments Bean에 등록 
public class DBConfig {
	@Value("${jdbc.driverClassName}")
	String driverClassName;
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		System.out.println(driverClassName);
		System.out.println("===========");
		System.out.println(env.getProperty("jdbc.driverClassName"));
		
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		basicDataSource.setUrl(env.getProperty("jdbc.url"));
		basicDataSource.setUsername(env.getProperty("jdbc.username"));
		basicDataSource.setPassword(env.getProperty("jdbc.password"));
		basicDataSource.setInitialSize(env.getProperty("jdbc.initialSize",Integer.class));
		basicDataSource.setMaxActive(env.getProperty("jdbc.maxActive",Integer.class));
		return basicDataSource;
	}
}
