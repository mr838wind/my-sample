package com.wdfall.slot.business.simulator.property;

import java.util.List;
import java.util.Map;

import com.wdfall.slot.business.simulator.loader.provider.GamePropertyParam;
import com.wdfall.slot.business.simulator.loader.provider.GamePropertyParam.SymbolDefineParam;
import com.wdfall.slot.business.simulator.pattern.WinLinePattern;
import com.wdfall.slot.business.simulator.pattern.WinScatterPattern;

/**
 * GamePropertyParam을 GameProperty로 변환
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class GamePropertyBuilder {

	
	public static GameProperty makeGamePropertiesFromParam(GamePropertyParam param) {
		GameProperty prop = new GameProperty();
		
		prop.setPlayThreadCount(param.getPlayThreadCount());
		prop.setPlayThreadPoolSize(param.getPlayThreadPoolSize());
		prop.setPlayGameCount(param.getPlayGameCount());
		
		// 게임 socket 정의 (스테이지 정의)
		prop.setGroundScale(param.getGroundScale());
		
		// 게임에 사용되는  심벌 및 Payout 정의
		List<SymbolDefineParam> symbolDefineParamList = param.getSymbolDefineParamList();
		for(int i=0; i<symbolDefineParamList.size(); i++) {
			SymbolDefineParam sdp = symbolDefineParamList.get(i);
			SymbolDefine sd = new SymbolDefine(sdp.getSymbolId(), sdp.getGroupSize(),sdp.getObjectCode(), sdp.getType(), sdp.getFirstPayoutSymbolCount()).setPayout(sdp.getPayouts());
			prop.addSymbolDefine(sd);
		}
		
		// reel 구성
		for( int i=0; i<prop.getGroundScale().length; i++ ) {
			Map<String, Integer> reelPropertiesParam = param.getReelPropertiesParamList().get(i);
			EmulatorReelProperty reel = new EmulatorReelProperty ();
			for(Map.Entry<String,Integer> item : reelPropertiesParam.entrySet()) {
				reel.addSymbolAmount(item.getKey(), item.getValue());
			}
			prop.setReelProperty(i, reel );
		}
		
		// Win Line 구성
		int[][] winningPatternsParam = param.getWinLinePatternParamList();
		for( int[] line : winningPatternsParam ){
			prop.addWinLine(new WinLinePattern(line));
		}
		//scatter rule
		for(String scatterSymbolCode : prop.getScatterSymbolCodes()) {
			prop.addWinningPattern(new WinScatterPattern(scatterSymbolCode));
		}
		
		return prop;
	}
	
	
}
