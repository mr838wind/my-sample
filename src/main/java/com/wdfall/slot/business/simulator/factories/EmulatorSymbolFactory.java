package com.wdfall.slot.business.simulator.factories;

import java.util.HashMap;
import java.util.Map;

import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.property.SymbolDefine;

import lombok.extern.slf4j.Slf4j;
/**
 * EmulatorSymbolFactory
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class EmulatorSymbolFactory {

	// === obj
	
	private GameProperty property;
	
	public void setProperty(GameProperty property) {
		this.property = property;
	}
	
	public GameProperty getProperty() {
		return property;
	}
	
	private Map<String, EmulatorSymbol> symbolMap = new HashMap<>();
	
	public EmulatorSymbol get(String symbolId) {
		EmulatorSymbol result = null;
		if(symbolMap.containsKey(symbolId)) {
			result = symbolMap.get(symbolId);
		} else {
			EmulatorSymbol symbol = createSymbol(symbolId);
			symbolMap.put(symbolId, symbol);
			result = symbol;
		}
		return result;
	}

	
	private EmulatorSymbol createSymbol(String symbolCode) {
		EmulatorSymbol symbol = null;
		SymbolDefine sd = null;
		if(property != null) {
			sd = property.getSymbolDefineMapWrapper().getSymbolDefineBySymbolCode(symbolCode);
		}
		
		if(sd != null) {
				symbol = createSymbolFromSymbolDefine(sd);
		} else {
			symbol = createSymbolExternal(symbolCode);
		}
		return symbol;
	}

	private EmulatorSymbol createSymbolFromSymbolDefine(SymbolDefine sd) {
		EmulatorSymbol symbol = new EmulatorSymbol(sd.getSymbolCode());
		symbol.setGroupSize(sd.getGroupSize()); // should not use in EmulatorSymbol
		symbol.setObjectCode(sd.getObjectCode());
		symbol.setSymbolType(sd.getType());
		symbol.setNeedForWin(sd.getNeedForWin());
		return symbol;
	}

	// 일반 symbol 만들어준다. left,right box에 쓰는것
	private EmulatorSymbol createSymbolExternal(String symbolId) {
		EmulatorSymbol symbol = new EmulatorSymbol(symbolId);
		return symbol;
	}
	
}
