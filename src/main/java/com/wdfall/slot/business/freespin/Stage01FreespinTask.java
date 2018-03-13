package com.wdfall.slot.business.freespin;

import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.EmulatorSlot;
import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.calculator.Wild2xWinningCalculator;
import com.wdfall.slot.business.simulator.calculator.WinningCalculator;
import com.wdfall.slot.business.simulator.display.DisplayCreator;
import com.wdfall.slot.business.simulator.display.IFDisplayCreator;
import com.wdfall.slot.business.simulator.display.NudgingSymbolDisplayCreatorDecorator;
import com.wdfall.slot.business.simulator.display.Snapshot;
import com.wdfall.slot.business.simulator.factories.EmulatorSymbolFactory;
import com.wdfall.slot.business.simulator.factories.ReelSymbolSelector;
import com.wdfall.slot.business.simulator.finder.WinningFinder;
import com.wdfall.slot.business.simulator.property.Stage01GameProperty;
import com.wdfall.slot.business.simulator.result.EmulateResult;
import com.wdfall.slot.business.simulator.result.FreespinAccEmulateResult;
import com.wdfall.slot.business.simulatorframework.AbstractBasicTask;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
/**
 * Stage01FreespinTask
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class Stage01FreespinTask extends AbstractBasicTask<Stage01GameProperty, FreespinAccEmulateResult> {

	
	private IFDisplayCreator displayCreator;
	private WinningCalculator winningCalculator;
	private WinningFinder winningFinder;
	private FreespinAccEmulateResult accEmulateResult;
	
	
	@Override
	public FreespinAccEmulateResult call() throws Exception {
		/*
		 * Stage1 Free spin (God Of Mermaid):
		 * --------------------------------
		 * >> wild -> 2x: line payout * 2. 
 		 * >> scatter +1 freespin: 
		 * --------------------------------
		 * 
		 * 0. game property: wild --> wild2x
		 * 1. standing 선택 효과: ReelSymbolSelector
		 * 2. nudging 효과:  NudgingSymbolDisplayCreatorDecorator
		 * 3. wild2x: line payout * 2
		 * 4. freespin scatter 효과(freespin +1):  
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
		displayCreator = new NudgingSymbolDisplayCreatorDecorator(dc, wildSymbol, 3);
		
		winningFinder = new WinningFinder();
		winningFinder.setProperty(property);
		
		winningCalculator = new Wild2xWinningCalculator();
		winningCalculator.setProperty(property);
		
		accEmulateResult = new FreespinAccEmulateResult(); 
		
		FreespinScatterData fsData = new FreespinScatterData();
		
		for(int i=0 ; i< property.getPlayGameCount(); i++){
			EmulatorSlot aSlot = new EmulatorSlot();
			aSlot.setGameProperties(property);
			aSlot.setDisplayCreator(displayCreator);
			aSlot.setWinningFinder(winningFinder);
			aSlot.setWinningCalculator(winningCalculator);
			
			EmulateResult result = aSlot.spin( );
			
			accEmulateResult.append( result );
			
			// freespin scatter 처리 
			long currentFssCount = countFreespinScatter(aSlot.getSpinResult());
			fsData.appendCurrentCount(currentFssCount);
			//log.debug("currentFssCount : {}", currentFssCount);
			
			handleFreespinScatterRun(fsData);
			//
			if(progress != null) {
				progress.increaseDoneTask();
			}
		}
		
		accEmulateResult.printTotalResult();
		
		return accEmulateResult;
	}
	
	private void handleFreespinScatterRun(FreespinScatterData fsData) {
		while(true) {
			if(fsData.getFreespinScatterCountCurrent() <= 0) {
				break;
			}
			
			fsData.countDown();
			
			//...
			EmulatorSlot aSlot = new EmulatorSlot();
			aSlot.setGameProperties(property);
			aSlot.setDisplayCreator(displayCreator);
			aSlot.setWinningFinder(winningFinder);
			aSlot.setWinningCalculator(winningCalculator);
			
			EmulateResult result = aSlot.spin( );
			
			accEmulateResult.appendFreespin( result );
			
			// freespin scatter 처리 
			long currentFssCount = countFreespinScatter(aSlot.getSpinResult());
			fsData.appendCurrentCount(currentFssCount);
			//log.debug("currentFssCount : {}", currentFssCount);
		}
	}

	private long countFreespinScatter(Snapshot spinResult) {
		List<EmulatorReel> playReels = spinResult.getPlayReels();
		long count = 0;
		for(EmulatorReel reel : playReels) {
			for(int i=0; i<reel.size(); i++) {
				EmulatorSymbol symbol = reel.getSymbol(i);
				if(symbol.isScatterFreespin()) {
					count++;
				}
			}
		}
		
		return count;
	} 
	
	@Data
	private static class FreespinScatterData {
		private long freespinScatterCountCurrent = 0; // current
		private long freespinScatterCountTotal = 0; //총 
		
		public void appendCurrentCount(long currentFssCount) {
			this.freespinScatterCountCurrent += currentFssCount;
			this.freespinScatterCountTotal += currentFssCount;
		}
		
		public void countDown() {
			this.freespinScatterCountCurrent--;
		}
	}

	
}
