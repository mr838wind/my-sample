package test.wdfall.slot.simulator.shorttime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.display.Snapshot;
import com.wdfall.slot.business.simulator.finder.WinningFinder;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;

import lombok.extern.slf4j.Slf4j;
/**
 * test: finder
 * @author chhan
 *
 */
@Slf4j
public class Test08WinningFinder extends Test00Basic {
	
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testWinning5LineMatch(){
		
		List<EmulatorReel> reels = makeReelList(new String[][] {
			new String[] {"L1", "L2", "L3"},
			new String[] {"L1", "L2", "L3"},
			new String[] {"L1", "L2", "L3"},
			new String[] {"L1", "L2", "L3"},
			new String[] {"L1", "L2", "L3"}
		});
		
		List<List<EmulatorWinningPatternResult>> results = findWin(reels);
		
		assertThat( results.size(), equalTo(3) );
		assertHitResult(results.get(0).get(0), "L1", 5);
		assertHitResult(results.get(1).get(0), "L2", 5);
		assertHitResult(results.get(2).get(0), "L3", 5);
	}

	@Test
	public void testWild_WWWWK1(){
		
		List<EmulatorReel> reels = makeReelList(new String[][] {
			new String[] {WILD, "L2", "L3"},
			new String[] {WILD, "L1", "L2"},
			new String[] {WILD, "L2", "L3"},
			new String[] {WILD, "L2", "L3"},
			new String[] {"L1", "L2", "L3"}
		});
		
		List<List<EmulatorWinningPatternResult>> results = findWin(reels);
		
		assertThat( results.size(), equalTo(1) );
		assertHitResult(results.get(0).get(0), WILD, 4);
		assertHitResult(results.get(0).get(1), "L1", 5);
	}
	
	@Test
	public void testWild_WWWWK2(){
		
		List<EmulatorReel> reels = makeReelList(new String[][] {
			new String[] {WILD, "L2", "L3"},
			new String[] {WILD, "L1", "L2"},
			new String[] {WILD, "L2", "L3"},
			new String[] {WILD, "L2", "L3"},
			new String[] {"H1", "L2", "L3"}
		});
		
		List<List<EmulatorWinningPatternResult>> results = findWin(reels);
		
		assertThat( results.size(), equalTo(1) );
		assertHitResult(results.get(0).get(0), WILD, 4);
		assertHitResult(results.get(0).get(1), "H1", 5);
	}
	
	@Test
	public void testScatterFail(){
		
		List<EmulatorReel> reels = makeReelList(new String[][] {
			new String[] {SCATTER, "L2", "L3"},
			new String[] {"L1", "L1", "L2"},
			new String[] {"L2", SCATTER, "L3"},
			new String[] {"L2", "L2", "L1"},
			new String[] {"H1", "L2", "L3"}
		});
		
		List<List<EmulatorWinningPatternResult>> results = findWin(reels);
		
		assertThat( results.size(), equalTo(0) );
	}


	@Test
	public void testScatterWin(){
		List<EmulatorReel> reels = makeReelList(new String[][] {
			new String[] {SCATTER, "L2", "L3"},
			new String[] {"L1", "L1", "L2"},
			new String[] {"L2", SCATTER, "L3"},
			new String[] {"L2", "L2", SCATTER},
			new String[] {"H1", "L2", "L3"}
		});
		
		List<List<EmulatorWinningPatternResult>> results = findWin(reels);
		
		assertThat( results.size(), equalTo(1) );
		assertHitResult(results.get(0).get(0), SCATTER, 3);
	}
	
	
	/**
	 * 특수상황: WILD후에 바로 SCATTER이 옴. 
	 * 이것때문에 payout이 0.02차이남
	 */
	@Test
	public void testWildWithScatter(){
		
		List<EmulatorReel> reels = makeReelList(new String[][] {
			new String[] {WILD, "L2", "L3"},
			new String[] {SCATTER, "L1", "L2"},
			new String[] {SCATTER, "L1", "L3"},
			new String[] {"L2", "L2", "L1"},
			new String[] {"H1", "L2", "L3"}
		});
		
		List<List<EmulatorWinningPatternResult>> results = findWin(reels);
		assertThat( results.size(), equalTo(0) );
	}
	
	
	protected List<List<EmulatorWinningPatternResult>> findWin(List<EmulatorReel> reels) {
		Snapshot spinResult = new Snapshot();
		spinResult.setPlayReels(reels);
		
		WinningFinder finder = new WinningFinder();
		finder.setProperty(property);
		
		List<List<EmulatorWinningPatternResult>> matchedResults = finder.findWin( spinResult  );
		return matchedResults;
	}
	
	private void assertHitResult(EmulatorWinningPatternResult result, String hitSymbol, int hitCount) {
		assertThat( result.getHittedSymbolId(), equalTo(hitSymbol) );
		assertThat( result.getHittedCount(), equalTo(hitCount) );
	}
	
	
	
}
