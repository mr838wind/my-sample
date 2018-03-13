package com.wdfall.slot.business.simulator.calculator;

import java.util.ArrayList;
import java.util.List;

import com.wdfall.slot.business.simulator.finder.WinningFinder.WinningResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.PatternType;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.property.SimulatorPayoutPropertyTable;
import com.wdfall.slot.business.simulator.property.SymbolDefine;

import lombok.extern.slf4j.Slf4j;
/**
 * payout 결과 계산
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class WinningCalculator extends AbstractWinningCalculator {

	protected GameProperty property;
	
	public void setProperty(GameProperty property) {
		this.property = property;
	}

	@Override
	public List<WinningResult> calculatePayout(List<List<EmulatorWinningPatternResult>> matchedResults) {
		List<WinningResult> results = new ArrayList<>();
		
		for(List<EmulatorWinningPatternResult> item : matchedResults) {
			// only get max payout from matchResultList
			WinningResult winningResult = getMaxWinningResult(item);
			if(winningResult != null) {
				results.add(winningResult);
			}
			
		}
		
		return results;
	}
	
	
	
	protected WinningResult getMaxWinningResult(List<EmulatorWinningPatternResult> matchResultList) {
		WinningResult winningResult = null;
		
		if( matchResultList.isEmpty() ){
			return null;
		}
		
		long maxPayout = -1;
		EmulatorWinningPatternResult maxMatchResult = null;
		for(int i=0; i< matchResultList.size(); i++) {
			EmulatorWinningPatternResult matchResult = matchResultList.get(i);
			long payout = getPayout(matchResult);
			if(payout > 0 && payout > maxPayout) {
				maxMatchResult = matchResult;
				maxPayout = payout;
			}
		}
		
		if( maxPayout  > 0 ){
			winningResult = new WinningResult();
			winningResult.setSymbolId(maxMatchResult.getHittedSymbolId());
			winningResult.setCount(maxMatchResult.getHittedCount());
			winningResult.setHittedSymbols(maxMatchResult.getHittedSymbols());			
			winningResult.setPattern(maxMatchResult.getPattern());
			// scatter인 경우 총 bet수의 배수이다.
			int resultPayout = (int)maxPayout;
			if(PatternType.SCATTER.equals(maxMatchResult.getPattern())) {
				resultPayout = resultPayout * property.getTotalBet();
			}
			winningResult.setPayout(resultPayout);
			
			//log.debug("Hitted !!  Symbol={}, Count={}, Payout={}", maxMatchResult.getHittedSymbolId(), maxMatchResult.getHittedCount(), winningResult.getPayout()  );
		}
		return winningResult;
	}

	protected long getPayout(EmulatorWinningPatternResult matchResult) {
		SimulatorPayoutPropertyTable payoutTable = property.getPayoutTable();
		SymbolDefine payoutProperty = payoutTable.get( matchResult.getHittedSymbolId()  );
		if( payoutProperty == null ) throw new NullPointerException( "Cannot find Payout : " + matchResult.getHittedSymbolId() );
		long payout = payoutTable.get( matchResult.getHittedSymbolId()  ).getPayout(matchResult.getHittedCount());
		return payout;
	}

}
