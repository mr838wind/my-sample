/*
 * @(#)RandomPool.java
 * Copyright (c) Windfall Inc., All rights reserved.
 * 2014. 3. 21. - First implementation
 * contact : devhcchoi@gmail.com
 */
package com.wdfall.slot.business.simulator.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <pre>
 *
 * </pre>
 * @author Hyun Chul Choi
 * @email devhcchoi@gmail.com
 * @version 1.0 Since 2014. 3. 21.
 */
@Deprecated
public class RandomPool <T> {
	
	RandomPool<T>  poolActor;
	
	private RandomPool (){}	
	
	public RandomPool ( boolean enableDuplicate ){
		if(enableDuplicate){
			poolActor = new EnableDuplicatePool<T>();
		}else{
			poolActor= new DisableDuplicatePool<T>();
		}
	}
	
	public void addChance(T obj, int chance) {
		poolActor.addChance(obj, chance);
	}
	public T get(){
		return poolActor.get();
	}
	
	static class EnableDuplicatePool<T> extends RandomPool<T> {
		private int totalChance = 0;				
		private List<Integer> chanceRange = new ArrayList<Integer>();
		private List<T> pool = new ArrayList<T>(); 

		@Override
		public void addChance(T obj, int chance) {
			if(chance <= 0 ) return;
			
			pool.add(obj);
			chanceRange.add( totalChance + chance );
			
			totalChance += chance;
		}
		
		@Override
		public T get(){
			int targetIndex = getMin();
			if( targetIndex < 0 ) return null; 
			return pool.get(targetIndex);
		}

		private  int getMin(  ){
			if( totalChance <= 0 ) return -1;
			
			Random rand = new Random();								
			int targetPoint = rand.nextInt(totalChance)  ; // 0 ~ totalChance-1 ?��?��?�� �?.				
			
			for (int i = 0; i < chanceRange.size() ; i++) {
				int max = chanceRange.get(i);
				if( max >= targetPoint ){
					return i;
				}
			}
			return -1;
		}
	}
	


	static class DisableDuplicatePool<T> extends RandomPool<T> {
		private List<T> pool = new ArrayList<T>(); 

		@Override
		public void addChance(T obj, int chance) {
			if(chance <= 0 ) return;
			
			for( int i=0; i< chance; i++ ){
				pool.add(obj);	
			}
		}
		
		@Override
		public T get(){
			if( pool.isEmpty() ){
				throw new RuntimeException( "Pool is Empty." );
			}
			
			Random random = new Random();			
			int targetIndex = random.nextInt(pool.size());			
			return pool.remove(targetIndex);
		}

	}
}
