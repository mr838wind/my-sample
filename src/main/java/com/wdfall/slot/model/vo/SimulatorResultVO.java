package com.wdfall.slot.model.vo;

import com.wdfall.slot.business.simulator.result.AccEmulateResult;

import lombok.Data;

@Data
public class SimulatorResultVO extends BasicResultVO {

	/** 라인 지급률 */
	private double linePayoutRate;
	/** 라인  승률 */
	private double winLineRate;
	/** scatter 지급률 */
	private double scatterPayoutRate;
	/** scatter 승률 */
	private double winScatterRate;
	/** bonus 지급률 */
	private double scatterBonusPayoutRate;
	/** freespin 지급률 */
	private double scatterFreespinPayoutRate;
	/** bonus 승률 */
	private double winScatterBonusRate;
	/** freespin 승률 */
	private double winScatterFreespinRate;
	
	//
	/** 입력 vo */
	private SimulatorInputVO inputVO;
	
	
	public void setAccEmulateResult(AccEmulateResult result) {
		this.setPlayGameCount(result.getPlayGameCount());
		this.setTotalBet(result.getTotalBet());
		this.setPayout(result.getWinPayout());
		this.setPayoutRate(result.getPayoutRate());
		this.setWinGameRate(result.getWinGameRate());
		this.setLinePayoutRate(result.getLinePayoutRate());
		this.setWinLineRate(result.getWinLineRate());
		this.setScatterPayoutRate(result.getScatterPayoutRate());
		this.setWinScatterRate(result.getWinScatterRate());
		this.setScatterBonusPayoutRate(result.getScatterBonusPayoutRate());
		this.setScatterFreespinPayoutRate(result.getScatterFreespinPayoutRate());
		this.setWinScatterBonusRate(result.getWinScatterBonusRate());
		this.setWinScatterFreespinRate(result.getWinScatterFreespinRate());
	}
	
	
}
