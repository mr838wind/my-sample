package com.wdfall.slot.business.bonus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wdfall.slot.business.bonus.Stage01BonusProperty.WheelColor;
import com.wdfall.slot.business.bonus.Stage01BonusProperty.WheelPiece;
import com.wdfall.slot.business.simulator.factories.IFRandomSymbolGroupPool;
import com.wdfall.slot.business.simulatorframework.Stage01BonusResult;

import lombok.extern.slf4j.Slf4j;
/**
 * Stage01BonusGame
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class Stage01BonusGame {

	private Stage01BonusProperty bonusProperty;
	
	private List<WheelPiece> wheel;
	
	private Random randomPlayerPool = new Random();
	private RandomWheelPool randomWheelPool;
	
	public void setBonusProperty(Stage01BonusProperty bonusProperty) {
		this.bonusProperty = bonusProperty;
	}

	public Stage01BonusGame() {
		
	}
	
	public void init() {
		wheel = new ArrayList<>();
		wheel.addAll(bonusProperty.getWheelPieceList());
		randomWheelPool = new RandomWheelPool(wheel);
		//log.debug("wheel = {}", wheel);
		
	}
	
	public Stage01BonusResult run() {
		
		Stage01BonusResult result = new Stage01BonusResult();
		result.setPlayGameCount(1);
		result.setTotalBet(1);
		
		// 1. red,black선택
		WheelColor playerColor = selectPlayer();
		
		// 2. random으로 돌림 
		// 배수를 더해줌.
		// 같은색이면 다시 선택, 다른색이면 종료.
		int totalMultiple = 0;
		while(true) {
			WheelPiece selection = spinWheel();
			totalMultiple += selection.getMultiple();
			
			result.setPlaySubGameCount(result.getPlaySubGameCount() + 1);
			if(!playerColor.equals(selection.getColor())) {
				break;
			} else {
				result.setWinGameCount(result.getWinGameCount() + 1);
			}
		}
		
		result.setWinPayout(totalMultiple);
		
		//log.debug("totalMultiple = {}", totalMultiple);
		return result;
	}
	

	private WheelPiece spinWheel() {
		WheelPiece result = randomWheelPool.getRandomOneResult();
		//log.debug("wheelPiece = {}", result); 
		return result;
	}

	private WheelColor selectPlayer() {
		int randomColor = randomPlayerPool.nextInt(2);
		WheelColor player = WheelColor.fromInt(randomColor);
		//log.debug("player = {}", player);
		return player;
	}
	
	
	public static class RandomWheelPool {

		private List<WheelPiece> wheel;
		private IFRandomSymbolGroupPool<Integer> randomPool;
		
		public RandomWheelPool(List<WheelPiece> wheel) {
			this.wheel = wheel;
			//
			randomPool = new IFRandomSymbolGroupPool.ShuffleRandomSymbolGroupSize1Pool<>();
			for(int i=0; i<wheel.size(); i++) {
				WheelPiece entry = wheel.get(i);
				randomPool.addGroupSize1Chance(i, entry.getFrequency());
			}
		}
		
		public WheelPiece getRandomOneResult() {
			int choice = randomPool.get(1).get(0);
			return wheel.get(choice);
		}
		
	}
	
	
}
