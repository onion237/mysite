package com.douzone.mysite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.douzone.config.web.FileUploadConfig;
import com.douzone.config.web.MessageConfig;
import com.douzone.config.web.MvcConfig;
import com.douzone.config.web.SecurityConfig;
import com.douzone.util.SiteInfoLoadInterceptor;
import com.douzone.util.SiteInfoUpdateInterceptor;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "com.douzone.mysite.controller", "com.douzone.mysite.advice.controller" })
@Import({ MvcConfig.class, MessageConfig.class,  FileUploadConfig.class, SecurityConfig.class })
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public HandlerInterceptor siteInfoLoadInterceptor() {
		SiteInfoLoadInterceptor siteInfoLoadInterceptor = new SiteInfoLoadInterceptor();
		return siteInfoLoadInterceptor;
	}
	
	@Bean
	public HandlerInterceptor siteInfoUpdateInterceptor() {
		return new SiteInfoUpdateInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(siteInfoLoadInterceptor())
			.addPathPatterns("/**");
		registry
			.addInterceptor(siteInfoUpdateInterceptor())
			.addPathPatterns("/admin/main/update");
	}
	
}
