package test.wdfall.slot.simulator.longtime.payout;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.result.AccEmulateResult;
import com.wdfall.slot.business.simulator.simulatortask.BasicSimulatorTask;
import com.wdfall.slot.business.simulatorframework.BasicSimulator;

import lombok.extern.slf4j.Slf4j;
import test.wdfall.slot.simulator.basic.utils.GamePropertyUtils;

/*
 *	run result = 
 */
@Slf4j
public class Test25CheckScatter {

	private static final String SCATTER = "SS";
	GameProperty property;
	
	@Before
	public void setUp() {
		property = GamePropertyUtils.makeGamePropertiesCheckScatterExcel();
	}
	
	@Test
	public void testProperty() {
		assertThat(property.getGroundScale().length, equalTo(3));
		assertThat(property.getPayoutTable().get(SCATTER).getPayout(3), equalTo(200));
		assertThat(property.getPayoutTable().get("N1").getPayout(3), equalTo(0));
		log.debug("property={}", property); 
	}
	
	
	@Test
	public void testSpin() throws Exception{
		
		double EXCEL_EXPECTED_PAYOUT = 0.181262797;
		
		property.setPlayThreadCount(1);
		property.setPlayThreadPoolSize(1);
		property.setPlayGameCount(10*1000*1000);
		
		BasicSimulator<GameProperty, AccEmulateResult, BasicSimulatorTask> basicSimulator = new BasicSimulator<>(property, AccEmulateResult.class, BasicSimulatorTask.class);
		basicSimulator.runTest();
		AccEmulateResult totalResult = basicSimulator.getTotalResult();
		
		
		double difference = Math.abs(EXCEL_EXPECTED_PAYOUT - totalResult.getPayoutRate());
		log.debug(">>> difference={}", String.format("%f", difference)); 
		assertThat(difference, lessThan(0.01));
	}
	
}
