package test.wdfall.slot.simulator.shorttime;

import com.wdfall.slot.business.simulator.factories.EmulatorSymbolFactory;

import test.wdfall.slot.simulator.basic.utils.Stage01GamePropertyUtils;

public abstract class Test00BasicStage1 extends Test00Basic {

	@Override
	protected void setUp() {
		property = Stage01GamePropertyUtils.makeGamePropertiesStage1();
		symbolFactory = new EmulatorSymbolFactory();
		symbolFactory.setProperty(property);
	}
	
	
}
