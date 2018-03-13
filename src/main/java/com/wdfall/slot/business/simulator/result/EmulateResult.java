package com.wdfall.slot.business.simulator.result;

import java.util.ArrayList;
import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.finder.WinningFinder.WinningResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.PatternType;

import lombok.extern.slf4j.Slf4j;

/**
 * 한번 게임 result
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class EmulateResult  {

	// pattern
	private List<IFEmulatorWinningPattern> winningPatterns;
	private List<IFEmulatorWinningPattern> winningLines;
	private List<IFEmulatorWinningPattern> winningScatters;
	// 결과
	private List<WinningResult> winningResults;
	private List<WinningResult> winningLineResults;
	private List<WinningResult> winningScatterResults;
	// payout
	private long payout = 0;
	private long linePayout = 0;
	private long scatterPayout = 0;
	//scatter detail
	private long scatterBonusPayout = 0;
	private long scatterFreespinPayout = 0;
	
	
	public void setWinningPatterns(List<IFEmulatorWinningPattern> winningPatterns) {
		this.winningPatterns = winningPatterns;
		this.winningLines = new ArrayList<>();
		this.winningScatters = new ArrayList<>();
		for(IFEmulatorWinningPattern pattern : winningPatterns) {
			if(PatternType.LINE.equals(pattern.getPatternType())) {
				this.winningLines.add(pattern);
			} else {
				this.winningScatters.add(pattern);
			}
		}
	}
	
	public List<IFEmulatorWinningPattern> getWinningPatterns(){
		return this.winningPatterns;
	} 
	
	public List<IFEmulatorWinningPattern> getWinningLines(){
		return this.winningLines ;
	}
	
	public List<IFEmulatorWinningPattern> getWinningScatters(){
		return this.winningScatters ;
	} 
	
	public void setWinningResults(List<WinningResult> results) {
		payout = 0;
		linePayout = 0;
		scatterPayout = 0;
		scatterBonusPayout = 0;
		scatterFreespinPayout = 0;
		winningResults = results;
		this.winningLineResults = new ArrayList<>();
		this.winningScatterResults = new ArrayList<>();
		
		for( WinningResult win : winningResults){
			payout += win.getPayout();
			//log.debug( "win.getPayout(): {}", win.getPayout() );
			
			if(PatternType.LINE.equals(win.getPattern())) {
				linePayout += win.getPayout();
				this.winningLineResults.add(win);
			} else {
				scatterPayout += win.getPayout();
				this.winningScatterResults.add(win);
				
				// detail
				EmulatorSymbol winSymbol = win.getWinSymbol();
				if(winSymbol.isScatterBonus()) {
					scatterBonusPayout += win.getPayout();
				} else if(winSymbol.isScatterFreespin()) {
					scatterFreespinPayout += win.getPayout();
				}
				
			}
		}
	}

	
	public List<WinningResult> getWinningResults(){
		return winningResults;
	}
	
	public long getWinningCount(){
		return winningResults==null ? 0 : winningResults.size();
	}
	
	public long getWinningLineCount(){
		return winningLineResults==null ? 0 : winningLineResults.size();
	}
	
	public long getWinningScatterCount(){
		return winningScatterResults==null ? 0 : winningScatterResults.size();
	}
	
	public long getPayout(){
		return payout;
	}
	
	public long getLinePayout() {
		return linePayout;
	}
	
	public long getScatterPayout() {
		return scatterPayout;
	}

	public long getScatterBonusPayout() {
		return scatterBonusPayout;
	}

	public long getScatterFreespinPayout() {
		return scatterFreespinPayout;
	}
	
	
	
}
