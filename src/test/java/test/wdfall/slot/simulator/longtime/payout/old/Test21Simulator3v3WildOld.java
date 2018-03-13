package test.wdfall.slot.simulator.longtime.payout.old;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.wdfall.slot.business.simulator.EmulatorSlot;
import com.wdfall.slot.business.simulator.calculator.WinningCalculator;
import com.wdfall.slot.business.simulator.display.DisplayCreator;
import com.wdfall.slot.business.simulator.factories.EmulatorSymbolFactory;
import com.wdfall.slot.business.simulator.factories.ReelSymbolSelector;
import com.wdfall.slot.business.simulator.finder.WinningFinder;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.result.AccEmulateResult;
import com.wdfall.slot.business.simulator.result.EmulateResult;
import com.wdfall.slot.business.utils.CloneUtils;

import lombok.extern.slf4j.Slf4j;
import test.wdfall.slot.simulator.basic.utils.GamePropertyUtils;

/*
 *	run result = 1.4136
 *	expected = 	1.414521164
 *
 */
@Slf4j
public class Test21Simulator3v3WildOld {

	//3v3 wild
	public static final double EXCEL_EXPECTED_PAYOUT = 1.414521164;

	static int runningThreadCount = 0;
	static AccEmulateResult totalResult = new AccEmulateResult();
	
	@Test
	public void testSpin() throws InterruptedException{
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<< testSpin STARTED  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		long startTime = System.currentTimeMillis();
		
		//GameProperties prop = TestUtils.makeGameProperties3v3();
		GameProperty prop = GamePropertyUtils.makeGameProperties3v3Excel();
		
		for(int i=0; i < prop.getPlayThreadCount(); i++){
			SimulatorRunner task = new SimulatorRunner();
			task.setProperty(prop);
			new Thread(task).start();
			Thread.sleep(100);
		}		
		
		while( runningThreadCount > 0 ){
			Thread.sleep(100);
		}
		
		log.debug("\r\n\r\n");
		log.debug("<<<<<<<<<<<<< totalResult  >>>>>>>>>>>>>");
		totalResult.printTotalResult();
		
		long endTime = System.currentTimeMillis();
		log.debug(">>> time: {}s",(endTime - startTime) / 1000.0);
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<< testSpin Terminated  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		double difference = Math.abs(EXCEL_EXPECTED_PAYOUT - totalResult.getPayoutRate());
		assertThat(difference, lessThan(0.01));
		
	}
	
	public class SimulatorRunner implements Runnable {
		
		protected GameProperty property;
		
		public void setProperty(GameProperty property) {
			this.property = CloneUtils.deepCopyByLib(property);
		}
		
		public void run() {

			synchronized (totalResult) {
				runningThreadCount++;	
			}
			
			EmulatorSymbolFactory symbolFactory = new EmulatorSymbolFactory();
			symbolFactory.setProperty(property);
			
			ReelSymbolSelector symbolSelector = new ReelSymbolSelector();
			symbolSelector.setSymbolFactory(symbolFactory);
			symbolSelector.setProperty(property);
			
			DisplayCreator displayCreator = new DisplayCreator();
			displayCreator.setSymbolSelector(symbolSelector);
			displayCreator.setGameProperties(property);
			
			WinningFinder winningFinder = new WinningFinder();
			winningFinder.setProperty(property);
			
			WinningCalculator winningCalculator = new WinningCalculator();
			winningCalculator.setProperty(property);
			
			AccEmulateResult accEmulateResult = new AccEmulateResult();
			
			for(int i=0 ; i< property.getPlayGameCount(); i++){
				EmulatorSlot aSlot = new EmulatorSlot();
				aSlot.setGameProperties(property);
				aSlot.setDisplayCreator(displayCreator);
				aSlot.setWinningCalculator(winningCalculator);
				aSlot.setWinningFinder(winningFinder);
				
				EmulateResult result = aSlot.spin();
				
				accEmulateResult.append( result );				
			}
			
			accEmulateResult.printTotalResult();
			
			totalResult.append(accEmulateResult);
			
			synchronized (totalResult) {
				runningThreadCount--;	
			}

		}
	}
	

}
