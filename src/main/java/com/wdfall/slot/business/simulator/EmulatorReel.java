package com.wdfall.slot.business.simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * 릴
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class EmulatorReel {
	
	private List<EmulatorSymbol> symbols = new ArrayList<EmulatorSymbol>();
	
	public EmulatorReel(){}

	public EmulatorReel(EmulatorSymbol... symbols){
		for( int i=0; i< symbols.length; i++ ){			
			this.symbols.add(symbols[i] );
		}
	}
	
	public EmulatorSymbol  getSymbol( int idx ){
		return symbols.get(idx);
	}


	public void add(EmulatorSymbol emulatorSymbol) {
		symbols.add(emulatorSymbol);
	}
	
	
	public void shiftUp(EmulatorSymbol symbol) {
		symbols.remove(0);
		symbols.add(symbol);
	}
	
	public void shiftDown(EmulatorSymbol symbol) {
		int lastIndex = symbols.size() - 1;
		symbols.remove(lastIndex);
		symbols.add(0, symbol);
	}

	public int size() {
		return symbols.size();
	}
	
	@Override
	public String toString() {
		if(symbols == null) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(EmulatorSymbol symbol : symbols) {
			sb.append(symbol.getSymbolCode()).append(","); 
		}
		sb.append("]");
		return sb.toString();
	}
	
}
