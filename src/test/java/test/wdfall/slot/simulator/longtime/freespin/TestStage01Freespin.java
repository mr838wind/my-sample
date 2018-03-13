package test.wdfall.slot.simulator.longtime.freespin;

import org.junit.Test;

import com.wdfall.slot.business.freespin.Stage01FreespinTask;
import com.wdfall.slot.business.simulator.property.Stage01GameProperty;
import com.wdfall.slot.business.simulator.result.AccEmulateResult;
import com.wdfall.slot.business.simulator.result.FreespinAccEmulateResult;
import com.wdfall.slot.business.simulatorframework.BasicSimulator;

import lombok.extern.slf4j.Slf4j;
import test.wdfall.slot.simulator.basic.utils.Stage01GamePropertyUtils;

@Slf4j
public class TestStage01Freespin {

	
	@Test
	public void testSpin() throws Exception{
		
		double EXCEL_EXPECTED_PAYOUT = 2.2607;
		
		Stage01GameProperty prop = Stage01GamePropertyUtils.makeGamePropertiesStage1Freespin();
		prop.setPlayThreadCount(1);
		prop.setPlayThreadPoolSize(2);
		prop.setPlayGameCount(10);
		
		BasicSimulator<Stage01GameProperty, FreespinAccEmulateResult, Stage01FreespinTask> basicSimulator = new BasicSimulator<>(prop, FreespinAccEmulateResult.class, Stage01FreespinTask.class);
		basicSimulator.runTest();
		AccEmulateResult totalResult = basicSimulator.getTotalResult();
		
		double difference = Math.abs(EXCEL_EXPECTED_PAYOUT - totalResult.getPayoutRate());
		log.debug("difference={}", difference);
		
		//assertThat(difference, lessThan(0.01));
		
	}
	
	
}
