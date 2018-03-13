package com.wdfall.slot.business.simulatorframework;

import lombok.extern.slf4j.Slf4j;
/**
 * 기본 집계 result
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class BasicAccResult {
	
	// 합계
	protected long playGameCount = 0; //game 횟수
	protected long totalBet = 0;  	//bet 
	protected long winPayout = 0; //payout
	protected long winGameCount = 0; //win 게임
	
	
	public void append(BasicResult result) {
		this.playGameCount += result.playGameCount;
		this.totalBet += result.getTotalBet();
		this.winPayout += result.getWinPayout();
		this.winGameCount += result.getWinGameCount();
	}
	
	public void append(BasicAccResult accResult) {
		playGameCount += accResult.playGameCount;
		totalBet += accResult.totalBet;
		winPayout += accResult.winPayout;
		winGameCount += accResult.winGameCount;
	}
	
	//==============
	public double getPayoutRate(){
		return (double)winPayout/totalBet;
	}
	
	public double getWinGameRate() {
		return (double)winGameCount/playGameCount;
	}
	
	//==============
	public long getPlayGameCount() {
		return playGameCount;
	}

	public long getTotalBet() {
		return totalBet;
	}

	public long getWinPayout() {
		return winPayout;
	}

	//-----------------------------------------
	public void printTotalResult(){
		log.debug("==================================================");
		BasicAccResult result = this;
		log.debug("-----------------------");
		log.debug(String.format(" << PayoutRate= %f >> ", result.getPayoutRate())); 
		log.debug("-----------------------");
		log.debug( String.format("GameCount=%,d, TotalBet=%,d, winPayout=%,d ", result .getPlayGameCount() , result.getTotalBet(), result.getWinPayout()));
		log.debug( String.format("WinGameRate=%f", result.getWinGameRate()));
		log.debug("==================================================");
	}

}
