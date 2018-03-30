package com.wind.framework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import lombok.extern.slf4j.Slf4j;
/**
 * main으로 spring 실행 util
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class SpringBootstrap {
	
	public static SpringBootstrap instance;
	private final String CONFIG_PATH = "classpath*:/spring/*-context.xml";
	private ApplicationContext context;
	
	public static SpringBootstrap getInstance() {
		if(instance == null) {
			instance = new SpringBootstrap();
		}
		return instance;
	}
	
	private SpringBootstrap() {
		context = new ClassPathXmlApplicationContext(CONFIG_PATH);
	}
	
	public ApplicationContext getContext() {
		return context;
	}
	
	public void close(){
		try {
			finalize();
		} catch (Throwable e) {
			log.error("", e); 
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		((AbstractApplicationContext) context).close();
		super.finalize();
	}
	
}
