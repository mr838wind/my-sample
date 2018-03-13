package com.wdfall.slot.business.simulator.property;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * payout table rule
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class SimulatorPayoutPropertyTable {
	
	// <symbolCode, SymbolDefine> 
	Map<String, SymbolDefine> properties;
	
	public SimulatorPayoutPropertyTable(){
		properties = new HashMap<String, SymbolDefine>();
	} 
	
	public SymbolDefine put( SymbolDefine property ){
		return properties.put(property.getSymbolCode(), property);
	}
	public SymbolDefine get( String id){
		return properties.get(id);
	}

	public Object getSymbolCount() {
		return properties.size();
	}
	
	public Set<Map.Entry<String,SymbolDefine>> iterator() {
		return properties.entrySet();
	}
	
}
