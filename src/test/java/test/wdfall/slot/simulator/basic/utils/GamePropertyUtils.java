package test.wdfall.slot.simulator.basic.utils;

import static com.wdfall.slot.business.utils.SlotConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import com.wdfall.slot.business.simulator.loader.IFPropertyLoader;
import com.wdfall.slot.business.simulator.loader.excel.ExcelPropertyLoader;
import com.wdfall.slot.business.simulator.loader.provider.GamePropertyProvider;
import com.wdfall.slot.business.simulator.pattern.WinLinePattern;
import com.wdfall.slot.business.simulator.pattern.WinScatterPattern;
import com.wdfall.slot.business.simulator.property.EmulatorReelProperty;
import com.wdfall.slot.business.simulator.property.GameProperty;
import com.wdfall.slot.business.simulator.property.SymbolDefine;
import com.wdfall.slot.business.utils.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GamePropertyUtils {
	
	
	/*
	 * unit test
	 */
	public static GameProperty makeGamePropertiesForUnitTest() {
		GameProperty gameProperties = new GameProperty();
		
		gameProperties.setPlayThreadCount(1);
		gameProperties.setPlayThreadPoolSize(1); 
		gameProperties.setPlayGameCount(1000 * 1000);
		
		// 게임 socket 정의 (스테이지 정의)
		gameProperties.setGroundScale( 3, 3, 3, 3, 3);	//릴 별 소켓수
		
		int firstPayoutSymbolCount = 5;
		// 게임에 사용되는  심벌 및 Payout 정의
		gameProperties.addSymbolDefine(new SymbolDefine(WILD, 1, WILD_1, SymbolDefine.TYPE_WILD, firstPayoutSymbolCount).setPayout(5000,500,50));
		gameProperties.addSymbolDefine(new SymbolDefine("H1", 1, "H1.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(1000,200,40));
		gameProperties.addSymbolDefine(new SymbolDefine("H2", 1, "H2.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(500,150,30));
		gameProperties.addSymbolDefine(new SymbolDefine("M1", 1, "M1.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(300,100,25));
		gameProperties.addSymbolDefine(new SymbolDefine("M2", 1, "M2.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(200,75,20));
		gameProperties.addSymbolDefine(new SymbolDefine("M3", 1, "M3.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(200,75,20));
		gameProperties.addSymbolDefine(new SymbolDefine("L1", 1, "L1.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(100,50,15));
		gameProperties.addSymbolDefine(new SymbolDefine("L2", 1, "L2.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(100,50,15));
		gameProperties.addSymbolDefine(new SymbolDefine("L3", 1, "L3.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(50,25,10));
		gameProperties.addSymbolDefine(new SymbolDefine("L4", 1, "L4.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(50,25,10));
		gameProperties.addSymbolDefine(new SymbolDefine(SCATTER, 1, SCATTER_1, SymbolDefine.TYPE_SCATTER_BONUS, 5).setPayout(100,50,10));
		
		// Reel 구성
		EmulatorReelProperty reel0 = new EmulatorReelProperty ();
		reel0.addSymbolAmount(WILD_1, 3).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 2).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 3).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 4).addSymbolAmount("L2.1", 3)
			.addSymbolAmount("L3.1", 4).addSymbolAmount("L4.1", 4).addSymbolAmount(SCATTER_1, 1);
		assertThat(reel0.count(), is(32));
		gameProperties.setReelProperty(0, reel0);
		
		EmulatorReelProperty reel1 = new EmulatorReelProperty ();
		reel1.addSymbolAmount(WILD_1, 3).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 3).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 3).addSymbolAmount("M3.1", 2).addSymbolAmount("L1.1", 3).addSymbolAmount("L2.1", 4)
			.addSymbolAmount("L3.1", 4).addSymbolAmount("L4.1", 4).addSymbolAmount(SCATTER_1, 1);
		assertThat(reel1.count(), is(32));
		gameProperties.setReelProperty(1, reel1);
		
		EmulatorReelProperty reel2 = new EmulatorReelProperty ();
		reel2.addSymbolAmount(WILD_1, 3).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 3).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 3).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 4).addSymbolAmount("L2.1", 4)
			.addSymbolAmount("L3.1", 4).addSymbolAmount("L4.1", 2).addSymbolAmount(SCATTER_1, 1);
		assertThat(reel2.count(), is(32));
		gameProperties.setReelProperty(2, reel2);
		
		EmulatorReelProperty reel3 = new EmulatorReelProperty ();
		reel3.addSymbolAmount(WILD_1, 3).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 3).addSymbolAmount("M1.1", 2)
			.addSymbolAmount("M2.1", 4).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 3).addSymbolAmount("L2.1", 4)
			.addSymbolAmount("L3.1", 5).addSymbolAmount("L4.1", 2).addSymbolAmount(SCATTER_1, 1);
		assertThat(reel3.count(), is(32));
		gameProperties.setReelProperty(3, reel3);
		
		EmulatorReelProperty reel4 = new EmulatorReelProperty ();
		reel4.addSymbolAmount(WILD_1, 3).addSymbolAmount("H1.1", 3).addSymbolAmount("H2.1", 4).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 4).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 4).addSymbolAmount("L2.1", 3)
			.addSymbolAmount("L3.1", 3).addSymbolAmount("L4.1", 1).addSymbolAmount(SCATTER_1, 1);
		assertThat(reel4.count(), is(32));
		gameProperties.setReelProperty(4, reel4);
		
		
		// Win Line 구성
		int[][] wininLines= {
				new int[]{0,0,0,0,0},
				new int[]{1,1,1,1,1},
				new int[]{2,2,2,2,2}
		};
		for( int[] line : wininLines ){
			gameProperties.addWinLine( new WinLinePattern( line  ));
		}
		
		gameProperties.addWinningPattern(new WinScatterPattern(SCATTER));
		
		return gameProperties;
	}


	/*
	 * 3v3
	 */
	public static GameProperty makeGameProperties3v3ScatterExcel() {
		File file = FileUtil.getFileOnClasspath("/param/vslot_input_main.xlsx");
		IFPropertyLoader loader = new ExcelPropertyLoader();
		GamePropertyProvider propertyProvider = new GamePropertyProvider();
		GameProperty property = propertyProvider.parse(loader.load(file, "3v3_scatter"));
		return property;
	}
	
	public static GameProperty makeGameProperties3v3Excel() {
		File file = FileUtil.getFileOnClasspath("/param/vslot_input_main.xlsx");
		IFPropertyLoader loader = new ExcelPropertyLoader();
		GamePropertyProvider propertyProvider = new GamePropertyProvider();
		GameProperty property = propertyProvider.parse(loader.load(file, "3v3"));
		return property;
	}
	
	public static GameProperty makeGamePropertiesCheckScatterExcel() {
		File file = FileUtil.getFileOnClasspath("/param/vslot_input_main.xlsx");
		IFPropertyLoader loader = new ExcelPropertyLoader();
		GamePropertyProvider propertyProvider = new GamePropertyProvider();
		GameProperty property = propertyProvider.parse(loader.load(file, "check_scatter"));
		return property;
	}
	
	
	/*
	 * 3v5
	 */
	public static GameProperty makeGameProperties3v5Excel() {
		File file = FileUtil.getFileOnClasspath("/param/vslot_input_main.xlsx");
		IFPropertyLoader loader = new ExcelPropertyLoader();
		GamePropertyProvider propertyProvider = new GamePropertyProvider();
		GameProperty property = propertyProvider.parse(loader.load(file, "3v5"));
		return property;
	}
	
	public static GameProperty makeGameProperties3v5ScatterExcel() {
		File file = FileUtil.getFileOnClasspath("/param/vslot_input_main.xlsx");
		IFPropertyLoader loader = new ExcelPropertyLoader();
		GamePropertyProvider propertyProvider = new GamePropertyProvider();
		GameProperty property = propertyProvider.parse(loader.load(file, "3v5_scatter"));
		return property;
	}
	
	
}
