package com.wdfall.slot.business.simulator.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.EmulatorSymbol;

import lombok.extern.slf4j.Slf4j;

/**
 * line pattern
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class WinLinePattern implements IFEmulatorWinningPattern {

	private int[] winningPattern;
	
	public WinLinePattern(int[] pattern){
		winningPattern = pattern;
	}
	
	
	public List<EmulatorWinningPatternResult> match(List<EmulatorReel> reels ) {
		//log.debug("---------------------------------");
		//log.debug(" >> new WinLinePattern start ");
		List<EmulatorWinningPatternResult> resultList = new ArrayList<>();
		
		/*
		 * find match :
		 * 	1. start with wild symbol
		 * 	2. start with normal symbol
		 */
		List<EmulatorSymbol> toCheckList = getToCheckList(reels);
		
		if(toCheckList.get(0).isScatter()) {
			//scatter로 시작하는것은 체크하지 않음.
			return resultList;
		}
		
		EmulatorWinningPatternResult resultWild = matchWildSymbol(toCheckList);
		addIfNotNull(resultList, resultWild);
		
		EmulatorWinningPatternResult resultNormal = matchNormalSymbol(toCheckList);
		addIfNotNull(resultList, resultNormal);
		
		
		//log.debug(" >> WinLinePattern end ");
		//log.debug("---------------------------------");
		return resultList ;
	}
	
	private EmulatorWinningPatternResult matchWildSymbol(List<EmulatorSymbol> toCheckList) {
		EmulatorWinningPatternResult result = null;
		List<EmulatorSymbol> matchedSymbols = new ArrayList<EmulatorSymbol>();
		
		EmulatorSymbol firstSymbol = toCheckList.get(0);
		if(!firstSymbol.isWild()) {
			return null;
		}
		matchedSymbols.add(firstSymbol);
		
		EmulatorSymbol entrySymbol = firstSymbol; //매칭 하는 심블
		
		//start from 1
		for(int i=1; i<toCheckList.size(); i++) {
			EmulatorSymbol symbol = toCheckList.get(i);
			if(symbol.isWild()) {
				matchedSymbols.add(symbol);
			} else {
				break;
			}
		}
		
		if( matchedSymbols.size() >= entrySymbol.getNeedForWin() ){
			result = new EmulatorWinningPatternResult();
			result.setHittedSymbols(matchedSymbols);
			result.setHittedSymbolId(firstSymbol.getSymbolCode());
			result.setPattern(this.getPatternType());
			
			//log.debug(" matchWildSymbol: ");
			//log.debug(" result.hittedSymbolId = {}", result.hittedSymbolId);
			//log.debug(" result.hittedSymbols = {}", result.hittedSymbols);
		}
		
		return result;
	}
	
	/*
	 * check type: WWWWK, KWKKK
	 */
	private EmulatorWinningPatternResult matchNormalSymbol(List<EmulatorSymbol> toCheckList) {
		EmulatorWinningPatternResult result = null;
		List<EmulatorSymbol> matchedSymbols = new ArrayList<EmulatorSymbol>();
		
		// debug: scatter 3v3 payout 차이나는 문제 해결. 
		// scatter을 빼줘야 함. match는 normal,wild만 체크 한다
		
		//start from 0
		EmulatorSymbol firstNormalSymbol = null;
		for(int i=0; i<toCheckList.size(); i++) {
			EmulatorSymbol symbol = toCheckList.get(i);
			if(firstNormalSymbol == null) {
				if(symbol.isNormal()) {
					firstNormalSymbol = symbol;
					matchedSymbols.add(symbol);
				} else if(symbol.isWild()) { 
					matchedSymbols.add(symbol);
				} else if(symbol.isScatter()) { 
					// scatter은 빼줘야 함.
					break;
				}
				
			} else {
				boolean isWildOrSameNormalSymbol = symbol.isWild() 
						|| (symbol.isNormal() &&  firstNormalSymbol.getSymbolCode().equals(symbol.getSymbolCode()));
				if(isWildOrSameNormalSymbol) {
					matchedSymbols.add(symbol);
				} else {
					break;
				}
			}
		}
		
		EmulatorSymbol entrySymbol = firstNormalSymbol; //매칭 하는 심블
		
		if( firstNormalSymbol != null && matchedSymbols.size() >= entrySymbol.getNeedForWin() ){
			result = new EmulatorWinningPatternResult();
			result.setHittedSymbols(matchedSymbols);
			result.setHittedSymbolId(firstNormalSymbol.getSymbolCode());
			result.setPattern(this.getPatternType());
			
			//log.debug(" matchNormalSymbol: ");
			//log.debug(" result.hittedSymbolId = {}", result.hittedSymbolId);
			//log.debug(" result.hittedSymbols = {}", result.hittedSymbols);
		}
		
		return result;
	}


	private List<EmulatorSymbol> getToCheckList(List<EmulatorReel> reels) {
		List<EmulatorSymbol> toCheckList = new ArrayList<>();
		for( int i=0; i< reels.size() ; i++ ){
			int symbolIndex = winningPattern[i];
			EmulatorSymbol symbol = reels.get(i).getSymbol(symbolIndex);
			toCheckList.add(symbol);
		}
		return toCheckList;
	}
	
	private <T> void addIfNotNull(Collection<T> collection, T item) {
		if(item != null) {
			collection.add(item);
		}
	}
	

	public int length() {
		return winningPattern.length;
	}
	public int indexAt(int i) {		
		return winningPattern[i];		
	}


	@Override
	public PatternType getPatternType() {
		return PatternType.LINE;
	}


}
