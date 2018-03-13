package com.wdfall.slot.business.simulator.display;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.EmulatorSymbol;

import lombok.extern.slf4j.Slf4j;
/**
 * nudging 효과:  symbol을 아래/우 로 민다.
 * 우선순위:
 * 	1) 많은것 기준
 * 	2) 우에것 먼저
 * 
 * @author chhan
 *
 */
@Slf4j
public class NudgingSymbolDisplayCreatorDecorator extends DisplayCreatorDecorator {

	private int nudgingGroupSize = 3;
	private EmulatorSymbol nudgingSymbol;
	
	public NudgingSymbolDisplayCreatorDecorator(IFDisplayCreator displayCreator, EmulatorSymbol nudgingSymbol, int nudgingGroupSize) {
		super(displayCreator);
		this.nudgingSymbol = nudgingSymbol;
		this.nudgingGroupSize = nudgingGroupSize;
		
		if(nudgingGroupSize <= 1) {
			throw new IllegalArgumentException("nudgingSymbolLength should > 1");
		}
	}

	private enum ReelEdgeEnum{
		UP,DOWN
	}
	
	@Override
	public Snapshot createReels() {
		Snapshot spinResult = displayCreator.createReels();
		
		for(EmulatorReel reel : spinResult.getPlayReels()) {
			handleEachReel(reel);
		}
		
		return spinResult;
	}

	private void handleEachReel(EmulatorReel reel) {
		//log.debug("---- reel start : {}", reel); 
		int countUp = countNudgingSymbol(reel, ReelEdgeEnum.UP);
		int countDown = countNudgingSymbol(reel, ReelEdgeEnum.DOWN);
		
		//log.debug("countUp,countDown = {}, {}", countUp,countDown); 
		if(countUp >= countDown) {
			//값이 같을 경우 우에것이 큰것과 같은 효과
			if(ifNeedShifting(countUp)) {
				shiftReelDown(reel);
			}
			
		} else if(countUp < countDown) {
			if(ifNeedShifting(countDown)) {
				shiftReelUp(reel);
			}
		}
		//log.debug("---- reel end : {}", reel); 
	}

	private boolean ifNeedShifting(int countUp) {
		return countUp > 0 && countUp % nudgingGroupSize != 0;
	}
	
	private void shiftReelUp(EmulatorReel reel) {
		reel.shiftUp(getNudgingSymbol());
	}
	
	private void shiftReelDown(EmulatorReel reel) {
		reel.shiftDown(getNudgingSymbol());
	}
	
	private EmulatorSymbol getNudgingSymbol() {
		return nudgingSymbol;
	}

	private int countNudgingSymbol(EmulatorReel reel, ReelEdgeEnum edge) {
		int result = 0;
		
		if(edge.equals(ReelEdgeEnum.UP)) {
			for(int i=0; i<reel.size(); i++) {
				EmulatorSymbol symbol = reel.getSymbol(i);
				if(symbol.getSymbolCode().equals(nudgingSymbol.getSymbolCode())) {
					result++;
				} else {
					break;
				}
			}
			
		} else {
			for(int i= reel.size()-1; i>-1; i--) {
				EmulatorSymbol symbol = reel.getSymbol(i);
				if(symbol.getSymbolCode().equals(nudgingSymbol.getSymbolCode())) {
					result++;
				} else {
					break;
				}
			}
		}
		
		return result;
	}

	
	
}
