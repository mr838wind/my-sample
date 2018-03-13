package com.wdfall.slot.business.simulator.pattern;

import java.util.ArrayList;
import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.EmulatorSymbol;

import lombok.extern.slf4j.Slf4j;
/**
 * scatter pattern
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class WinScatterPattern implements IFEmulatorWinningPattern{
	String scatterSymbolId;
	public WinScatterPattern(String symbolId){
		scatterSymbolId = symbolId;
	}
	
	public List<EmulatorWinningPatternResult> match(List<EmulatorReel> reels) {
		List<EmulatorWinningPatternResult> resultList = new ArrayList<>();
		int count = 0;
		EmulatorWinningPatternResult result = null;
		List<EmulatorSymbol> matchedSymbols = new ArrayList<EmulatorSymbol>();
		
		EmulatorSymbol entrySymbol = null;
		
		for( int i=0; i< reels.size() ; i++ ){
			EmulatorReel reel = reels.get(i);
			for( int j=0; j<  reel.size() ; j++ ){
				EmulatorSymbol symbol = reels.get(i).getSymbol(j);
				if( symbol.getSymbolCode().equals(scatterSymbolId) ){
					entrySymbol = symbol;
					matchedSymbols.add(symbol);
					count++;
				}
			}				
		}

		
		boolean isWin = (entrySymbol != null) && (count >= entrySymbol.getNeedForWin() );
		if( isWin ){
			result = new EmulatorWinningPatternResult();
			result.setHittedSymbols(matchedSymbols);
			result.setHittedSymbolId(scatterSymbolId);
			result.setPattern(this.getPatternType());
			
			resultList.add(result);
		}
		
		
		
		return resultList ;
	}
	
	@Override
	public PatternType getPatternType() {
		return PatternType.SCATTER;
	}
	
	

}
