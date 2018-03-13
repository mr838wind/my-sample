package com.wdfall.slot.business.simulator.loader;

import com.wdfall.slot.business.simulatorframework.BasicProperty;
/**
 * IFBonusPropertyProvider
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public interface IFBonusPropertyProvider extends IFPropertyProvider {

	@Override
	public BasicProperty parse(PropertyDataMap data);
	
}
