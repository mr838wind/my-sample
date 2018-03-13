package com.wdfall.slot.business.simulator.property;

import lombok.Data;

/**
 * 심블 정의
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Data
public class SymbolDefine {

	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_WILD = 1;
	public static final int TYPE_SCATTER_BONUS = 2;
	public static final int TYPE_SCATTER_FREESPIN = 3;
	
	
	private String symbolCode;
	private int groupSize;
	private String objectCode;
	//-------------------------------------------------
	//	e.g.:	symbolCode,	groupSize,	objectCode(convention: "code.size") 
	//			A			1			A.1
	//			N1			2			N1.2
	//-------------------------------------------------
	
	/*
	 * symbol 개수가 많은데부터 적은대로 
	 */
	private int[] payouts;

	private int type;
	//payouts[0]에 대응하는 symbol 개수 5
	private int firstPayoutSymbolCount = 5;
	
	public SymbolDefine(String symbolCode, int groupSize, String objectCode, int type, int firstPayoutSymbolCount) {
		this.symbolCode = symbolCode;
		this.groupSize = groupSize;
		this.objectCode = objectCode;
		this.type = type;
		this.firstPayoutSymbolCount = firstPayoutSymbolCount;
	}

	public SymbolDefine setPayout(int... payouts ) {
		 this.payouts = new int[ payouts.length ];
		 for( int i=0 ; i< payouts.length; i++ ){
			 this.payouts[i] = payouts[i];
		 }
		 return this;
	}

	
	public int getPayout( int count ){
		int idx = firstPayoutSymbolCount - count;
		if( idx < 0 || idx >= payouts.length) return 0;
		return payouts[idx];
	}

	//매칭 필요한 최소 개수
	public int getNeedForWin() {
		//TODO: payout 자체가 0 인 경우 고려
		//0 --- firstPayoutSymbolCount
		//payouts.length - 1 --- result
		int result = firstPayoutSymbolCount - (payouts.length - 1);
		return result;
	}

	
}
