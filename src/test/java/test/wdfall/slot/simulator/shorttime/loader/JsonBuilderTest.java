package test.wdfall.slot.simulator.shorttime.loader;

import java.io.File;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.wdfall.slot.business.simulator.loader.IFPropertyLoader;
import com.wdfall.slot.business.simulator.loader.PropertyDataMap;
import com.wdfall.slot.business.simulator.loader.IFPropertyLoader.TwoDimensionList;
import com.wdfall.slot.business.simulator.loader.excel.ExcelPropertyLoader;
import com.wdfall.slot.business.utils.FileUtil;
import com.wdfall.slot.business.utils.JsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonBuilderTest {

	@Test
	public void testWriteAndRead() {
		
		String jsonResult = objectToJson();
		
		jsonToObject(jsonResult);
	}

	private String objectToJson() {
		File file = FileUtil.getFileOnClasspath("/param/vslot_input_main.xlsx");
		
		IFPropertyLoader loader = new ExcelPropertyLoader();
		PropertyDataMap load = loader.load(file, "3v5_scatter");
		
		String jsonResult = JsonBuilder.objectToJson(load);
		log.debug("jsonResult = {}", jsonResult);
		return jsonResult;
	}
	
	private void jsonToObject(String jsonResult) {
		PropertyDataMap loadFromJson = JsonBuilder.jsonToObject(jsonResult, PropertyDataMap.class);
		log.debug("loadFromJson= {}", loadFromJson);
		TwoDimensionList simuSetting = loadFromJson.get("simulatorSetting");
		log.debug("simuSetting= {}", simuSetting);
		log.debug("simuSetting.getInputData= {}", simuSetting.getInputData());
		log.debug("simuSetting.getUserDataList= {}", simuSetting.getUserDataList());
	}
	
	
}
