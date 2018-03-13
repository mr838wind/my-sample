package com.wdfall.slot.business.simulator.loader.provider;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * GameProperty와 excel 사이의 매칭 class
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Data
public class GamePropertyParam {
	
	private int playThreadCount;
	private int playThreadPoolSize;
	private long playGameCount;
	
	private int[] groundScale;
	
	private List<SymbolDefineParam> symbolDefineParamList;
	
	private List<Map<String, Integer>> reelPropertiesParamList;
	

	private int[][] winLinePatternParamList;
	
	// max 매칭 심블 개수
	private int firstPayoutSymbolCount;
	
	

	@Data
	public static class SymbolDefineParam {
		
		public SymbolDefineParam() {
			super();
		}
		
		public SymbolDefineParam(String symbolId,int groupSize, String objectCode, int type, int firstPayoutSymbolCount, int[] payouts) {
			super();
			this.symbolId = symbolId;
			this.groupSize = groupSize;
			this.objectCode = objectCode;
			this.type = type;
			this.firstPayoutSymbolCount = firstPayoutSymbolCount;
			this.payouts = payouts;
		}
		
		
		private String symbolId;
		// 추가
		private int groupSize;
		private String objectCode;
		
		/*
		 * symbol 개수가 많은데부터 적은대로 
		 */
		private int[] payouts;

		private int type;
		//payouts[0]에 대응하는 symbol 개수 5
		private int firstPayoutSymbolCount = 5;
	}
	
}
