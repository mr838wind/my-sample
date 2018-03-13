package com.wdfall.slot.business.simulator.finder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.display.Snapshot;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.PatternType;
import com.wdfall.slot.business.simulator.property.GameProperty;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
/**
 * slot에서 win pattern 찾는 작용
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class WinningFinder implements IFWinningFinder {

	// input 
	protected GameProperty property;
	
	public WinningFinder() {
		
	}
	
	public void setProperty(GameProperty property) {
		this.property = property;
	}

	@Override
	public List<List<EmulatorWinningPatternResult>> findWin(Snapshot spinResult) {
		List<EmulatorReel> reels = spinResult.getPlayReels();
		List<List<EmulatorWinningPatternResult>> results = new ArrayList<>();
		
		for(IFEmulatorWinningPattern line : property.getWinningPatterns()){
			List<EmulatorWinningPatternResult> matchResultList = line.match(reels);
			if(!CollectionUtils.isEmpty(matchResultList)) { 
				results.add(matchResultList);
			}
		}

		return results ;
	}

	
	@Data
	public static class WinningResult{
		private String symbolId;
		private int count ;
		private int payout;
		private List<EmulatorSymbol> hittedSymbols = new ArrayList<EmulatorSymbol>();
		// 배수
		private double multiple = 1.0;
		
		//
		private PatternType pattern;
		
		public EmulatorSymbol getWinSymbol() {
			if(StringUtils.isEmpty(symbolId)) {
				return null;
			}
			EmulatorSymbol result = null;
			for(EmulatorSymbol symbol : hittedSymbols) {
				if(symbolId.equals(symbol.getSymbolCode())) {
					result = symbol;
					break;
				}
			}
			return result;
		}
		
	}
}
