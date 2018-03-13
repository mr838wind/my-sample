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
 *	run result = 
 */
@Slf4j
public class Test24Simulator3v5Scatter {

	/*
	 * Line Pays	0.63460
	 * Scatter		0.06976 ---> 0.0831
	 * total:		0.70436 ---> 0.71766
	 * ---------------------------------------
	 * excel에서는 reel을 구성하고 돌리는 방식으로 생각했기때문에 reel1에서 scatter이 2개 동시에 나올수 없음.
	 * 우리 실현방식은 reel1의 3개 socket을 다 random으로 선택하기에 2개 동시에 나올수도 가능.
	 * 그래서 scatter(0.0831)이 예상(0.06976)보다 크게 나옴.
	 * ---------------------------------------
	 */
	@Test
	public void testSpin() throws Exception{
		
		double EXCEL_EXPECTED_PAYOUT = 0.71766;
		
		GameProperty prop = GamePropertyUtils.makeGameProperties3v5ScatterExcel();
		
		BasicSimulator<GameProperty, AccEmulateResult, BasicSimulatorTask> basicSimulator = new BasicSimulator<>(prop, AccEmulateResult.class, BasicSimulatorTask.class);
		basicSimulator.runTest();
		AccEmulateResult totalResult = basicSimulator.getTotalResult();
		
		
		double difference = Math.abs(EXCEL_EXPECTED_PAYOUT - totalResult.getPayoutRate());
		assertThat(difference, lessThan(0.01));
	}
	
}
