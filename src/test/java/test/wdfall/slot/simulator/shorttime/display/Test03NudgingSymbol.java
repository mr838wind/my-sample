package test.wdfall.slot.simulator.shorttime.display;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.display.DisplayCreator;
import com.wdfall.slot.business.simulator.display.IFDisplayCreator;
import com.wdfall.slot.business.simulator.display.NudgingSymbolDisplayCreatorDecorator;
import com.wdfall.slot.business.simulator.display.Snapshot;

import lombok.extern.slf4j.Slf4j;
import test.wdfall.slot.simulator.shorttime.Test00BasicStage1;

@Slf4j
public class Test03NudgingSymbol extends Test00BasicStage1 {

	
	@Before
	public void setUp() {
		 super.setUp();
	}
	
	@Test
	public void testNudging() {
		EmulatorSymbol wildSymbol = symbolFactory.get(WILD);
		
		IFDisplayCreator dc = mock(DisplayCreator.class);
		
		//
		Snapshot snapshot = new Snapshot();
		snapshot.setPlayReels(makeReelList( new String[][] {
			new String[] {WILD,WILD,"L2"},
			new String[] {WILD,"L1",WILD},
			new String[] {"H1","L1","L2"},
			new String[] {"H1",WILD,WILD}, 
			new String[] {"H1","L1",WILD}
		}));
		doReturn(snapshot).when(dc).createReels();
		
		
		// wild symbol
		NudgingSymbolDisplayCreatorDecorator displayCreator = new NudgingSymbolDisplayCreatorDecorator(dc, wildSymbol, 3);
		Snapshot reels = displayCreator.createReels();
		List<EmulatorReel> playReels = reels.getPlayReels();
		
		log.debug("playReels={}", playReels);
		assertThat(getReelSymbolCodeList(playReels.get(0)), equalTo(Arrays.asList(WILD,WILD,WILD)));
		assertThat(getReelSymbolCodeList(playReels.get(1)), equalTo(Arrays.asList(WILD,WILD,"L1")));
		assertThat(getReelSymbolCodeList(playReels.get(2)), equalTo(Arrays.asList("H1","L1","L2")));
		assertThat(getReelSymbolCodeList(playReels.get(3)), equalTo(Arrays.asList(WILD,WILD,WILD)));
		assertThat(getReelSymbolCodeList(playReels.get(4)), equalTo(Arrays.asList("L1",WILD,WILD)));
		
	}
	
	
	
}
