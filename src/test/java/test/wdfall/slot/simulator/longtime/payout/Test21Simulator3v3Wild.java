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
 *	run result = 1.4134
 *	expected = 	1.414521164
 */
@Slf4j
public class Test21Simulator3v3Wild {

	@Test
	public void testSpin() throws Exception{
		
		double EXCEL_EXPECTED_PAYOUT = 1.414521164;
		
		//GameProperties prop = TestUtils.makeGameProperties3v3();
		GameProperty prop = GamePropertyUtils.makeGameProperties3v3Excel();
		prop.setPlayThreadCount(1);
		prop.setPlayThreadPoolSize(1);
		prop.setPlayGameCount(10*1000*1000);
		
		BasicSimulator<GameProperty, AccEmulateResult, BasicSimulatorTask> basicSimulator = new BasicSimulator<>(prop, AccEmulateResult.class, BasicSimulatorTask.class);
		basicSimulator.runTest();
		AccEmulateResult totalResult = basicSimulator.getTotalResult();
		
		
		double difference = Math.abs(EXCEL_EXPECTED_PAYOUT - totalResult.getPayoutRate());
		log.debug(">>> difference={}", String.format("%f", difference)); 
		assertThat(difference, lessThan(0.01));
	}
	
}
