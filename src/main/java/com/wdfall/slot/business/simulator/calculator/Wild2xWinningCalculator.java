package com.wdfall.slot.business.simulator.calculator;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.finder.WinningFinder.WinningResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;

import lombok.extern.slf4j.Slf4j;
/**
 * wild2x가 있는 line의 payout을 2배로 늘려준다.
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class Wild2xWinningCalculator extends WinningCalculator {

	
	private static final int WILD_MULTIPLE = 2;

	/**
	 * 좌측:심블 
	 * 우측:배수
	 * WinningCalculator 계산 결과에 기초하여 
	 * wild2x가 있는 line의 payout을 2배로 늘려준다.
	 * wild2x 개수만큼 2를 곱해준다. 예를 들면 wil2x 3개 --- *2*2*2 = *8
	 */
	@Override
	public List<WinningResult> calculatePayout(List<List<EmulatorWinningPatternResult>> matchedResults) {
		List<WinningResult> results = super.calculatePayout(matchedResults);
		
		multipleLinePayout(results);
		
		return results;
	}

	private void multipleLinePayout(List<WinningResult> results) {
		
		if(!CollectionUtils.isEmpty(results)) {
			for(WinningResult item : results) {
				int countWild2x = countWild2x(item.getHittedSymbols());
				if(countWild2x > 0) {
					int multipleValue = (int)Math.pow(WILD_MULTIPLE, countWild2x);
					int originPayout = item.getPayout();
					int newPayout = (int)(originPayout * multipleValue);
					item.setPayout(newPayout);
					item.setMultiple(multipleValue);
					//log.debug(" !! WinningResult = {}", item);
					//log.debug(" !! originPayout = {}, newPayout = {}", originPayout, newPayout);
				}
			}
		}
	}

	private int countWild2x(List<EmulatorSymbol> list) {
		int result = 0;
		if(!CollectionUtils.isEmpty(list)) {
			for(EmulatorSymbol symbol: list) {
				if(symbol.isWild()) {
					result++;
				}
			}
		}
		return result;
	}
	
}
