package com.douzone.mysite;

import java.util.stream.Stream;
import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
public class MySiteApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(MySiteApplication.class, args);
		Stream.of(run.getEnvironment().getActiveProfiles()).forEach(System.out::println);
		HikariDataSource bean = (HikariDataSource)run.getBean(DataSource.class);
		System.out.println(bean);
		System.out.println(bean.getMinimumIdle());
		System.out.println(bean.getMaximumPoolSize());
	}
}
