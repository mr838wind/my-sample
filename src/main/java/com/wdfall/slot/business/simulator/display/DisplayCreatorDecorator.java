package com.wdfall.slot.business.simulator.display;
/**
 * IFDisplayCreator decorator
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public abstract class DisplayCreatorDecorator implements IFDisplayCreator {

	protected IFDisplayCreator displayCreator;

	public DisplayCreatorDecorator(IFDisplayCreator displayCreator) {
		super();
		this.displayCreator = displayCreator;
	}
	
}
