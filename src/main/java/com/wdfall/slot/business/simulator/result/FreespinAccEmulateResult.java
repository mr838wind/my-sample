package com.wdfall.slot.business.simulator.result;

import com.wdfall.slot.business.simulatorframework.BasicAccResult;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
/**
 * Freespin 집계 result
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
@Data
public class FreespinAccEmulateResult extends AccEmulateResult {
	
	//freespin 실행수
	private long freespinScatterCountTotal = 0; 
	//freespin hit수
	private long freespinWinGameCount = 0; 
	
	// 승률 = (원Hit + freeHit)/(원횟수+free횟수) 
	@Override
	public double getWinGameRate() {
		//log.debug("winGameCount={}", winGameCount);
		//log.debug("playGameCount={}", playGameCount);
		//log.debug("freespinWinGameCount={}", freespinWinGameCount);
		//log.debug("freespinScatterCountTotal={}", freespinScatterCountTotal);
		
		return (double)(winGameCount + freespinWinGameCount)/(playGameCount + freespinScatterCountTotal);
	}
	
	// freespin scatter로 공짜로 돌리는 경우
	public void appendFreespin(EmulateResult result) {
		freespinScatterCountTotal++;
		if( result.getWinningCount() > 0 ){
			freespinWinGameCount++;
			winPayout += result.getPayout();
		}
	}
	
	@Override
	public void append(BasicAccResult _accResult) {
		super.append(_accResult);
		
		FreespinAccEmulateResult accResult = (FreespinAccEmulateResult) _accResult;
		this.freespinScatterCountTotal += accResult.getFreespinScatterCountTotal();
		this.freespinWinGameCount += accResult.getFreespinWinGameCount();
	}
	
	@Override
	public void printTotalResult(){
		log.debug("==================================================");
		FreespinAccEmulateResult result = this;
		log.debug("-----------------------");
		log.debug(String.format(" << PayoutRate= %f >> ", result.getPayoutRate())); 
		log.debug("-----------------------");
		log.debug( String.format("GameCount=%,d, TotalBet=%,d ", result .getPlayGameCount() , result.getTotalBet()));
		log.debug( String.format("WIN : FreespinScatterCountTotal=%d ",
				result.getFreespinScatterCountTotal()) );
		log.debug(String.format(" << winGameRate= %f >> ", result.getWinGameRate()));
		log.debug("==================================================");
	}
	
}
