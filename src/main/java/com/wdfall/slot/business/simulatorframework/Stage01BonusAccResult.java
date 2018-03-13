package com.wdfall.slot.business.simulatorframework;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
/**
 * stage01 bonus 집계 result
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
@Data
public class Stage01BonusAccResult extends BasicAccResult {
	
	private long playSubGameCount;
	
	@Override
	public void append(BasicResult _result) {
		Stage01BonusResult result = (Stage01BonusResult)_result;
		super.append(result);
		this.playSubGameCount += result.getPlaySubGameCount();
	}
	
	@Override
	public void append(BasicAccResult _accResult) {
		Stage01BonusAccResult accResult = (Stage01BonusAccResult)_accResult;
		super.append(accResult);
		this.playSubGameCount += accResult.getPlaySubGameCount();
	}
	
	/**
	 * win횟수/휠 돌린 횟수
	 */
	@Override
	public double getWinGameRate() {
		return (double)winGameCount/playSubGameCount;
	}

}
