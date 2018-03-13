package test.wdfall.slot.simulator.longtime.payout;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.result.AccEmulateResult;
import com.wdfall.slot.business.simulator.simulatortask.BasicSimulatorTask;
import com.wdfall.slot.business.simulatorframework.BasicSimulator;

import lombok.extern.slf4j.Slf4j;
import test.wdfall.slot.simulator.basic.utils.GamePropertyUtils;

/*
 */
@Slf4j
public class Test22Simulator3v5wild {

	@Test
	public void testSpin() throws Exception{
		
		double EXCEL_EXPECTED_PAYOUT = 0.6346;
		
		GameProperty prop = GamePropertyUtils.makeGameProperties3v5Excel();
		
		BasicSimulator<GameProperty, AccEmulateResult, BasicSimulatorTask> basicSimulator = new BasicSimulator<>(prop, AccEmulateResult.class, BasicSimulatorTask.class);
		basicSimulator.runTest();
		AccEmulateResult totalResult = basicSimulator.getTotalResult();
		
		
		double difference = Math.abs(EXCEL_EXPECTED_PAYOUT - totalResult.getPayoutRate());
		assertThat(difference, lessThan(0.01));
	}
	
}
