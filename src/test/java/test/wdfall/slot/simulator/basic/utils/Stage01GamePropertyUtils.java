package test.wdfall.slot.simulator.basic.utils;

import static com.wdfall.slot.business.utils.SlotConstants.*;

import java.util.HashMap;
import java.util.Map;

import com.wdfall.slot.business.simulator.pattern.WinLinePattern;
import com.wdfall.slot.business.simulator.pattern.WinScatterPattern;
import com.wdfall.slot.business.simulator.property.EmulatorReelProperty;
import com.wdfall.slot.business.simulator.property.Stage01GameProperty;
import com.wdfall.slot.business.simulator.property.SymbolDefine;

public class Stage01GamePropertyUtils {

	/*
	 * stage1
	 */
	public static Stage01GameProperty makeGamePropertiesStage1() {
		Stage01GameProperty gameProperties = new Stage01GameProperty();
		
		gameProperties.setPlayThreadCount(1);
		gameProperties.setPlayThreadPoolSize(1); 
		gameProperties.setPlayGameCount(1000 * 1000);
		
		// 게임 socket 정의 (스테이지 정의)
		gameProperties.setGroundScale( 3, 3, 3, 3, 3);	//릴 별 소켓수
		
		int firstPayoutSymbolCount = 5;
		// 게임에 사용되는  심벌 및 Payout 정의
		gameProperties.addSymbolDefine(new SymbolDefine(WILD, 3, WILD_3, SymbolDefine.TYPE_WILD, firstPayoutSymbolCount).setPayout(5000,500,50));
		//
		gameProperties.addSymbolDefine(new SymbolDefine("H1", 1, "H1.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(1000,200,40));
		gameProperties.addSymbolDefine(new SymbolDefine("H2", 1, "H2.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(500,150,30));
		gameProperties.addSymbolDefine(new SymbolDefine("M1", 1, "M1.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(300,100,25));
		gameProperties.addSymbolDefine(new SymbolDefine("M2", 1, "M2.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(200,75,20));
		gameProperties.addSymbolDefine(new SymbolDefine("M3", 1, "M3.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(200,75,20));
		gameProperties.addSymbolDefine(new SymbolDefine("L1", 1, "L1.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(100,50,15));
		gameProperties.addSymbolDefine(new SymbolDefine("L2", 1, "L2.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(100,50,15));
		gameProperties.addSymbolDefine(new SymbolDefine("L3", 1, "L3.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(50,25,10));
		gameProperties.addSymbolDefine(new SymbolDefine("L4", 1, "L4.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(50,25,10));
			//scatter
		gameProperties.addSymbolDefine(new SymbolDefine(SS_BONUS, 1, SS_BONUS_1, SymbolDefine.TYPE_SCATTER_BONUS, 5).setPayout(200,60,20));
		gameProperties.addSymbolDefine(new SymbolDefine(SS_FREESPIN, 1, SS_FREESPIN_1, SymbolDefine.TYPE_SCATTER_FREESPIN, 5).setPayout(500,200,100));
				
		// Reel 구성
		EmulatorReelProperty reel0 = new EmulatorReelProperty ();
		reel0.addSymbolAmount(WILD_3, 1).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 2).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 3).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 4).addSymbolAmount("L2.1", 3)
			.addSymbolAmount("L3.1", 4).addSymbolAmount("L4.1", 4).addSymbolAmount(SS_BONUS_1, 1).addSymbolAmount(SS_FREESPIN_1, 1);
		gameProperties.setReelProperty(0, reel0);
		
		EmulatorReelProperty reel1 = new EmulatorReelProperty ();
		reel1.addSymbolAmount(WILD_3, 1).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 3).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 3).addSymbolAmount("M3.1", 2).addSymbolAmount("L1.1", 3).addSymbolAmount("L2.1", 4)
			.addSymbolAmount("L3.1", 4).addSymbolAmount("L4.1", 4).addSymbolAmount(SS_BONUS_1, 1).addSymbolAmount(SS_FREESPIN_1, 1);
		gameProperties.setReelProperty(1, reel1);
		
		EmulatorReelProperty reel2 = new EmulatorReelProperty ();
		reel2.addSymbolAmount(WILD_3, 1).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 3).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 3).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 4).addSymbolAmount("L2.1", 4)
			.addSymbolAmount("L3.1", 4).addSymbolAmount("L4.1", 2).addSymbolAmount(SS_BONUS_1, 1).addSymbolAmount(SS_FREESPIN_1, 1);
		gameProperties.setReelProperty(2, reel2);
		
		EmulatorReelProperty reel3 = new EmulatorReelProperty ();
		reel3.addSymbolAmount(WILD_3, 1).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 3).addSymbolAmount("M1.1", 2)
			.addSymbolAmount("M2.1", 4).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 3).addSymbolAmount("L2.1", 4)
			.addSymbolAmount("L3.1", 5).addSymbolAmount("L4.1", 2).addSymbolAmount(SS_BONUS_1, 1).addSymbolAmount(SS_FREESPIN_1, 1);
		gameProperties.setReelProperty(3, reel3);
		
		EmulatorReelProperty reel4 = new EmulatorReelProperty ();
		reel4.addSymbolAmount(WILD_3, 1).addSymbolAmount("H1.1", 3).addSymbolAmount("H2.1", 4).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 4).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 4).addSymbolAmount("L2.1", 3)
			.addSymbolAmount("L3.1", 3).addSymbolAmount("L4.1", 1).addSymbolAmount(SS_BONUS_1, 1).addSymbolAmount(SS_FREESPIN_1, 1);
		gameProperties.setReelProperty(4, reel4);
		
		
		// Win Line 구성
		int[][] wininLines= {
				new int[]{0,0,0,0,0,}
		};
		for( int[] line : wininLines ){
			gameProperties.addWinLine( new WinLinePattern( line  ));
		}
		// scatter
		gameProperties.addWinningPattern(new WinScatterPattern(SS_BONUS));
		gameProperties.addWinningPattern(new WinScatterPattern(SS_FREESPIN));
		
		// new: left right box
		Map<String, Integer> leftBoxProperty = new HashMap<>();
		leftBoxProperty.put("H1", 1);
		leftBoxProperty.put("H2", 2);
		leftBoxProperty.put("M1", 5);
		leftBoxProperty.put("M2", 6);
		leftBoxProperty.put("M3", 7);
		gameProperties.setLeftBoxProperty(leftBoxProperty );
		
		Map<String, Integer> rightBoxProperty = new HashMap<>();
		rightBoxProperty.put("2", 10);
		rightBoxProperty.put("3", 5);
		rightBoxProperty.put("4", 2);
		gameProperties.setRightBoxProperty(rightBoxProperty );
		
		
		return gameProperties;
	}
	
	/**
	 *  free spin
	 */
	public static Stage01GameProperty makeGamePropertiesStage1Freespin() {
		Stage01GameProperty gameProperties = new Stage01GameProperty();
		
		gameProperties.setPlayThreadCount(1);
		gameProperties.setPlayThreadPoolSize(1); 
		gameProperties.setPlayGameCount(1000 * 1000);
		
		// 게임 socket 정의 (스테이지 정의)
		gameProperties.setGroundScale( 3, 3, 3, 3, 3);	//릴 별 소켓수
		
		int firstPayoutSymbolCount = 5;
		// 게임에 사용되는  심벌 및 Payout 정의
		gameProperties.addSymbolDefine(new SymbolDefine(WILD, 3, WILD_3, SymbolDefine.TYPE_WILD, firstPayoutSymbolCount).setPayout(5000,500,50));
		//
		gameProperties.addSymbolDefine(new SymbolDefine("H1", 1, "H1.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(1000,200,40));
		gameProperties.addSymbolDefine(new SymbolDefine("H2", 1, "H2.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(500,150,30));
		gameProperties.addSymbolDefine(new SymbolDefine("M1", 1, "M1.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(300,100,25));
		gameProperties.addSymbolDefine(new SymbolDefine("M2", 1, "M2.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(200,75,20));
		gameProperties.addSymbolDefine(new SymbolDefine("M3", 1, "M3.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(200,75,20));
		gameProperties.addSymbolDefine(new SymbolDefine("L1", 1, "L1.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(100,50,15));
		gameProperties.addSymbolDefine(new SymbolDefine("L2", 1, "L2.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(100,50,15));
		gameProperties.addSymbolDefine(new SymbolDefine("L3", 1, "L3.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(50,25,10));
		gameProperties.addSymbolDefine(new SymbolDefine("L4", 1, "L4.1", SymbolDefine.TYPE_NORMAL, firstPayoutSymbolCount).setPayout(50,25,10));
			//scatter
		gameProperties.addSymbolDefine(new SymbolDefine(SS_BONUS, 1, SS_BONUS_1, SymbolDefine.TYPE_SCATTER_BONUS, 5).setPayout(17,17,17));
		gameProperties.addSymbolDefine(new SymbolDefine(SS_FREESPIN, 1, SS_FREESPIN_1, SymbolDefine.TYPE_SCATTER_FREESPIN, 5).setPayout(5,4,3,2,1));
				
		// Reel 구성
		EmulatorReelProperty reel0 = new EmulatorReelProperty ();
		reel0.addSymbolAmount(WILD_3, 1).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 2).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 3).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 4).addSymbolAmount("L2.1", 3)
			.addSymbolAmount("L3.1", 4).addSymbolAmount("L4.1", 4).addSymbolAmount(SS_BONUS_1, 1).addSymbolAmount(SS_FREESPIN_1, 1);
		gameProperties.setReelProperty(0, reel0);
		
		EmulatorReelProperty reel1 = new EmulatorReelProperty ();
		reel1.addSymbolAmount(WILD_3, 1).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 3).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 3).addSymbolAmount("M3.1", 2).addSymbolAmount("L1.1", 3).addSymbolAmount("L2.1", 4)
			.addSymbolAmount("L3.1", 4).addSymbolAmount("L4.1", 4).addSymbolAmount(SS_BONUS_1, 1).addSymbolAmount(SS_FREESPIN_1, 1);
		gameProperties.setReelProperty(1, reel1);
		
		EmulatorReelProperty reel2 = new EmulatorReelProperty ();
		reel2.addSymbolAmount(WILD_3, 1).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 3).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 3).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 4).addSymbolAmount("L2.1", 4)
			.addSymbolAmount("L3.1", 4).addSymbolAmount("L4.1", 2).addSymbolAmount(SS_BONUS_1, 1).addSymbolAmount(SS_FREESPIN_1, 1);
		gameProperties.setReelProperty(2, reel2);
		
		EmulatorReelProperty reel3 = new EmulatorReelProperty ();
		reel3.addSymbolAmount(WILD_3, 1).addSymbolAmount("H1.1", 2).addSymbolAmount("H2.1", 3).addSymbolAmount("M1.1", 2)
			.addSymbolAmount("M2.1", 4).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 3).addSymbolAmount("L2.1", 4)
			.addSymbolAmount("L3.1", 5).addSymbolAmount("L4.1", 2).addSymbolAmount(SS_BONUS_1, 1).addSymbolAmount(SS_FREESPIN_1, 1);
		gameProperties.setReelProperty(3, reel3);
		
		EmulatorReelProperty reel4 = new EmulatorReelProperty ();
		reel4.addSymbolAmount(WILD_3, 1).addSymbolAmount("H1.1", 3).addSymbolAmount("H2.1", 4).addSymbolAmount("M1.1", 3)
			.addSymbolAmount("M2.1", 4).addSymbolAmount("M3.1", 3).addSymbolAmount("L1.1", 4).addSymbolAmount("L2.1", 3)
			.addSymbolAmount("L3.1", 3).addSymbolAmount("L4.1", 1).addSymbolAmount(SS_BONUS_1, 1).addSymbolAmount(SS_FREESPIN_1, 1);
		gameProperties.setReelProperty(4, reel4);
		
		
		// Win Line 구성
		int[][] wininLines= {
				new int[]{0,0,0,0,0,}
		};
		for( int[] line : wininLines ){
			gameProperties.addWinLine( new WinLinePattern( line  ));
		}
		// scatter
		gameProperties.addWinningPattern(new WinScatterPattern(SS_BONUS));
		gameProperties.addWinningPattern(new WinScatterPattern(SS_FREESPIN));
		
		
		return gameProperties;
	}
	
	
	
	
}
