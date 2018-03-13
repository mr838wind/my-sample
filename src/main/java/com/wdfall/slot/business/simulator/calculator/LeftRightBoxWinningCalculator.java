package com.wdfall.slot.business.simulator.calculator;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.wdfall.slot.business.simulator.factories.RandomOneResultGenerator;
import com.wdfall.slot.business.simulator.finder.WinningFinder.WinningResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;

import lombok.extern.slf4j.Slf4j;
/**
 * 좌우측 box WinningCalculator
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class LeftRightBoxWinningCalculator extends WinningCalculator {

	private RandomOneResultGenerator leftGenerator;
	private RandomOneResultGenerator rightGenerator;
	
	public void setLeftGenerator(RandomOneResultGenerator leftGenerator) {
		this.leftGenerator = leftGenerator;
	}
	
	public void setRightGenerator(RandomOneResultGenerator rightGenerator) {
		this.rightGenerator = rightGenerator;
	}
	
	/**
	 * 좌측:심블 
	 * 우측:배수
	 * WinningCalculator 계산 결과에 기초하여 
	 * 좌측 심블과 같은 line의 payout을 우측 배수로 늘려준다.
	 */
	@Override
	public List<WinningResult> calculatePayout(List<List<EmulatorWinningPatternResult>> matchedResults) {
		List<WinningResult> results = super.calculatePayout(matchedResults);
		
		multipleLinePayout(results);
		
		return results;
	}

	private void multipleLinePayout(List<WinningResult> results) {
		String leftSymbolCode = generateLeftBoxResult();
		double rightMultiple = generateRightBoxResult();
		
		if(!CollectionUtils.isEmpty(results)) {
			for(WinningResult item : results) {
				if(leftSymbolCode.equals(item.getSymbolId())) {
					int originPayout = item.getPayout();
					int newPayout = (int)(originPayout * rightMultiple);
					item.setPayout(newPayout);
					item.setMultiple(rightMultiple);
					//log.debug(" !! WinningResult = {}", item);
					//log.debug(" !! originPayout = {}, newPayout = {}", originPayout, newPayout);
				}
			}
		}
	}

	private String generateLeftBoxResult() {
		String result = leftGenerator.getRandomOneResult();
		//log.debug("left box: {}", result);
		return result;
	}

	private double generateRightBoxResult() {
		String result = rightGenerator.getRandomOneResult();
		//log.debug("right box: {}", result);
		return Double.valueOf(result);
	}
	
}
