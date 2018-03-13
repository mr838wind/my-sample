package com.wdfall.slot.business.simulatorframework;

import lombok.Data;

/**
 * 기본 하나 게임의 result
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Data
public class BasicResult {
	
	// 실행 횟수
	protected long playGameCount = 0;
	// win 횟수
	protected long winGameCount = 0;
	// 베팅금액
	protected long totalBet = 0;
	// payout
	protected long winPayout = 0;
	
	
}
