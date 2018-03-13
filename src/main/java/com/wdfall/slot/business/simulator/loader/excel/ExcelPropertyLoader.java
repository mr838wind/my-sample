package com.wdfall.slot.business.simulator.loader.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wdfall.slot.business.simulator.loader.IFPropertyLoader;
import com.wdfall.slot.business.simulator.loader.PropertyDataMap;
import com.wdfall.slot.business.simulator.loader.excel.ExcelUtils.ParamItemLocation;

import lombok.extern.slf4j.Slf4j;
/**
 * ExcelPropertyLoader
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class ExcelPropertyLoader implements IFPropertyLoader  {

	@Override
	public PropertyDataMap load(File file, String sheetName) {
		MyExcelReader myExcelReader = new MyExcelReader();
		myExcelReader.processSheetData(file, sheetName);
		return myExcelReader.getDataMap();
	}
	
	
	@Slf4j
	public static class MyExcelReader extends ExcelReader {
		
		private PropertyDataMap dataMap = new PropertyDataMap();
		
		public PropertyDataMap getDataMap() {
			return dataMap;
		}

		@Override
		protected void parseData(List<List<String>> excelData) {
			Map<String, ParamItemLocation> allParamList = ExcelUtils.findAllParamItem(excelData);
			
			fillTagData(excelData, allParamList);
			
			log.debug("dataMap = {}", dataMap);
		}

		private void fillTagData(List<List<String>> excelData, Map<String, ParamItemLocation> allParamList) {
			for(Map.Entry<String, ParamItemLocation> entry : allParamList.entrySet()) {
				String tag = entry.getKey();
				ParamItemLocation loc = entry.getValue();
				
				// List<String> ---- row 
				List<List<String>> tagData = new ArrayList<>();
				for(int rIdx=loc.getRowIndexStart(); rIdx<loc.getRowIndexEnd(); rIdx++) {
					List<String> itemList = new ArrayList<>();
					for(int cIdx=loc.getCellIndexStart(); cIdx<loc.getCellIndexEnd(); cIdx++) {
						String data = excelData.get(rIdx).get(cIdx);
						itemList.add(data);
					}
					tagData.add(itemList);
				}
				
				dataMap.put(tag, new TwoDimensionList(tagData));
			}
		}
		
	}

	
}
