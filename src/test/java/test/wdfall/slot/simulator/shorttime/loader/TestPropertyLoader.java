package test.wdfall.slot.simulator.shorttime.loader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.regex.Matcher;

import org.junit.Test;

import com.wdfall.slot.business.simulator.loader.IFPropertyLoader;
import com.wdfall.slot.business.simulator.loader.excel.ExcelPropertyLoader;
import com.wdfall.slot.business.simulator.loader.excel.ExcelUtils;
import com.wdfall.slot.business.simulator.loader.provider.GamePropertyProvider;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.utils.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestPropertyLoader {

	@Test
	public void testExcelLoader() {
		File file = FileUtil.getFileOnClasspath("/param/vslot_input_main.xlsx");
		
		IFPropertyLoader loader = new ExcelPropertyLoader();
		GamePropertyProvider propertyProvider = new GamePropertyProvider();
		GameProperty property = propertyProvider.parse(loader.load(file, "3v5_scatter"));
		
		log.debug(" >>> excel loader: prop = \r\n{}", property); 
		
		assertThat(property.getPlayThreadCount(), is(1));
		assertThat(property.getPlayThreadPoolSize(), is(1));
		assertThat(property.getPlayGameCount(), is(1000*1000L));
		
		assertThat(property.getGroundScale().length, is(5));
		assertThat(property.getReelProperties().length, is(5));
		//scatter당 하나 pattern
		int scatterPatternSize = 1; 
		int patternSize = 3 + scatterPatternSize;
		assertThat(property.getWinningPatterns().size(), is(patternSize));
		assertThat(property.getPayoutTable().getSymbolCount(), is(11));
	}
	
	
	@Test
	public void testRegexStart() {
		Matcher matcher = ExcelUtils.INPUT_DATA_TAG_START_PATTERN.matcher("{{ggg}}");
		assertThat(matcher.matches(), equalTo(true));
		assertThat(matcher.group(1), equalTo("ggg"));
	}
	
	@Test
	public void testRegexEnd() {
		Matcher matcher = ExcelUtils.INPUT_DATA_TAG_END_PATTERN.matcher("{{//abc}}");
		assertThat(matcher.matches(), equalTo(true));
		assertThat(matcher.group(1), equalTo("abc"));
	}
}
