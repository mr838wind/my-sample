package com.wind.framework.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wind.framework.SpringBootstrap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestSpring {
	
	@Autowired
	private MySingle testSingle;
	
	public void sayHello(String msg) {
		testSingle.call();
		log.info(" sayHello: {}", msg);
	}
	
	public static void main(String[] args) {
		SpringBootstrap.getInstance().getContext().getBean(TestSpring.class).sayHello("Hello World !!!");
		SpringBootstrap.getInstance().close();
	}
	
}


interface MySingle {
	void call();
}

@Slf4j
@Component
class MySingleA implements MySingle {
	@Override
	public void call() {
		log.info("MySingleA");
	}
}

