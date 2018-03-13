package com.wdfall.slot.business.simulator.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * SymbolDefineMapWrapper
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class SymbolDefineMapWrapper {

	// 심블 정의 및 payout: <objectCode,SymbolDefine>
	private Map<String, SymbolDefine> symbolDefineObjectCodeMap = new HashMap<>();
	
	public void addSymbolDefine(SymbolDefine symbolDefine) {
		if( symbolDefineObjectCodeMap.containsKey(symbolDefine.getObjectCode()) ) {
			throw new InvalidSimulatorProperty("Duplicated symbol");
		}
		symbolDefineObjectCodeMap.put(symbolDefine.getObjectCode(), symbolDefine);
	}
	
	public SymbolDefine getSymbolDefineByObjectCode(String objectCode) {
		return symbolDefineObjectCodeMap.get(objectCode);
	}
	
	public boolean containsObjectCode(String objectCode) {
		return symbolDefineObjectCodeMap.containsKey(objectCode);
	}
	
	public SymbolDefine getSymbolDefineBySymbolCode(String symbolCode) {
		return this.getSymbolDefineSymbolCodeMap().get(symbolCode);
	}
	
	public List<String> getScatterSymbolCodes() {
		List<String> result = new ArrayList<>();
		for(Map.Entry<String, SymbolDefine> item : symbolDefineObjectCodeMap.entrySet()) {
			SymbolDefine sd = item.getValue();
			if(sd.getType() == SymbolDefine.TYPE_SCATTER_BONUS 
					|| sd.getType() == SymbolDefine.TYPE_SCATTER_FREESPIN) {
				result.add(sd.getSymbolCode());
			}
		}
		return result;
	}
	
	public String getWildSymbolCode() {
		String result = "";
		for(Map.Entry<String, SymbolDefine> entry : symbolDefineObjectCodeMap.entrySet()) {
			SymbolDefine symbolDF = entry.getValue();
			if(symbolDF.getType() == SymbolDefine.TYPE_WILD) {
				result = symbolDF.getSymbolCode();
				break;
			}
		}
		return result;
	}
	
	/**
	 * better use addSymbolDefine to add setting
	 * @param payoutTable
	 */
	@Deprecated
	public void setPayoutTable(SimulatorPayoutPropertyTable payoutTable) {
		symbolDefineObjectCodeMap.clear();
		for(Map.Entry<String, SymbolDefine> entry :  payoutTable.iterator()) {
			SymbolDefine sd = entry.getValue();
			symbolDefineObjectCodeMap.put(sd.getObjectCode(), sd);
		}
	}
	
	
	public SimulatorPayoutPropertyTable getPayoutTable() {
		SimulatorPayoutPropertyTable payoutTable = new SimulatorPayoutPropertyTable ();
		
		Map<String,SymbolDefine> symbolDefineSymbolCodeMap = getSymbolDefineSymbolCodeMap();
		for(Map.Entry<String,SymbolDefine> entry: symbolDefineSymbolCodeMap.entrySet()) {
			payoutTable.put(entry.getValue());
		}
		return payoutTable;	
	}

	/**
	 * 중복되는 symbolCode는 하나만 넣는다. 
	 * (symbolDefineObjectCodeMap > symbolDefineSymbolCodeMap)
	 * @return
	 */
	private Map<String, SymbolDefine> getSymbolDefineSymbolCodeMap() {
		Map<String, SymbolDefine> symbolDefineSymbolCodeMap = new HashMap<>();
		for(Map.Entry<String, SymbolDefine> entry : symbolDefineObjectCodeMap.entrySet()) {
			SymbolDefine sd = entry.getValue();
			
			SymbolDefine sdInMap = symbolDefineSymbolCodeMap.get(sd.getSymbolCode());
			if(sdInMap == null) {
				symbolDefineSymbolCodeMap.put(sd.getSymbolCode(), sd);
			} else {
				if(!isPayoutEquals(sdInMap.getPayouts(), sd.getPayouts())) {
					throw new InvalidSimulatorProperty("payout not same. symbol = " + sd.getObjectCode());
				}
			}
		}
		return symbolDefineSymbolCodeMap;
	}

	private boolean isPayoutEquals(int[] payouts, int[] payouts2) {
		return Objects.deepEquals(payouts, payouts2);
	}
	
		
}
