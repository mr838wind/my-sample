package com.wdfall.slot.business.simulator.result;

import com.wdfall.slot.business.simulatorframework.BasicAccResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 집계 result
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class AccEmulateResult extends BasicAccResult {
	// TODO: add to db:
	//protected List<EmulateResult> rawData = new ArrayList<>();
	
	// 합계
	//protected long playGameCount = 0; //game 횟수
	//protected long totalBet = 0;  	//bet 
	//protected long winPayout = 0; //payout
	//protected long winGameCount = 0; //win 게임
	
	//line: 
	protected long playLineCount = 0;	//line 횟수 == 총bet
	protected long winLineCount = 0;
	protected long winLinePayout = 0; 
	
	//scatter: 
	protected long playScatterCount = 0; // == palyGameCount
	protected long winScatterCount = 0;
	protected long winScatterPayout = 0;
	
	//scatter detail
	protected long winScatterBonusCount = 0;
	protected long winScatterBonusPayout = 0;
	
	protected long winScatterFreespinCount = 0;
	protected long winScatterFreespinPayout = 0;
	
	
	public void append(EmulateResult result) {
		playGameCount++;
		totalBet += result.getWinningLines().size();
		
		playLineCount += result.getWinningLines().size();
		
		playScatterCount++;
		
		if( result.getWinningCount() > 0 ){
			winGameCount++;
			winPayout += result.getPayout();
			
			if(result.getWinningLineCount() > 0) {
				winLineCount += result.getWinningLineCount();
				winLinePayout += result.getLinePayout();
			}
		
			if(result.getWinningScatterCount() > 0) {
				winScatterCount += result.getWinningScatterCount();
				winScatterPayout += result.getScatterPayout();
				
				// detail
				long scatterBonusPayout = result.getScatterBonusPayout();
				if(scatterBonusPayout > 0) {
					winScatterBonusCount++;
					winScatterBonusPayout += scatterBonusPayout;
				}
				
				long scatterFreespinPayout = result.getScatterFreespinPayout();
				if(scatterFreespinPayout > 0) {
					winScatterFreespinCount++;
					winScatterFreespinPayout += scatterFreespinPayout;
				}
			}
		}
		
	}
	
	@Override
	public void append(BasicAccResult _accResult) {
		AccEmulateResult accResult = (AccEmulateResult)_accResult;
		
		playGameCount += accResult.playGameCount;
		totalBet += accResult.totalBet;
		winGameCount += accResult.winGameCount;
		winPayout += accResult.winPayout;
		
		playLineCount += accResult.playLineCount;
		winLineCount += accResult.winLineCount;
		winLinePayout += accResult.winLinePayout;
		
		playScatterCount += accResult.playScatterCount;
		winScatterCount += accResult.winScatterCount;
		winScatterPayout += accResult.winScatterPayout;
		
		// detail
		winScatterBonusCount += accResult.winScatterBonusCount;
		winScatterBonusPayout += accResult.winScatterBonusPayout;
		
		winScatterFreespinCount += accResult.winScatterFreespinCount;
		winScatterFreespinPayout += accResult.winScatterFreespinPayout;
		
	}
	
	//==============
	public double getWinGameRate() {
		return (double)winGameCount/playGameCount;
	}

	public double getWinLineRate() {
		return (double)winLineCount/playLineCount;
	}
	
	public double getWinScatterRate() {
		return (double)winScatterCount/playScatterCount;
	}

	public double getPayoutRate(){
		return (double)winPayout/totalBet;
	}
	
	public double getLinePayoutRate(){
		return (double)winLinePayout/totalBet;
	}
	
	public double getScatterPayoutRate(){
		return (double)winScatterPayout/totalBet;
	}
	
	// scatter detail 승률 추가
	public double getWinScatterBonusRate(){
		return (double)winScatterBonusCount/playScatterCount;
	}
	
	public double getWinScatterFreespinRate(){
		return (double)winScatterFreespinCount/playScatterCount;
	}
	
	public double getScatterBonusPayoutRate(){
		return (double)winScatterBonusPayout/totalBet;
	}
	
	public double getScatterFreespinPayoutRate(){
		return (double)winScatterFreespinPayout/totalBet;
	}
	
	
	//==============
	public long getPlayGameCount() {
		return playGameCount;
	}

	public long getTotalBet() {
		return totalBet;
	}

	public long getWinGameCount() {
		return winGameCount;
	}

	public long getWinPayout() {
		return winPayout;
	}

	public long getPlayLineCount() {
		return playLineCount;
	}

	public long getWinLineCount() {
		return winLineCount;
	}

	public long getWinLinePayout() {
		return winLinePayout;
	}

	public long getPlayScatterCount() {
		return playScatterCount;
	}

	public long getWinScatterCount() {
		return winScatterCount;
	}

	public long getWinScatterPayout() {
		return winScatterPayout;
	}
	
	//-----------------------------------------
	@Override
	public void printTotalResult(){
		log.debug("==================================================");
		AccEmulateResult result = this;
		log.debug("-----------------------");
		log.debug(String.format(" << PayoutRate= %f >> ", result.getPayoutRate())); 
		log.debug("-----------------------");
		log.debug( String.format("GameCount=%,d, TotalBet=%,d ", result .getPlayGameCount() , result.getTotalBet()));
		log.debug( String.format("WIN : PayoutRate=%.4f,  winGameRate=%.4f ",
				result.getPayoutRate(), result.getWinGameRate()) );
		log.debug( String.format("WIN : LinePayoutRate=%.4f,  WinLineRate=%.4f ",
				result.getLinePayoutRate(), result.getWinLineRate()) );
		log.debug( String.format("WIN : ScatterPayoutRate=%.4f,  WinScatterRate=%.4f ",
				result.getScatterPayoutRate(), result.getWinScatterRate()) );
		// scatter detail:
		log.debug( String.format("WIN : ScatterBonusPayoutRat=%.4f,  ScatterFreespinPayoutRate=%.4f ",
				result.getScatterBonusPayoutRate(), result.getScatterFreespinPayoutRate()) );
		log.debug("==================================================");
	}
	
	//-----------------------------------------
}
