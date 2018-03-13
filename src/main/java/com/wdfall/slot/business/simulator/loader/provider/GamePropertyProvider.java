package com.wdfall.slot.business.simulator.loader.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wdfall.slot.business.simulator.loader.IFGamePropertyProvider;
import com.wdfall.slot.business.simulator.loader.IFPropertyLoader.TwoDimensionList;
import com.wdfall.slot.business.simulator.loader.PropertyDataMap;
import com.wdfall.slot.business.simulator.loader.excel.ExcelUtils;
import com.wdfall.slot.business.simulator.loader.provider.GamePropertyParam.SymbolDefineParam;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.property.GamePropertyBuilder;

import lombok.extern.slf4j.Slf4j;
/**
 * GamePropertyProvider
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class GamePropertyProvider implements IFGamePropertyProvider {

	
	/*
	 * List<List<String>>> data : 구조:
	 * 첫번째 row: header영역
	 * 기타: data영역
	 */
	@Override
	public GameProperty parse(PropertyDataMap data) {
		// TODO: refactor
		GamePropertyParam param = new GamePropertyParam();
		
		parseDataItemSimulatorSetting(data, param);
		parseDataItemGroudScale(data, param);
		parseDataItemFirstPayoutsSymbolCount(data, param);
		parseDataItemSymbolDefineParamList(data, param);
		parseDataItemReelPropertiesParamList(data, param);
		parseDataItemWinLinePatternParamList(data, param);
		
		GameProperty property = GamePropertyBuilder.makeGamePropertiesFromParam(param);
		return property;
	}

	
	protected void parseDataItemSimulatorSetting(PropertyDataMap data, GamePropertyParam param) {
		TwoDimensionList rawData = data.get("simulatorSetting");
		
		int playThreadCount = ExcelUtils.parseInt(rawData.getUserData(0, 0));
		param.setPlayThreadCount(playThreadCount);
		
		int poolSize = ExcelUtils.parseInt(rawData.getUserData(0, 1));
		param.setPlayThreadPoolSize(poolSize);
		
		long playGameCount = ExcelUtils.parseLong(rawData.getUserData(0, 2));
		param.setPlayGameCount(playGameCount);
		
		log.info("playThreadCount = {}", playThreadCount); 
		log.info("playThreadPoolSize = {}", poolSize); 
		log.info("playGameCount = {}", playGameCount); 
	}
	
	protected void parseDataItemGroudScale(PropertyDataMap data, GamePropertyParam param) {
		TwoDimensionList rawData = data.get("groundScale");
		
		List<String> groudScaleStr = rawData.getColumnData(0);
		int[] groudScale = new int[groudScaleStr.size()];
		for(int i=0; i<groudScaleStr.size(); i++) {
			String item = groudScaleStr.get(i);
			int reelCount = ExcelUtils.parseInt(item);
			groudScale[i] = reelCount;
		}
		param.setGroundScale(groudScale);
		
		log.info("groundScale = {}", groudScale);
	}

	protected void parseDataItemFirstPayoutsSymbolCount(PropertyDataMap data, GamePropertyParam param) {
		TwoDimensionList rawData = data.get("firstPayoutSymbolCount");
		
		String symbolCountStr = rawData.getUserData(0, 0);
		int firstPayoutSymbolCount = ExcelUtils.parseInt(symbolCountStr);
		param.setFirstPayoutSymbolCount(firstPayoutSymbolCount);
		
		log.info("firstPayoutSymbolCount = {}", firstPayoutSymbolCount);
	}

	
	protected void parseDataItemSymbolDefineParamList(PropertyDataMap data, GamePropertyParam param) {
		TwoDimensionList rawData = data.get("symbolDefineParamList");
		
		int firstPayoutSymbolCount = param.getFirstPayoutSymbolCount();
		if(firstPayoutSymbolCount <= 0) {
			throw new IllegalArgumentException("excel: firstPayoutSymbolCount not parsed.");
		}
		
		List<SymbolDefineParam> symbolDefineParamList = new ArrayList<>();
		
		List<String> symbolIdList = rawData.getColumnData(0); //심볼ID--0
		
		//symbol
		for(int rowIndex=0; rowIndex<symbolIdList.size(); rowIndex++) {
			
			SymbolDefineParam ruleParam = generateSymbolDefineParam(rowIndex, rawData, firstPayoutSymbolCount);
			
			symbolDefineParamList.add(ruleParam);
		}
		
		param.setSymbolDefineParamList(symbolDefineParamList);
		
		log.info("symbolDefineParamList = {}", symbolDefineParamList);
	}
	
	protected SymbolDefineParam generateSymbolDefineParam(int rowIndex, TwoDimensionList rawData,  int firstPayoutSymbolCount) {
		//심볼ID--0,	groupSize--1,	objectCode--2,	심볼 type--3,	5	4	3	2
		String symbolId = rawData.getUserData(rowIndex, 0);
		int groupSize = ExcelUtils.parseInt(rawData.getUserData(rowIndex, 1));
		String objectCode = rawData.getUserData(rowIndex, 2);
		int symbolType = ExcelUtils.parseInt(rawData.getUserData(rowIndex, 3));
		
		//
		SymbolDefineParam ruleParam = new SymbolDefineParam();
		ruleParam.setSymbolId(symbolId);
		ruleParam.setGroupSize(groupSize);
		ruleParam.setObjectCode(objectCode);
		ruleParam.setType(symbolType);
		ruleParam.setFirstPayoutSymbolCount(firstPayoutSymbolCount);
		
		List<String> headerLine = rawData.getHeaderLine();
		// 개수
		int firstPayoutIndex = 4; // == firstPayoutSymbolCount Index
		int[] payouts = new int[headerLine.size() - firstPayoutIndex];
		for(int j=firstPayoutIndex; j<headerLine.size(); j++) {
			String dataString = rawData.getUserData(rowIndex, j);
			int pay = ExcelUtils.parseInt(dataString);
			payouts[j-firstPayoutIndex] = pay;
		}
		ruleParam.setPayouts(payouts);
		
		return ruleParam;
	}

	protected void parseDataItemReelPropertiesParamList(PropertyDataMap data, GamePropertyParam param) {
		TwoDimensionList rawData = data.get("reelPropertiesParamList");
		List<String> headerLine = rawData.getHeaderLine();
		
		List<Map<String, Integer>> reelPropertiesParamList = new ArrayList<>(); 
		
		// 릴1,릴2...	
		List<String> symbolIdList = rawData.getColumnData(0); //ID 
		for(int offset=1; offset<headerLine.size(); offset++) {
			List<String> reelList = rawData.getColumnData(offset);
			
			Map<String, Integer> reelComp = generateReelPropertiesParam(symbolIdList, reelList);
			reelPropertiesParamList.add(reelComp);
		}
		
		param.setReelPropertiesParamList(reelPropertiesParamList);
		
		log.info("reelPropertiesParamList = {}", reelPropertiesParamList);
		
	}
	
	protected Map<String, Integer> generateReelPropertiesParam(List<String> symbolIdList, List<String> reelList) {
		Map<String, Integer> reelComp = new HashMap<>();
		for(int i=0; i<symbolIdList.size(); i++) {
			String symbolID = symbolIdList.get(i);
			String countString = reelList.get(i);
			Integer count = (int)(double)Double.valueOf(countString);
			reelComp.put(symbolID, count);
		}
		return reelComp;
	}
	
	protected void parseDataItemWinLinePatternParamList(PropertyDataMap data, GamePropertyParam param) {
		TwoDimensionList rawData = data.get("winLinePatternParamList");
		List<String> headerLine = rawData.getHeaderLine();
		
		// get all pattern 
		List<String> linePatternStrAll = new ArrayList<>();
		for(int i=0; i<headerLine.size(); i++) {
			List<String> item = rawData.getColumnData(i);
			linePatternStrAll.addAll(item);
		}
		
		// parse :  "1,1,1,1,1"
		List<List<Integer>> winLinePatternParamList = new ArrayList<>();
		for(int i=0; i<linePatternStrAll.size(); i++) {
			String patternStr = linePatternStrAll.get(i);
			
			if(! ExcelUtils.isEmpty(patternStr)) {
				List<Integer> pattern = generateLinePattern(patternStr);
				winLinePatternParamList.add(pattern);
			}
		}
		
		int[][] winningPatternList = convert(winLinePatternParamList);
		
		param.setWinLinePatternParamList(winningPatternList);
		
		log.info("winLinePatternParamList = {}", winLinePatternParamList);
	}


	protected int[][] convert(List<List<Integer>> linePatternList) {
		int[][] winningPatternList = new int[linePatternList.size()][];
		for(int i=0; i<linePatternList.size(); i++) {
			List<Integer> pattern = linePatternList.get(i);
			
			int[] patternArray = new int[pattern.size()]; 
			for(int j=0; j<pattern.size(); j++) {
				patternArray[j] = pattern.get(j);
			}
			
			winningPatternList[i] = patternArray;
		}
		return winningPatternList;
	}

	protected List<Integer> generateLinePattern(String patternStr) {
		String[] patternStrSep = patternStr.trim().split(",");
		List<Integer> pattern = new ArrayList<>();
		for(String item : patternStrSep) {
			int value = ExcelUtils.parseInt(item);
			pattern.add(value);
		}
		return pattern;
	}

	
}
