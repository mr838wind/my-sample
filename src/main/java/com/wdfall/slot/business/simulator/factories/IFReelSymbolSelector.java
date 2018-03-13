package com.wdfall.slot.business.simulator.factories;

import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.property.EmulatorReelProperty;
/**
 * 릴 심블 생성 interface
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public interface IFReelSymbolSelector {

	public List<EmulatorSymbol> select(EmulatorReelProperty reel, int amount);
	
}
