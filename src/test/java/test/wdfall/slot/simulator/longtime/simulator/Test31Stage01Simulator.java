package test.wdfall.slot.simulator.longtime.simulator;

import org.junit.Test;

import com.wdfall.slot.business.simulator.property.Stage01GameProperty;
import com.wdfall.slot.business.simulator.result.AccEmulateResult;
import com.wdfall.slot.business.simulator.simulatortask.Stage01SimulatorTask;
import com.wdfall.slot.business.simulatorframework.BasicSimulator;

import lombok.extern.slf4j.Slf4j;
import test.wdfall.slot.simulator.basic.utils.Stage01GamePropertyUtils;

/**
 * stage 1
 * @author chhan
 *
 */
@Slf4j
public class Test31Stage01Simulator {

	@Test
	public void testSpin() throws Exception{
		
		double EXCEL_EXPECTED_PAYOUT = 2.2607;
		
		Stage01GameProperty prop = Stage01GamePropertyUtils.makeGamePropertiesStage1();
		prop.setPlayThreadCount(1);
		prop.setPlayThreadPoolSize(1);
		prop.setPlayGameCount(1000*1000);
		
		BasicSimulator<Stage01GameProperty, AccEmulateResult, Stage01SimulatorTask> basicSimulator = new BasicSimulator<>(prop, AccEmulateResult.class, Stage01SimulatorTask.class);
		basicSimulator.runTest();
		AccEmulateResult totalResult = basicSimulator.getTotalResult();
		
		
		double difference = Math.abs(EXCEL_EXPECTED_PAYOUT - totalResult.getPayoutRate());
		log.debug("difference={}", difference);
		
		//assertThat(difference, lessThan(0.01));
	}
	
}
