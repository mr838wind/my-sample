package test.wdfall.slot.simulator.shorttime;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.factories.ReelSymbolSelector;
import com.wdfall.slot.business.simulator.property.EmulatorReelProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test02ReelMaker extends Test00Basic {

	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void makeReel(){
		
		ReelSymbolSelector symbolSelector = new  ReelSymbolSelector();
		symbolSelector.setSymbolFactory(symbolFactory);
		symbolSelector.setProperty(property);
		
		EmulatorReelProperty reelProperty = property.getReelProperties()[0];
		
		List<EmulatorSymbol> symbols =  symbolSelector.select(reelProperty, 3);
		assertEquals( 3, symbols.size() );
		log.debug("Selected Symbols : {}", symbols);
		
		symbols =  symbolSelector.select(reelProperty, 2);
		assertEquals( 2, symbols.size() );
		log.debug("Selected Symbols : {}", symbols);
	} 
	
	
}
