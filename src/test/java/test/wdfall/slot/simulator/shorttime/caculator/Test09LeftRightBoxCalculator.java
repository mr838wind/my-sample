package test.wdfall.slot.simulator.shorttime.caculator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import com.wdfall.slot.business.simulator.calculator.LeftRightBoxWinningCalculator;
import com.wdfall.slot.business.simulator.factories.RandomOneResultGenerator;
import com.wdfall.slot.business.simulator.finder.WinningFinder.WinningResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.EmulatorWinningPatternResult;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.PatternType;

import lombok.extern.slf4j.Slf4j;
import test.wdfall.slot.simulator.shorttime.Test00BasicStage1;
/**
 * test: calculator
 * @author chhan
 *
 */
@Slf4j
public class Test09LeftRightBoxCalculator extends Test00BasicStage1 {
	
	private static final int PAY_L1_5 = 100;
	
	RandomOneResultGenerator leftGenerator;
	RandomOneResultGenerator rightGenerator;
	
	@Before
	public void setUp() {
		super.setUp();
		leftGenerator = mock(RandomOneResultGenerator.class);
		rightGenerator = mock(RandomOneResultGenerator.class);
	}

	@Test
	public void testLeftRightBox() {
		List<EmulatorWinningPatternResult> matchedResults = new ArrayList<>();
		matchedResults.add(makeOnePatternResult("L1", PatternType.LINE,
				new String[] { "L1", "L1", "L1", "L1", "L1" }));

		doReturn("L1").when(leftGenerator).getRandomOneResult();
		doReturn("2").when(rightGenerator).getRandomOneResult();
		
		WinningResult winResult = calculatePayoutOnePattern(matchedResults);

		assertThat(winResult, is(notNullValue()));
		assertThat(winResult.getSymbolId(), equalTo("L1"));
		assertThat(winResult.getPayout(), equalTo(PAY_L1_5 * 2));
		assertThat(winResult.getMultiple(), equalTo(2.0));
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
		
		LeftRightBoxWinningCalculator calc = new LeftRightBoxWinningCalculator();
		calc.setProperty(property);
		//
		calc.setLeftGenerator(leftGenerator);
		calc.setRightGenerator(rightGenerator);
		
		List<WinningResult> resultList = calc.calculatePayout(matchedResultList );
		
		WinningResult result = null;
		if(!CollectionUtils.isEmpty(resultList)) {
			result = resultList.get(0);
		}
		return result;
	}
	
	
}
