package com.wdfall.slot.rungame.games;

import org.springframework.stereotype.Component;

import com.wdfall.slot.business.bonus.Stage01BonusProperty;
import com.wdfall.slot.business.bonus.Stage01BonusTask;
import com.wdfall.slot.business.simulator.loader.provider.Stage01BonusPropertyProvider;
import com.wdfall.slot.business.simulatorframework.BasicAccResult;
import com.wdfall.slot.business.simulatorframework.Stage01BonusAccResult;
import com.wdfall.slot.model.vo.BonusInputVO;
import com.wdfall.slot.model.vo.BonusResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
import com.wdfall.slot.rungame.RunGameConstants.RunGameType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Stage01BonusRun extends AbstractRun<Stage01BonusProperty, Stage01BonusAccResult, Stage01BonusTask, BonusInputVO, BonusResultVO> {

	@Override
	protected void initStageAndType() {
		currentStage = RunGameStage.STAGE01;
		currentType = RunGameType.BONUS;
	}
	
	@Override
	protected void parseProperty() {
		Stage01BonusPropertyProvider propertyProvider = new Stage01BonusPropertyProvider();
		property = propertyProvider.parse(loadedData);
	}
	
	@Override
	protected void insertResultToDb() {
		BasicAccResult result = basicSimulator.getTotalResult();
		
		BonusResultVO resultVO = new BonusResultVO();
		resultVO.setInputSeq(inputVO.getSeq());
		resultVO.setStage(currentStage);
		resultVO.setUsername(username);
		resultVO.setBasicAccResult(result);
		
		slotService.insertResult(resultVO);
		
		log.info(" >>> resultVO.getSeq = {}", resultVO.getSeq());
	}
	

}