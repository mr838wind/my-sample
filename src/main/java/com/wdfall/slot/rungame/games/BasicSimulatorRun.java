package com.wdfall.slot.rungame.games;

import org.springframework.stereotype.Component;

import com.wdfall.slot.business.simulator.loader.provider.GamePropertyProvider;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.result.AccEmulateResult;
import com.wdfall.slot.business.simulator.simulatortask.BasicSimulatorTask;
import com.wdfall.slot.model.vo.SimulatorInputVO;
import com.wdfall.slot.model.vo.SimulatorResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
import com.wdfall.slot.rungame.RunGameConstants.RunGameType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BasicSimulatorRun extends AbstractRun<GameProperty,AccEmulateResult,BasicSimulatorTask, SimulatorInputVO, SimulatorResultVO> {

	@Override
	protected void initStageAndType() {
		currentStage = RunGameStage.BASIC;
		currentType = RunGameType.SIMULATOR;
	}
	
	@Override
	protected void parseProperty() {
		GamePropertyProvider propertyProvider = new GamePropertyProvider();
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
		
		log.info(" >>> resultVO.getSeq = {}", resultVO.getSeq());
	}
	
}
