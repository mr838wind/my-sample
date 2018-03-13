package test.wdfall.slot.simulator.shorttime;

import java.util.ArrayList;
import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.calculator.WinningCalculator;
import com.wdfall.slot.business.simulator.display.Snapshot;
import com.wdfall.slot.business.simulator.factories.EmulatorSymbolFactory;
import com.wdfall.slot.business.simulator.finder.WinningFinder;
import com.wdfall.slot.business.simulator.finder.WinningFinder.WinningResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.utils.SlotConstants;

import test.wdfall.slot.simulator.basic.utils.GamePropertyUtils;

public abstract class Test00Basic {

	public static final String SCATTER = SlotConstants.SCATTER; 	//"SS";
	public static final String SCATTER_1 = SlotConstants.SCATTER_1; // "SS.1";
	public static final String WILD = SlotConstants.WILD; 			//"WW";
	public static final String WILD_1 = SlotConstants.WILD_1; 		//"WW.1";
	
	protected GameProperty property;
	protected EmulatorSymbolFactory symbolFactory;
	
	protected void setUp() {
		property = GamePropertyUtils.makeGamePropertiesForUnitTest();
		symbolFactory = new EmulatorSymbolFactory();
		symbolFactory.setProperty(property);
	}
	
	protected List<EmulatorReel> makeReelList(String[][] reelParam) {
		List<EmulatorReel> reels = new ArrayList<>();
		for(String[] reelItem : reelParam) {
			reels.add(makeReel(reelItem));
		}
		return reels;
	}

	protected EmulatorReel makeReel(String[] symbolIds) {
		EmulatorReel reel0 = new EmulatorReel();
		for(String symbolId : symbolIds) {
			reel0.add(makeSymbol(symbolId));
		}
		return reel0;
	}

	protected EmulatorSymbol makeSymbol(String symbolId) {
		return symbolFactory.get(symbolId);
	}
	
	protected List<EmulatorSymbol> makeSymbolList(String... symbols) {
		List<EmulatorSymbol> result = new ArrayList<>();
		for(String item : symbols) {
			result.add(makeSymbol(item));
		}
		return result;
	}
	
	
	protected List<WinningResult> calcResult(List<EmulatorReel> reels) {
		Snapshot spinResult = new Snapshot();
		spinResult.setPlayReels(reels);
		
		WinningFinder finder = new WinningFinder();
		finder.setProperty(property);
		
		List<List<EmulatorWinningPatternResult>> matchedResults = finder.findWin( spinResult  );
		WinningCalculator calc = new WinningCalculator();
		calc.setProperty(property);
		List<WinningResult> results = calc.calculatePayout(matchedResults);
		return results;
	}
	
	
	protected List<String> getReelSymbolCodeList(EmulatorReel reel) {
		List<String> resultList = new ArrayList<>();
		for(int i=0; i<reel.size(); i++) {
			resultList.add(reel.getSymbol(i).getSymbolCode());
		}
		return resultList;
	}
	
	
}
