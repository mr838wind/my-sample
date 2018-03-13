package com.wdfall.slot.business.simulator.loader;

import com.wdfall.slot.business.simulator.property.GameProperty;
/**
 * IFGamePropertyProvider
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public interface IFGamePropertyProvider extends IFPropertyProvider {

	@Override
	public GameProperty parse(PropertyDataMap data);
	
}
