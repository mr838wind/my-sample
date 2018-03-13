package com.wdfall.slot.business.simulator.pattern;

import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.EmulatorSymbol;

import lombok.Data;
/**
 * win pattern
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public interface IFEmulatorWinningPattern{

	public List<EmulatorWinningPatternResult> match( List<EmulatorReel> reels);
	
	public PatternType getPatternType();
	
	/**
	 * pattern 구분
	 * @author chhan
	 *
	 */
	public static enum PatternType {
		LINE, SCATTER
	}

	@Data
	public static class EmulatorWinningPatternResult {
		private String hittedSymbolId;
		private List<EmulatorSymbol> hittedSymbols;
		private PatternType pattern;
		
		public String getHittedSymbolId() {
			return hittedSymbolId;
		}
	
		public int getHittedCount() {
			return hittedSymbols.size();
		}
	
		public List<EmulatorSymbol> getHittedSymbols() {
			return hittedSymbols;
		}
	
	}
}
