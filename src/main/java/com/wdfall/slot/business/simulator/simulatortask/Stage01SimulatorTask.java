package com.wdfall.slot.business.simulator.simulatortask;

import com.wdfall.slot.business.simulator.EmulatorSlot;
import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.calculator.LeftRightBoxWinningCalculator;
import com.wdfall.slot.business.simulator.display.DisplayCreator;
import com.wdfall.slot.business.simulator.display.NudgingSymbolDisplayCreatorDecorator;
import com.wdfall.slot.business.simulator.factories.EmulatorSymbolFactory;
import com.wdfall.slot.business.simulator.factories.RandomOneResultGenerator;
import com.wdfall.slot.business.simulator.factories.ReelSymbolSelector;
import com.wdfall.slot.business.simulator.finder.WinningFinder;
import com.wdfall.slot.business.simulator.property.Stage01GameProperty;
import com.wdfall.slot.business.simulator.result.AccEmulateResult;
import com.wdfall.slot.business.simulator.result.EmulateResult;
import com.wdfall.slot.business.simulatorframework.AbstractBasicTask;
/**
 * Stage01SimulatorTask
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class Stage01SimulatorTask extends AbstractBasicTask<Stage01GameProperty, AccEmulateResult> {

	@Override
	public AccEmulateResult call() throws Exception {
		/*
		 * Stage1 (God Of Mermaid):
		 * 
		 * 0. game property
		 * 1. standing 선택 효과: ReelSymbolSelector
		 * 2. nudging 효과:  NudgingSymbolDisplayCreatorDecorator
		 * 3. 좌우측 효과: LeftRightBoxWinningCalculator 
		 * 
		 */
		EmulatorSymbolFactory symbolFactory = new EmulatorSymbolFactory();
		symbolFactory.setProperty(property);
		
		ReelSymbolSelector symbolSelector = new ReelSymbolSelector();
		symbolSelector.setSymbolFactory(symbolFactory);
		symbolSelector.setProperty(property);
		
		DisplayCreator dc = new DisplayCreator();
		dc.setSymbolSelector(symbolSelector);
		dc.setGameProperties(property);
		
		// nudging : wild symbol
		String wildSymbolCode = property.getWildSymbolId();
		EmulatorSymbol wildSymbol = symbolFactory.get(wildSymbolCode);
		NudgingSymbolDisplayCreatorDecorator displayCreator = new NudgingSymbolDisplayCreatorDecorator(dc, wildSymbol, 3);
		
		
		WinningFinder winningFinder = new WinningFinder();
		winningFinder.setProperty(property);
		
		LeftRightBoxWinningCalculator winningCalculator = new LeftRightBoxWinningCalculator();
		winningCalculator.setProperty(property);
		RandomOneResultGenerator leftGenerator = new RandomOneResultGenerator(((Stage01GameProperty)property).getLeftBoxProperty());
		RandomOneResultGenerator rightGenerator = new RandomOneResultGenerator(((Stage01GameProperty)property).getRightBoxProperty());
		winningCalculator.setLeftGenerator(leftGenerator);
		winningCalculator.setRightGenerator(rightGenerator);
		
		
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
