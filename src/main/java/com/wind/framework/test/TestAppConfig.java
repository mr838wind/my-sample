package com.wind.framework.test;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;

import com.wind.framework.AppConfig.ConfigTestBean;
import com.wind.framework.SpringBootstrap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestAppConfig {

	public static void main(String[] args) {
		
		// profile
		//System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "default");
		
		ApplicationContext context = SpringBootstrap.getInstance().getContext();
		
		String profile = ((ConfigurableEnvironment)context.getEnvironment()).getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
		log.info("profile={}",profile); 
		
		context.getBean(ConfigTestBean.class).sayhello("wind"); 
		SpringBootstrap.getInstance().close();
	}
}
