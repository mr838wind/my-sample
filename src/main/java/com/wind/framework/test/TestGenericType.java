package com.wind.framework.test;

import org.springframework.core.GenericTypeResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestGenericType {

	private static class A<T,R> {
		public Class<T> genericTypeT;
		public Class<R> genericTypeR;
		
		@SuppressWarnings("unchecked")
		public A() {
			// can only use when subclass
			this.genericTypeT = (Class<T>) GenericTypeResolver.resolveTypeArguments(getClass(), A.class)[0];
			this.genericTypeR = (Class<R>) GenericTypeResolver.resolveTypeArguments(getClass(), A.class)[1];
		}
		
	}
	private static class B extends A<String,Integer> {
		
	}
	
	public static void main(String[] args) {
		B b = new B();
		log.debug("name = {}",b.genericTypeT.getName());
		log.debug("name = {}",b.genericTypeR.getName());
		
		// null exception
		//A<String,Integer> a = new A<>();
		//log.debug("name = {}",a);
		
	}
	
}
