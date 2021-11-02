package com.douzone.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:com/douzone/mysite/config/web/fileupload.properties")
public class FileUploadConfig extends WebMvcConfigurerAdapter{
	@Autowired
	private Environment environment;
	
	// MultiPart Resolver
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(environment.getProperty("fileupload.maxUploadSize", Integer.class));
		multipartResolver.setMaxInMemorySize(environment.getProperty("fileupload.maxInMemorySize", Integer.class));
		multipartResolver.setDefaultEncoding(environment.getProperty("fileupload.defaultEncoding"));
		
		return multipartResolver;
	}
	
	// ResourceHandler
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler(environment.getProperty("fileupload.resourceMapping"))
			.addResourceLocations(environment.getProperty("fileupload.fileUploadLocation"));
	}


}
