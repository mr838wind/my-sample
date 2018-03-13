package com.wdfall.slot.business.simulator;

import com.wdfall.slot.business.simulator.property.SymbolDefine;

import lombok.Data;

/**
 * 심블
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Data
public class EmulatorSymbol {

	private String symbolCode;
	private int groupSize;
	private String objectCode;
	
	private int symbolType = -1;
	private int needForWin;
	
	
	public EmulatorSymbol(String symbolCode){
		this.symbolCode = symbolCode;
		//default value
		this.groupSize = 1;
		this.objectCode = symbolCode;
	}
	
	public boolean isWild() {
		checkSymbolTypeInitialize();
		return symbolType == SymbolDefine.TYPE_WILD;
	}

	private void checkSymbolTypeInitialize() {
		if(symbolType == -1) {
			throw new IllegalArgumentException("symbolType not initialized.");
		}
	}
	
	public boolean isScatter() {
		checkSymbolTypeInitialize();
		return symbolType == SymbolDefine.TYPE_SCATTER_BONUS
				||  symbolType == SymbolDefine.TYPE_SCATTER_FREESPIN;
	}
	
	public boolean isScatterFreespin() {
		checkSymbolTypeInitialize();
		return symbolType == SymbolDefine.TYPE_SCATTER_FREESPIN;
	}
	
	public boolean isScatterBonus() {
		checkSymbolTypeInitialize();
		return symbolType == SymbolDefine.TYPE_SCATTER_BONUS;
	}
	
	public boolean isNormal() {
		checkSymbolTypeInitialize();
		return symbolType == SymbolDefine.TYPE_NORMAL;
	}
	
	
	@Override 
	public String toString(){
		return symbolCode;
	}

	
	// auto generate using id
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbolCode == null) ? 0 : symbolCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmulatorSymbol other = (EmulatorSymbol) obj;
		if (symbolCode == null) {
			if (other.symbolCode != null)
				return false;
		} else if (!symbolCode.equals(other.symbolCode))
			return false;
		return true;
	}

}
