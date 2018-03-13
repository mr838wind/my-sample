package com.wdfall.slot.business.simulator.display;

import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.EmulatorSymbol;

import lombok.Data;
/**
 * 한번 spin 결과
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Data
public class Snapshot {

	//
	private List<EmulatorReel> playReels;
	
	
	@Override
	public String toString(){		
		if(playReels == null){
			return "No Spin";
		}
		
		StringBuilder sb = new StringBuilder();
		
		int maxCols= 0;
		for (EmulatorReel playReel : playReels) {
			maxCols = Math.max(maxCols, playReel.size());
		}
		
		for(int i=0; i< maxCols; i++){
			for (EmulatorReel playReel : playReels) {
				if(i < playReel.size()){
					EmulatorSymbol symbol = playReel.getSymbol(i);
					sb.append("[").append(symbol.getSymbolCode()).append("]");
				}else{
					sb.append("[ ]");
				}				
			}	
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
