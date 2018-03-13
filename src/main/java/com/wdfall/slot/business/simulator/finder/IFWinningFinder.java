package com.wdfall.slot.business.simulator.finder;

import java.util.List;

import com.wdfall.slot.business.simulator.display.Snapshot;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;

/**
 * slot에서 win pattern 찾는 작용
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public interface IFWinningFinder {

	public List<List<EmulatorWinningPatternResult>> findWin(Snapshot spinResult);
	
	
}
