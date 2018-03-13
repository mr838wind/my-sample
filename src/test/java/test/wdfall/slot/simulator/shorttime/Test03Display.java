package test.wdfall.slot.simulator.shorttime;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.display.DisplayCreator;
import com.wdfall.slot.business.simulator.factories.ReelSymbolSelector;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test03Display extends Test00Basic {

	@Before
	public void setUp(){
		super.setUp();
	}
	
	@Test
	public void testDisplay() {
		
		ReelSymbolSelector symbolSelector = new ReelSymbolSelector();
		symbolSelector.setSymbolFactory(symbolFactory);
		symbolSelector.setProperty(property);
		
		DisplayCreator displayCreator = new DisplayCreator();
		displayCreator.setSymbolSelector(symbolSelector);
		displayCreator.setGameProperties(property);
		
		List<EmulatorReel> reels = displayCreator.createReels().getPlayReels();
		log.debug("reels = {}", reels);
		assertEquals(5, reels.size());
	}
	
	
}
