package com.douzone.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.douzone.config.app.DBConfig;
import com.douzone.config.app.MyBatisConfig;

@Configuration
@EnableAspectJAutoProxy
@Import({DBConfig.class, MyBatisConfig.class})
@ComponentScan(basePackages = {"com.douzone.mysite.service","com.douzone.mysite.repository", "com.douzone.mysite.advice","com.douzone.util"}, 
			excludeFilters = {@Filter(type = FilterType.ANNOTATION, classes = {ControllerAdvice.class})})
public class AppConfig {

}
