package com.wdfall.slot.business.simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Deprecated
public class RandomShuffle<T> {
	
	private List<T> pool = new ArrayList<>(); 
	public RandomShuffle() {
		
	}
	
	public void addChance(T obj, int chance) {
		if(chance <= 0 ) return;
		
		for( int i=0; i< chance; i++ ){
			pool.add(obj);	
		}
	}
	
	public List<T> get(int amount){
		if( pool.isEmpty() ){
			throw new RuntimeException( "Pool is Empty." );
		}
		
		Collections.shuffle(pool);
		
		List<T> results = new ArrayList<>();
		for(int i=0; i<amount; i++) {
			results.add(pool.get(i));
		}
		
		return results;
	}
}
