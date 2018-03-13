package com.wdfall.slot.business.simulator.simulatortask;

import com.wdfall.slot.business.simulator.EmulatorSlot;
import com.wdfall.slot.business.simulator.calculator.WinningCalculator;
import com.wdfall.slot.business.simulator.display.DisplayCreator;
import com.wdfall.slot.business.simulator.factories.EmulatorSymbolFactory;
import com.wdfall.slot.business.simulator.factories.ReelSymbolSelector;
import com.wdfall.slot.business.simulator.finder.WinningFinder;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.result.AccEmulateResult;
import com.wdfall.slot.business.simulator.result.EmulateResult;
import com.wdfall.slot.business.simulatorframework.AbstractBasicTask;
/**
 * BasicSimulatorTask
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class BasicSimulatorTask extends AbstractBasicTask<GameProperty, AccEmulateResult> { 
	
	@Override
	public AccEmulateResult call() throws Exception {
		
		//GameProperties gproperty = (GameProperties)property;
		
		EmulatorSymbolFactory symbolFactory = new EmulatorSymbolFactory();
		symbolFactory.setProperty(property);
		
		ReelSymbolSelector symbolSelector = new ReelSymbolSelector();
		symbolSelector.setSymbolFactory(symbolFactory);
		symbolSelector.setProperty(property);
		
		DisplayCreator displayCreator = new DisplayCreator();
		displayCreator.setSymbolSelector(symbolSelector);
		displayCreator.setGameProperties(property);
		
		WinningFinder winningFinder = new WinningFinder();
		winningFinder.setProperty(property);
		
		WinningCalculator winningCalculator = new WinningCalculator();
		winningCalculator.setProperty(property);
		
		AccEmulateResult accEmulateResult = new AccEmulateResult(); 
		
		for(int i=0 ; i< property.getPlayGameCount(); i++){
			EmulatorSlot aSlot = new EmulatorSlot();
			aSlot.setGameProperties(property);
			aSlot.setDisplayCreator(displayCreator);
			aSlot.setWinningCalculator(winningCalculator);
			aSlot.setWinningFinder(winningFinder);
			
			EmulateResult result = aSlot.spin( );
			
			accEmulateResult.append( result );	
			//
			if(progress != null) {
				progress.increaseDoneTask();
			}
		}
		
		accEmulateResult.printTotalResult();
		
		return accEmulateResult;

	}
	
}