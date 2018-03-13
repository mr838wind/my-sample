package com.wdfall.slot.business.simulator;

import java.util.List;

import com.wdfall.slot.business.simulator.calculator.AbstractWinningCalculator;
import com.wdfall.slot.business.simulator.display.IFDisplayCreator;
import com.wdfall.slot.business.simulator.display.Snapshot;
import com.wdfall.slot.business.simulator.finder.IFWinningFinder;
import com.wdfall.slot.business.simulator.finder.WinningFinder.WinningResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.result.EmulateResult;

/**
 * slot game
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class EmulatorSlot {

	// input
	private GameProperty gameProperties;
	private IFDisplayCreator displayCreator;
	private IFWinningFinder winningFinder;
	private AbstractWinningCalculator winningCalculator;
	
	//
	private Snapshot spinResult;
	private EmulateResult result = new EmulateResult();
	
	public EmulatorSlot() {
		
	}
	
	public void setGameProperties(GameProperty gameProperties) {
		this.gameProperties = gameProperties;
	}

	public void setDisplayCreator(IFDisplayCreator displayCreator) {
		this.displayCreator = displayCreator;
	}
	
	public void setWinningFinder(IFWinningFinder winningFinder) {
		this.winningFinder = winningFinder;
	}

	public void setWinningCalculator(AbstractWinningCalculator winningCalculator) {
		this.winningCalculator = winningCalculator;
	}

	public EmulateResult spin() {		
		spinResult = displayCreator.createReels();

		List<List<EmulatorWinningPatternResult>> matchedResults = winningFinder.findWin( spinResult  );
		
		winningCalculator.setSpinResult(spinResult);
		List<WinningResult> results = winningCalculator.calculatePayout(matchedResults);
		
		result.setWinningPatterns( gameProperties.getWinningPatterns() );
		result.setWinningResults( results );
		return result;
	}

	
	public Snapshot getSpinResult() {
		return spinResult;
	}

	@Override
	public String toString() {
		return "EmulatorSlot [spinResult=" + spinResult + "]";
	}
	

}
