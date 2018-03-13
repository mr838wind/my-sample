package com.wdfall.slot.business.simulator.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 릴 구성
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class EmulatorReelProperty {

	//<objectCode,count>
	private Map<String, Integer> countPerSymbolObjectCodes;
	
	public EmulatorReelProperty(){
		countPerSymbolObjectCodes = new HashMap<String, Integer>();
		
	}
	

	public int count() {
		int sum = 0;
		Iterator<Integer> values = countPerSymbolObjectCodes.values().iterator();
		while( values.hasNext() ){
			sum += values.next();
		}

		return sum;
	}

	public List<String> getSymbolObjectCodes() {
		return new ArrayList<String>( countPerSymbolObjectCodes.keySet() );
	}
	
	public int count(String key){
		if( countPerSymbolObjectCodes.containsKey(key) ){
			return countPerSymbolObjectCodes.get(key);			
		}else{
			return 0;
		}		
		
	}
	
	public void addSymbolAmounts(KeyValue<String,Integer>... symbolCounts) {
		for (KeyValue<String,Integer> symbolCount : symbolCounts) {			
			this.addSymbolAmount(  symbolCount.key, symbolCount.value);	
		}
	}
	

	public EmulatorReelProperty addSymbolAmount(String key, int count) {
		
		if( countPerSymbolObjectCodes.containsKey(key) ){
			int existCount = countPerSymbolObjectCodes.get(key);
			countPerSymbolObjectCodes.put(key, existCount + count );
		}else{
			countPerSymbolObjectCodes.put(key, count );
		}		
		return this;
	}
	
}
