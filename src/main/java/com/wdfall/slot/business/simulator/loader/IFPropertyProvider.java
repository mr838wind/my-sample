package com.wdfall.slot.business.simulator.loader;

import com.wdfall.slot.business.simulatorframework.BasicProperty;
/**
 * IFPropertyProvider
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public interface IFPropertyProvider {

	public BasicProperty parse(PropertyDataMap data);
	
}
