package com.wdfall.slot.business.simulator.factories;

import java.util.ArrayList;
import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.factories.IFRandomSymbolGroupPool.ShuffleRandomSymbolGroupPool;
import com.wdfall.slot.business.simulator.factories.IFRandomSymbolGroupPool.SymbolGroup;
import com.wdfall.slot.business.simulator.property.EmulatorReelProperty;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.property.SymbolDefine;

/**
 * 릴 심블 생성 class
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class ReelSymbolSelector implements IFReelSymbolSelector {
	private EmulatorSymbolFactory symbolFactory;
	private GameProperty property;
	
	public void setSymbolFactory(EmulatorSymbolFactory symbolFactory) {
		this.symbolFactory = symbolFactory;
	}
	
	public void setProperty(GameProperty property) {
		this.property = property;
	}

	public List<EmulatorSymbol> select(EmulatorReelProperty reel, int amount) {
		//shuffle method
		IFRandomSymbolGroupPool<String> pool = new ShuffleRandomSymbolGroupPool<String>();
		List<String> objectCodeList = reel.getSymbolObjectCodes();
		for(String objectCode : objectCodeList) {
			SymbolGroup<String> groupParam = convertObjectCodeToSymbolGroup(objectCode);
			int chance = reel.count(objectCode);
			pool.addChance(groupParam, chance);
		}
		
		List<String> selectedList = pool.get(amount);
		
		//
		List<EmulatorSymbol> selectedSymbols = new ArrayList<EmulatorSymbol>();
		for(int i=0; i< amount; i++){
			String selected = selectedList.get(i);
			EmulatorSymbol symbol = symbolFactory.get(selected);
			selectedSymbols.add( symbol );
		}
		
		return selectedSymbols;
	}
	

	private SymbolGroup<String> convertObjectCodeToSymbolGroup(String objectCode) {
		SymbolDefine sd = property.getSymbolDefineByObjectCode(objectCode);
		SymbolGroup<String> result = new SymbolGroup<>();
		result.setObj(sd.getSymbolCode());
		result.setGroupSize(sd.getGroupSize());
		return result;
	}

	
	/*//old
	public List<EmulatorSymbol> select(EmulatorReelProperty reel, int amount) {
		RandomPool<String> pool = new RandomPool<String>(false);
		
		List<String> symbols = reel.getSymbolObjectCodes();
		for( String symbol : symbols ){
			int count = reel.count(symbol);
			pool.addChance(symbol, count);
		}
		
		List<EmulatorSymbol> selectedSymbols = new ArrayList<EmulatorSymbol>();
		for(int i=0; i< amount; i++){
			String selected = pool.get();
			EmulatorSymbol symbol = symbolFactory.get(selected);
			selectedSymbols.add( symbol );
		}
		
		return selectedSymbols;
	}
	*/

}
