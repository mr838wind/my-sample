package com.wdfall.slot.rungame.games;

import org.springframework.stereotype.Component;

import com.wdfall.slot.business.simulator.loader.provider.Stage01GamePropertyProvider;
import com.wdfall.slot.business.simulator.property.Stage01GameProperty;
import com.wdfall.slot.business.simulator.result.AccEmulateResult;
import com.wdfall.slot.business.simulator.simulatortask.Stage01SimulatorTask;
import com.wdfall.slot.model.vo.SimulatorInputVO;
import com.wdfall.slot.model.vo.SimulatorResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
import com.wdfall.slot.rungame.RunGameConstants.RunGameType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Stage01SimulatorRun extends AbstractRun<Stage01GameProperty, AccEmulateResult, Stage01SimulatorTask,SimulatorInputVO, SimulatorResultVO> {

	@Override
	protected void initStageAndType() {
		currentStage = RunGameStage.STAGE01;
		currentType = RunGameType.SIMULATOR;
	}
	
	@Override
	protected void parseProperty() {
		Stage01GamePropertyProvider propertyProvider = new Stage01GamePropertyProvider();
		property = propertyProvider.parse(loadedData);
	}

	@Override
	protected void insertResultToDb() {
		AccEmulateResult result = basicSimulator.getTotalResult();
		
		SimulatorResultVO resultVO = new SimulatorResultVO();
		resultVO.setInputSeq(inputVO.getSeq());
		resultVO.setStage(currentStage);
		resultVO.setUsername(username);
		resultVO.setAccEmulateResult(result);
		
		slotService.insertResult(resultVO);
		
		log.debug(" >>> resultVO.getSeq = {}", resultVO.getSeq());
	}

}
