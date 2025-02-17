package com.mtc.online.buspass.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StudentConfig implements WebMvcConfigurer {
	 @Override
		public void addResourceHandlers(ResourceHandlerRegistry registry ) {
			Path studUploadDir =Paths.get("./stud-logo");
			String studUploadPath=studUploadDir.toFile().getAbsolutePath();
			registry.addResourceHandler("/stud-logo/**").addResourceLocations("file:/"+studUploadPath+"/");
		}
	
}
