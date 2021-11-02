package com.douzone.mysite.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.douzone.mysite.security.AuthInterceptor;
import com.douzone.mysite.security.LoginInterceptor;
import com.douzone.mysite.security.LogoutInterceptor;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.util.AuthUserArgumentResolver;
import com.douzone.mysite.util.SiteInfoLoadInterceptor;
import com.douzone.mysite.util.SiteInfoUpdateInterceptor;

@Configuration
@PropertySource("classpath:com/douzone/mysite/config/WebConfig.properties")
public class WebConfig implements WebMvcConfigurer {
	private UserService userService;
	@Autowired
	private Environment environment;

	@Autowired
	public WebConfig(UserService userService) {
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

	@Bean
	public HandlerInterceptor siteInfoLoadInterceptor() {
		return new SiteInfoLoadInterceptor();
	}

	@Bean
	public HandlerInterceptor siteInfoUpdateInterceptor() {
		return new SiteInfoUpdateInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(siteInfoLoadInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(siteInfoUpdateInterceptor()).addPathPatterns("/admin/main/update");

		registry.addInterceptor(loginInterceptor()).addPathPatterns("/user/login");
		registry.addInterceptor(new LogoutInterceptor()).addPathPatterns("/user/logout");
		registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/login",
				"/user/logout", "/assets/**");
	}

/*
	 * 
	 
	// Message Converter
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
		messageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "html", Charset.forName("UTF-8"))));
		return messageConverter;
	}
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().indentOutput(true)
				.dateFormat(new SimpleDateFormat("yyyy-mm-dd")) //
				.build();

		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
		messageConverter
				.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
		return messageConverter;
	}
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}
*/
	// ResourceHandler
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
		registry.addResourceHandler(environment.getProperty("fileupload.resourceMapping"))
				.addResourceLocations(environment.getProperty("fileupload.fileUploadLocation"));
	}

}
