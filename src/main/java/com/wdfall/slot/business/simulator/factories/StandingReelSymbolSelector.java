package com.wdfall.slot.business.simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.property.EmulatorReelProperty;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Deprecated
@Slf4j
public class StandingReelSymbolSelector implements IFReelSymbolSelector {
	
	private String standingSymbolId;
	
	private EmulatorSymbolFactory symbolFactory; 
	
	public void setSymbolFactory(EmulatorSymbolFactory symbolFactory) {
		this.symbolFactory = symbolFactory;
	}
	
	/*
	 * << Wild 3개 붙어다니는 형식으로 뽑는다. >>
	 * 1. wild를 3으로 나눈다.
	 * 2. 나눈 개수로 shuffle한고 줄 세운다.
	 * 3. shuffle한후 wild를 3개로 늘린다.
	 * 4. 앞의 몇개를 가져온다.
	 */
	@Override
	public List<EmulatorSymbol> select(EmulatorReelProperty reel, int amount) {
		validateStandingCount(reel);
		
		List<String> list = getStandingDivide3SymbolList(reel);
		Collections.shuffle(list);
		processStandingMultiply3ToSymbolList(list);
		
		List<EmulatorSymbol> selectedSymbols = getSymbolFromMiddle(list, amount);
		return selectedSymbols;
	}
	
	private List<EmulatorSymbol> getSymbolFromMiddle(List<String> list, int amount) {
		int middle = (list.size() - amount) / 2;
		List<EmulatorSymbol> resultList = new ArrayList<>();
		for(int i=middle; i<middle + amount; i++) {
			String symbolId = list.get(i);
			EmulatorSymbol symbol = symbolFactory.get(symbolId); 
			resultList.add( symbol );
		}
		return resultList;
	}

	private void processStandingMultiply3ToSymbolList(List<String> list) {
		List<Integer> wildIndexs = new ArrayList<>();
		for(int i=0; i<list.size(); i++) {
			String symbolId = list.get(i);
			if(standingSymbolId.equals(symbolId)) {
				wildIndexs.add(i);
			}
		}
		
		Collections.reverse(wildIndexs);
		for(Integer idx : wildIndexs) {
			//add 2 wild
			list.add(idx, standingSymbolId);
			list.add(idx, standingSymbolId);
		}
	}

	private List<String> getStandingDivide3SymbolList(EmulatorReelProperty reel) {
		List<String> resultList = new ArrayList<>();
		
		List<String> symbolIds = reel.getSymbolObjectCodes();
		for(String symbolId: symbolIds) {
			int count = -1;
			if(standingSymbolId.equals(symbolId)) {
				count = reel.count(symbolId) / 3;
			} else {
				count = reel.count(symbolId);
			}
			
			for(int i=0; i<count; i++) {
				resultList.add(symbolId);
			}
		}
		
		return resultList;
	}

	private void validateStandingCount(EmulatorReelProperty reel) {
		int wildCount = reel.count(standingSymbolId);
		if(wildCount % 3 != 0) {
			throw new IllegalArgumentException("standing symbol numbers must be 3*n.");
		}
	}

	public void setStandingSymbolId(String standingSymbolId) {
		this.standingSymbolId = standingSymbolId;		
	}

}
