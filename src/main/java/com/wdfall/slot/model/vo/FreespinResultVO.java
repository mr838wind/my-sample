package com.wdfall.slot.model.vo;

import com.wdfall.slot.business.simulator.result.FreespinAccEmulateResult;

import lombok.Data;

@Data
public class FreespinResultVO extends BasicResultVO {

	// freespin 총수
	private long freespinScatterCountTotal;
	
	//
	private FreespinInputVO inputVO;
	
	
	public void setFreespinAccEmulateResult(FreespinAccEmulateResult result) {
		this.setPlayGameCount(result.getPlayGameCount());
		this.setTotalBet(result.getTotalBet());
		this.setPayout(result.getWinPayout());
		this.setPayoutRate(result.getPayoutRate());
		this.setWinGameRate(result.getWinGameRate());
		this.setFreespinScatterCountTotal(result.getFreespinScatterCountTotal());
	}
	
	
}
