package com.wdfall.slot.business.bonus;

import com.wdfall.slot.business.simulatorframework.AbstractBasicTask;
import com.wdfall.slot.business.simulatorframework.Stage01BonusAccResult;
import com.wdfall.slot.business.simulatorframework.Stage01BonusResult;
/**
 * Stage01BonusTask
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class Stage01BonusTask extends AbstractBasicTask<Stage01BonusProperty, Stage01BonusAccResult> {

	@Override
	public Stage01BonusAccResult call() throws Exception {
		
		Stage01BonusGame bonusGame = new Stage01BonusGame();
		bonusGame.setBonusProperty(property);
		bonusGame.init();
		
		long playGameCount = property.getPlayGameCount();
		
		Stage01BonusAccResult accResult = new Stage01BonusAccResult();
		for(int i=0; i<playGameCount; i++) {
			Stage01BonusResult result = bonusGame.run();
			accResult.append(result);
			//
			if(progress != null) {
				progress.increaseDoneTask();
			}
		}
		
		accResult.printTotalResult();
		
		return accResult;
	}
	
}
