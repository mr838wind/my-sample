package com.wdfall.slot.business.simulator.loader.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdfall.slot.business.simulator.loader.IFPropertyLoader.TwoDimensionList;
import com.wdfall.slot.business.simulator.loader.PropertyDataMap;
import com.wdfall.slot.business.simulator.loader.excel.ExcelUtils;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.property.Stage01GameProperty;

import lombok.extern.slf4j.Slf4j;
/**
 * Stage01GamePropertyProvider
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class Stage01GamePropertyProvider extends GamePropertyProvider {

	@Override
	public Stage01GameProperty parse(PropertyDataMap data) {
		// TODO: 더 좋은 방식 찾아보자.
		GameProperty properties = super.parse(data);
		
		Stage01GameProperty result = new Stage01GameProperty(properties);
		
		Map<String, Integer> leftBoxProperty = parseLeftBox(data);
		result.setLeftBoxProperty(leftBoxProperty);
		
		Map<String, Integer> rightBoxProperty = parseRightBox(data);
		result.setRightBoxProperty(rightBoxProperty);
		
		return result;
	}

	private Map<String, Integer> parseLeftBox(PropertyDataMap data) {
		Map<String, Integer> result = new HashMap<>();
		
		TwoDimensionList rawData = data.get("leftBoxProperty");
		if(rawData == null) {
			return result;
		}
		
		List<String> symbolCodeList = rawData.getColumnData(0);
		List<String> frequencyList = rawData.getColumnData(1);
		
		for(int i=0; i<symbolCodeList.size(); i++) {
			String symbol = symbolCodeList.get(i);
			int frequency = ExcelUtils.parseInt(frequencyList.get(i));
			result.put(symbol, frequency);
		}
		
		log.info("leftBoxProperty = {}", result);
		return result;
	}
	
	private Map<String, Integer> parseRightBox(PropertyDataMap data) {
		Map<String, Integer> result = new HashMap<>();
		
		TwoDimensionList rawData = data.get("rightBoxProperty");
		if(rawData == null) {
			return result;
		}
		
		List<String> multipleList = rawData.getColumnData(0);
		List<String> frequencyList = rawData.getColumnData(1);
		
		for(int i=0; i<multipleList.size(); i++) {
			String multiple = multipleList.get(i);
			int frequency = ExcelUtils.parseInt(frequencyList.get(i));
			result.put(multiple, frequency);
		}
		
		log.info("rightBoxProperty = {}", result);
		return result;
	}

	
	
}


