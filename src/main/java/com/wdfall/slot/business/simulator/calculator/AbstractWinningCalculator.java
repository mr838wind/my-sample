package com.wdfall.slot.business.simulator.calculator;

import java.util.List;

import com.wdfall.slot.business.simulator.display.Snapshot;
import com.wdfall.slot.business.simulator.finder.WinningFinder.WinningResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;
/**
 * AbstractWinningCalculator
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public abstract class AbstractWinningCalculator {

	protected Snapshot spinResult;
	
	public void setSpinResult(Snapshot spinResult) {
		this.spinResult = spinResult;
	}
	
	public abstract List<WinningResult> calculatePayout(List<List<EmulatorWinningPatternResult>> matchedResults);
	
	
}
