package com.wdfall.slot.business.simulator.factories;

import java.util.Map;
/**
 * random 하나 결과 생성 class
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class RandomOneResultGenerator {

	private IFRandomSymbolGroupPool<String> randomPool;
	
	public RandomOneResultGenerator(Map<String, Integer> boxProperty) {
		randomPool = new IFRandomSymbolGroupPool.ShuffleRandomSymbolGroupSize1Pool<>();
		for(Map.Entry<String, Integer> entry : boxProperty.entrySet()) {
			String symbol = entry.getKey();
			int chance = entry.getValue();
			randomPool.addGroupSize1Chance(symbol, chance);
		}
	}
	
	public String getRandomOneResult() {
		String randomResult = randomPool.get(1).get(0);
		return randomResult;
	}
	
}
