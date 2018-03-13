package test.wdfall.slot.simulator.shorttime;

import org.junit.Before;
import org.junit.Test;

import com.wdfall.slot.business.simulator.EmulatorSlot;
import com.wdfall.slot.business.simulator.calculator.WinningCalculator;
import com.wdfall.slot.business.simulator.display.DisplayCreator;
import com.wdfall.slot.business.simulator.factories.EmulatorSymbolFactory;
import com.wdfall.slot.business.simulator.factories.ReelSymbolSelector;
import com.wdfall.slot.business.simulator.finder.WinningFinder;
import com.wdfall.slot.business.simulator.result.EmulateResult;

import lombok.extern.slf4j.Slf4j;
import test.wdfall.slot.simulator.basic.utils.GamePropertyUtils;

@Slf4j
public class Test10Emulator extends Test00Basic {

	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testSpin(){
		
		property = GamePropertyUtils.makeGamePropertiesForUnitTest();
		
		symbolFactory = new EmulatorSymbolFactory();
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
		
		EmulatorSlot aSlot = new EmulatorSlot();
		aSlot.setGameProperties(property);
		aSlot.setDisplayCreator(displayCreator);
		aSlot.setWinningCalculator(winningCalculator);
		aSlot.setWinningFinder(winningFinder);
		
		EmulateResult result = aSlot.spin();
		log.debug("payout = {}",result.getPayout());
	}
	
}
