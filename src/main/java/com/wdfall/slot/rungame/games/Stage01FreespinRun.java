package com.wdfall.slot.rungame.games;

import org.springframework.stereotype.Component;

import com.wdfall.slot.business.freespin.Stage01FreespinTask;
import com.wdfall.slot.business.simulator.loader.provider.Stage01GamePropertyProvider;
import com.wdfall.slot.business.simulator.property.Stage01GameProperty;
import com.wdfall.slot.business.simulator.result.FreespinAccEmulateResult;
import com.wdfall.slot.model.vo.FreespinInputVO;
import com.wdfall.slot.model.vo.FreespinResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
import com.wdfall.slot.rungame.RunGameConstants.RunGameType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Stage01FreespinRun extends AbstractRun<Stage01GameProperty, FreespinAccEmulateResult, Stage01FreespinTask, FreespinInputVO, FreespinResultVO> {

	@Override
	protected void initStageAndType() {
		currentStage = RunGameStage.STAGE01;
		currentType = RunGameType.FREESPIN;
	}
	
	@Override
	protected void parseProperty() {
		Stage01GamePropertyProvider propertyProvider = new Stage01GamePropertyProvider();
		property = propertyProvider.parse(loadedData);
	}

	@Override
	protected void insertResultToDb() {
		FreespinAccEmulateResult result = basicSimulator.getTotalResult();
		
		FreespinResultVO resultVO = new FreespinResultVO();
		resultVO.setInputSeq(inputVO.getSeq());
		resultVO.setStage(currentStage);
		resultVO.setUsername(username);
		resultVO.setFreespinAccEmulateResult(result);
		
		slotService.insertResult(resultVO);
		
		log.info(" >>> resultVO.getSeq = {}", resultVO.getSeq());
	}


}