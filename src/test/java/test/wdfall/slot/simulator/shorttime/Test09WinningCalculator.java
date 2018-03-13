package test.wdfall.slot.simulator.shorttime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import com.wdfall.slot.business.simulator.calculator.WinningCalculator;
import com.wdfall.slot.business.simulator.finder.WinningFinder.WinningResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.PatternType;

import lombok.extern.slf4j.Slf4j;
/**
 * test: calculator
 * @author chhan
 *
 */
@Slf4j
public class Test09WinningCalculator extends Test00Basic {
	
	private static final int PAY_L1_5 = 100;
	private static final int PAY_WILD_4 = 500;
	private static final int PAY_H1_5 = 1000;
	private static final int PAY_SCATTER_3 = 10;

	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testLine() {
		List<EmulatorWinningPatternResult> matchedResults = new ArrayList<>();
		matchedResults.add(makeOnePatternResult("L1", PatternType.LINE,
				new String[] { "L1", "L1", "L1", "L1", "L1" }));

		WinningResult winResult = calculatePayoutOnePattern(matchedResults);

		assertThat(winResult, is(notNullValue()));
		assertThat(winResult.getSymbolId(), equalTo("L1"));
		assertThat(winResult.getPayout(), equalTo(PAY_L1_5));
	}

	
	@Test
	public void testWild_WWWWK1(){
		List<EmulatorWinningPatternResult> matchedResults = new ArrayList<>();
		matchedResults.add(makeOnePatternResult(WILD, PatternType.LINE,
				new String[] { WILD, WILD, WILD, WILD}));
		matchedResults.add(makeOnePatternResult("L1", PatternType.LINE,
				new String[] { WILD, WILD, WILD, WILD, "L1" }));

		WinningResult winResult = calculatePayoutOnePattern(matchedResults);

		assertThat(winResult, is(notNullValue()));
		assertThat(PAY_WILD_4, greaterThan(PAY_L1_5));
		assertThat(winResult.getPayout(), equalTo(Math.max(PAY_WILD_4, PAY_L1_5)));
	}
	
	@Test
	public void testWild_WWWWK2(){
		List<EmulatorWinningPatternResult> matchedResults = new ArrayList<>();
		matchedResults.add(makeOnePatternResult(WILD, PatternType.LINE,
				new String[] { WILD, WILD, WILD, WILD}));
		matchedResults.add(makeOnePatternResult("H1", PatternType.LINE,
				new String[] { WILD, WILD, WILD, WILD, "H1" }));

		WinningResult winResult = calculatePayoutOnePattern(matchedResults);

		assertThat(winResult, is(notNullValue()));
		assertThat(PAY_WILD_4, lessThan(PAY_H1_5));
		assertThat(winResult.getPayout(), equalTo(Math.max(PAY_WILD_4, PAY_H1_5)));
	}
	
	@Test
	public void testScatterWin(){
		
		int totalBet = property.getTotalBet();
		
		
		List<EmulatorWinningPatternResult> matchedResults = new ArrayList<>();
		matchedResults.add(makeOnePatternResult(SCATTER, PatternType.SCATTER,
				new String[] { SCATTER, SCATTER, SCATTER}));

		WinningResult winResult = calculatePayoutOnePattern(matchedResults);

		assertThat(winResult, is(notNullValue()));
		assertThat(winResult.getPayout(), equalTo(PAY_SCATTER_3 * totalBet));
	}
	
	private EmulatorWinningPatternResult makeOnePatternResult(String hittedSymbolId, PatternType patternType, String[] hitSymbolCodes ) {
		EmulatorWinningPatternResult result1 = new EmulatorWinningPatternResult();
		result1.setHittedSymbolId(hittedSymbolId);
		result1.setHittedSymbols(makeSymbolList(hitSymbolCodes));
		result1.setPattern(patternType);
		return result1;
	}
	
	
	protected WinningResult calculatePayoutOnePattern(List<EmulatorWinningPatternResult> matchedResult) {
		List<List<EmulatorWinningPatternResult>> matchedResultList = new ArrayList<>();
		matchedResultList.add(matchedResult);
		
		WinningCalculator calc = new WinningCalculator();
		calc.setProperty(property);
		List<WinningResult> resultList = calc.calculatePayout(matchedResultList );
		
		WinningResult result = null;
		if(!CollectionUtils.isEmpty(resultList)) {
			result = resultList.get(0);
		}
		return result;
	}
	
	
}
