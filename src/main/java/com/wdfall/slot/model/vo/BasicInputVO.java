package com.wdfall.slot.model.vo;

import com.wdfall.slot.business.simulator.loader.PropertyDataMap;
import com.wdfall.slot.business.utils.CommonUtils;
import com.wdfall.slot.business.utils.JsonBuilder;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;

import lombok.Data;

@Data
public class BasicInputVO {

	protected int seq;
	protected RunGameStage stage;
	protected String jsonData;
	protected String registerTime = CommonUtils.getCurrentTimestamp(); //default값
	// file 저장 관련
	protected String dbPath;
	protected String filename;
	

	
	public void setData(PropertyDataMap loadedData) {
		this.jsonData = JsonBuilder.objectToJson(loadedData);
	}
	public PropertyDataMap getData() {
		return JsonBuilder.jsonToObject(this.jsonData, PropertyDataMap.class);
	}
	
}
