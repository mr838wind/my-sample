package com.wind.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Application Config file 
 * 
 * {xml(주) + java방식}
 * 
 * {java(주) + xml}도 가능함. 
 * 
 * @author chhan 2018. 3. 30.
 *
 */
@Configuration
@Slf4j
public class AppConfig {

	public static class ConfigTestBean {
		public String sayhello(String input) {
			String msg = "hello " +input+ " !!!";
			log.info(msg); 
			return msg;
		}
	}
	
	@Bean
	public ConfigTestBean mytest() {
		return new ConfigTestBean(); 
	}
	
}
