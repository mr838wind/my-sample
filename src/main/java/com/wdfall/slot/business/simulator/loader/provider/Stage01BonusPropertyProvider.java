package com.wdfall.slot.business.simulator.loader.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdfall.slot.business.bonus.Stage01BonusProperty;
import com.wdfall.slot.business.bonus.Stage01BonusProperty.WheelColor;
import com.wdfall.slot.business.bonus.Stage01BonusProperty.WheelPiece;
import com.wdfall.slot.business.simulator.loader.IFBonusPropertyProvider;
import com.wdfall.slot.business.simulator.loader.IFPropertyLoader.TwoDimensionList;
import com.wdfall.slot.business.simulator.loader.PropertyDataMap;
import com.wdfall.slot.business.simulator.loader.excel.ExcelUtils;

import lombok.extern.slf4j.Slf4j;
/**
 * Stage01BonusPropertyProvider
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class Stage01BonusPropertyProvider implements IFBonusPropertyProvider {

	@Override
	public Stage01BonusProperty parse(PropertyDataMap data) {
		Stage01BonusProperty result = new Stage01BonusProperty();
		
		Map<String, Object> simuMap = parseSimuSetting(data);
		result.setPlayThreadCount((int)simuMap.get("playThreadCount"));
		result.setPlayThreadPoolSize((int)simuMap.get("playThreadPoolSize"));
		result.setPlayGameCount((long)simuMap.get("playGameCount"));
		
		List<WheelPiece> wheelPieceList = parseWheelPieceList(data);
		result.setWheelPieceList(wheelPieceList);
		
		return result;
	}

	private Map<String, Object> parseSimuSetting(PropertyDataMap data) {
		TwoDimensionList rawData = data.get("simulatorSetting");
		
		int playThreadCount = ExcelUtils.parseInt(rawData.getUserData(0, 0));
		
		int playThreadPoolSize = ExcelUtils.parseInt(rawData.getUserData(0, 1));
		
		long playGameCount = ExcelUtils.parseLong(rawData.getUserData(0, 2));
		
		log.info("playThreadCount = {}", playThreadCount); 
		log.info("playThreadPoolSize = {}", playThreadPoolSize); 
		log.info("playGameCount = {}", playGameCount);
		
		Map<String, Object> result = new HashMap<>();
		result.put("playThreadCount", playThreadCount);
		result.put("playThreadPoolSize", playThreadPoolSize);
		result.put("playGameCount", playGameCount);
		return result;
	}

	
	private List<WheelPiece> parseWheelPieceList(PropertyDataMap data) {
		TwoDimensionList rawData = data.get("wheelPieceList");
		
		List<WheelPiece> result = new ArrayList<>();
		
		List<String> multipleList = rawData.getColumnData(0);
		List<String> frequencyList = rawData.getColumnData(1);
		List<String> colorList = rawData.getColumnData(2);
		
		for(int i=0; i<multipleList.size(); i++) {
			int multiple = ExcelUtils.parseInt(multipleList.get(i));
			int frequency = ExcelUtils.parseInt(frequencyList.get(i));
			String colorStr = colorList.get(i);
			WheelColor color = WheelColor.valueOf(colorStr);
			
			WheelPiece piece = new WheelPiece(multiple, frequency, color);
			result.add(piece);
		}
		
		log.info("wheelPieceList = {}", result);
		return result;
	}

	
	
}


