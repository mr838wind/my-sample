package com.wdfall.slot.business.simulator.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Data;
/**
 * Property Loader
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public interface IFPropertyLoader {
	
	public PropertyDataMap load(File file, String sheetName);
	
	@Data
	public static class TwoDimensionList {
		// 입력
		private List<List<String>> inputData;
		// header
		@JsonIgnore
		private List<String> headerLine;
		// 사용자 데이터
		@JsonIgnore
		private List<List<String>> userDataList;
		
		
		public TwoDimensionList() {
			
		}
		
		public TwoDimensionList(List<List<String>> data) {
			super();
			setInputData(data);
		}
		
		public void setInputData(List<List<String>> inputData) {
			this.inputData = inputData;
			splitHeaderAndData();
		}

		private void splitHeaderAndData() {
			if(this.inputData == null || this.inputData.isEmpty()) {
				return;
			}
			this.headerLine = this.inputData.get(0);
			this.userDataList = this.inputData.subList(1, this.inputData.size());
		}

		public String getUserData(int row, int column) {
			return userDataList.get(row).get(column);
		}
		
		public List<String> getColumnData(int columnIndex) {
			List<String> result = new ArrayList<>();
			for(int i=0; i<this.userDataList.size(); i++) {
				String item = this.userDataList.get(i).get(columnIndex);
				result.add(item);
			}
			return result;
		}
		
		public List<String> getRowData(int rowIndex) {
			List<String> result = new ArrayList<>();
			List<String> row = this.userDataList.get(rowIndex);
			result.addAll(row);
			return result;
		}
		
	}
	
}
