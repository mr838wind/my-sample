package com.wdfall.slot.business.simulator.loader.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import com.wdfall.slot.business.utils.CloneUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
/**
 * ExcelUtils
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class ExcelUtils {

	public static final String INPUT_DATA_TAG_START = "{{%s}}";
	private static final String INPUT_DATA_TAG_END = "{{//%s}}";
	//
	public static final Pattern INPUT_DATA_TAG_START_PATTERN = Pattern.compile("^\\{\\{(\\w*)\\}\\}$");
	public static final Pattern INPUT_DATA_TAG_END_PATTERN = Pattern.compile("^\\{\\{//(\\w*)\\}\\}$");

	@Data
	public static class ParamItemLocation {
		//include start , not include end 
		private int rowIndexStart = -1;
		private int rowIndexEnd = -1;
		private int cellIndexStart = -1;
		private int cellIndexEnd = -1;
		
		//
		public ParamItemLocation copy() {
			return CloneUtils.deepCopyByLib(this);
		}
		
		/**
		 * data location default
		 * 1) 첫번째행은 헤더
		 * 2) 나머지는 data
		 * @return
		 */
		// data location 
		// 1) 첫번째행은 헤더
		// 2) 나머지는 data
		public ParamItemLocation getDataLocationDefault() {
			ParamItemLocation dataLoc = CloneUtils.deepCopyByLib(this);
			dataLoc.setRowIndexStart(dataLoc.getRowIndexStart() + 1);
			return dataLoc;
		}
		
	}
	
	
	public static int parseInt(String data) {
		data = data.replaceAll(",", ""); //숫자표현식중","를 제거
		return (int)(double)Double.valueOf(data);
	}
	
	public static long parseLong(String data) {
		data = data.replaceAll(",", ""); //숫자표현식중","를 제거
		return (long)(double)Double.valueOf(data);
	}
	
	
	@Data
	private static class Location {
		private int rowIndex;
		private int cellIndex;
	}
	
	public static Map<String, ParamItemLocation> findAllParamItem(List<List<String>> excelData) {
		Map<String, ParamItemLocation> result = new HashedMap<>();
		
		Map<String, Location> startTagMaps = new HashedMap<>();
		Map<String, Location> endTagMaps = new HashedMap<>();
		findAndSetStartEndTags(excelData, startTagMaps, endTagMaps);
		
		for(Map.Entry<String, Location> entry : startTagMaps.entrySet()) {
			String startTag = entry.getKey();
			Location startLocation = entry.getValue();
			
			if(endTagMaps.containsKey(startTag)) {
				Location endLocation = endTagMaps.get(startTag);
				
				//start포함, end 배제
				ParamItemLocation itemLoc = new ParamItemLocation();
				itemLoc.setRowIndexStart(startLocation.getRowIndex() + 1);
				itemLoc.setCellIndexStart(startLocation.getCellIndex());
				itemLoc.setRowIndexEnd(endLocation.getRowIndex());
				
				findParamItemSetCellIndexEnd(excelData, itemLoc);
				
				result.put(startTag, itemLoc);
			}
		}
		
		log.debug("result = {}", result);
		return result;
	}

	private static void findAndSetStartEndTags(List<List<String>> excelData, Map<String, Location> startTagMaps,
			Map<String, Location> endTagMaps) {
		for(int rowIndex=0; rowIndex<excelData.size(); rowIndex++) {
			List<String> rowData = excelData.get(rowIndex);
			
			for(int cellIndex=0; cellIndex<rowData.size(); cellIndex++) {
				String cellData = rowData.get(cellIndex);
				
				if(StringUtils.isEmpty(cellData)) {
					continue;
				}
				
				Matcher startMatcher = INPUT_DATA_TAG_START_PATTERN.matcher(cellData);
				if(startMatcher.matches()) {
					String startTag = startMatcher.group(1);
					Location startLocation = new Location();
					startLocation.setRowIndex(rowIndex);
					startLocation.setCellIndex(cellIndex);
					startTagMaps.put(startTag, startLocation);
				}
				
				Matcher endMatcher = INPUT_DATA_TAG_END_PATTERN.matcher(cellData);
				if(endMatcher.matches()) {
					String endTag = endMatcher.group(1);
					Location endLocation = new Location();
					endLocation.setRowIndex(rowIndex);
					endLocation.setCellIndex(cellIndex);
					endTagMaps.put(endTag, endLocation);
				}
			}
		}
		
		log.debug("startTagMaps = {}", startTagMaps);
		log.debug("endTagMaps = {}", endTagMaps);
	}
	
	
	/**
	 * <<header포함 데이터 영역 조회>>
	 * 
	 * header영역은 첫번째 row
	 * start포함, end 배제 [row start,end),[cell start,end) 
	 * row start는 {{inputSymbol}} next row 부터
	 * row end는 {{inputSymbol}} 까지
	 * cell start는 {{inputSymbol}}이 있는 cell 부터
	 * cell end는 header영역에서 첫번째 null인 cell 까지 
	 * @param excelData
	 * @param inputSymbol
	 * @return
	 */
	public static ParamItemLocation findParamItem(List<List<String>> excelData, String inputSymbol) {
		ParamItemLocation result = new ParamItemLocation();
		String paramReelDefineStart = String.format(INPUT_DATA_TAG_START, inputSymbol);
		String paramReelDefineEnd = String.format(INPUT_DATA_TAG_END, inputSymbol);
		for(int rowIndex=0; rowIndex<excelData.size(); rowIndex++) {
			List<String> rowData = excelData.get(rowIndex);
			
			for(int cellIndex=0; cellIndex<rowData.size(); cellIndex++) {
				String cellData = rowData.get(cellIndex);
				
				// 
				if(paramReelDefineStart.equalsIgnoreCase(cellData)) {
					result.setRowIndexStart(rowIndex + 1);
					result.setCellIndexStart(cellIndex);
				}
				
				//
				if(paramReelDefineEnd.equalsIgnoreCase(cellData)) {
					result.setRowIndexEnd(rowIndex);
					break;
				}
				
			}
		}
		
		//
		findParamItemSetCellIndexEnd(excelData, result);
		
		log.debug("findParamItem = {}", result);
		
		return result;
	}

	public static void findParamItemSetCellIndexEnd(List<List<String>> excelData, ParamItemLocation result) {
		List<String> headerList = excelData.get(result.getRowIndexStart());
		
		int firstNonNullCellIndex = -1;
		int cellStart = result.getCellIndexStart();
		int cellEnd = headerList.size();
		for(int cellIndex=cellStart; cellIndex<cellEnd; cellIndex++) {
			String item = headerList.get(cellIndex);
			if(item == null) {
				firstNonNullCellIndex = cellIndex;
				break;
			}
		}
		if(firstNonNullCellIndex == -1) {
			firstNonNullCellIndex = cellEnd;
		}
		
		result.setCellIndexEnd(firstNonNullCellIndex);
	}
	
	public static List<String> readExcelDataVertical(List<List<String>> excelData, ParamItemLocation dataLoc, int cellIndex) {
		int rowIndexStart = dataLoc.getRowIndexStart();
		int rowIndexEnd = dataLoc.getRowIndexEnd();
		List<String> dataList = new ArrayList<>();
		for(int i=rowIndexStart; i<rowIndexEnd; i++) {
			String data = excelData.get(i).get(cellIndex);
			dataList.add(data);
		}
		return dataList;
	}
	
	public static List<String> readExcelDataHorizontal(List<List<String>> excelData, ParamItemLocation dataLoc, int rowIndex) {
		int cellIndexStart = dataLoc.getCellIndexStart();
		int cellIndexEnd = dataLoc.getCellIndexEnd();
		List<String> dataList = new ArrayList<>();
		for(int i=cellIndexStart; i<cellIndexEnd; i++) {
			String data = excelData.get(rowIndex).get(i);
			dataList.add(data);
		}
		return dataList;
	}
	
	public static boolean isEmpty(String s) {
		return ((s == null) || "".equals(s));
	}
	
	public static boolean isEmptyOrZero(String s) {
		boolean result = false;
		if(isEmpty(s)) {
			result = true;
		} else {
			int i = parseInt(s);
			if(i == 0) {
				result = true;
			}
		}
		return result;
	}
	
}
