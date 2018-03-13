package com.wdfall.slot.model.vo;

import com.wdfall.slot.business.simulatorframework.BasicAccResult;

import lombok.Data;

@Data
public class BonusResultVO extends BasicResultVO {

	//
	private BonusInputVO inputVO;
	
	
	public void setBasicAccResult(BasicAccResult result) {
		this.setPlayGameCount(result.getPlayGameCount());
		this.setTotalBet(result.getTotalBet());
		this.setPayout(result.getWinPayout());
		this.setPayoutRate(result.getPayoutRate());
		this.setWinGameRate(result.getWinGameRate());
	}
	
	
}
