package com.douzone.config.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.douzone.mysite.security.AuthInterceptor;
import com.douzone.mysite.security.LoginInterceptor;
import com.douzone.mysite.security.LogoutInterceptor;
import com.douzone.mysite.service.UserService;
import com.douzone.util.AuthUserArgumentResolver;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter{
	private UserService userService;

	@Autowired
	public SecurityConfig(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new AuthUserArgumentResolver());
	}

	@Bean
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor(userService);
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(loginInterceptor())
			.addPathPatterns("/user/login");
		registry
			.addInterceptor(new LogoutInterceptor())
			.addPathPatterns("/user/logout");
		registry
			.addInterceptor(new AuthInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns(
					"/user/login", 
					"/user/logout", 
					"/assets/**");
		
	
		
	}

	
}
