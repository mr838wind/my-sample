package com.wdfall.slot.model.vo;

import com.wdfall.slot.business.utils.CommonUtils;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;

import lombok.Data;

@Data
public class BasicResultVO {

	/** seq */
	protected int seq;
	/** 입력 seq */
	protected int inputSeq; //FK
	/** stage */
	protected RunGameStage stage;
	/** 시간 */
	protected String registerTime = CommonUtils.getCurrentTimestamp(); //default값
	/** 사용자 */
	protected String username;

	//
	/** 실행 횟수 */
	protected long playGameCount;
	/** 베팅금액 */
	protected long totalBet;
	/** 지급액 */
	protected long payout;
	/** 지급률 */
	protected double payoutRate;
	/** 승률 */
	protected double winGameRate;
	
}
