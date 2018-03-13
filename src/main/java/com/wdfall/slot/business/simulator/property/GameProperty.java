package com.wdfall.slot.business.simulator.property;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern;
import com.wdfall.slot.business.simulator.pattern.IFEmulatorWinningPattern.PatternType;
import com.wdfall.slot.business.simulator.pattern.WinLinePattern;
import com.wdfall.slot.business.simulatorframework.BasicProperty;

import lombok.Data;
/**
 * GameProperty
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Data
public class GameProperty extends BasicProperty {

	// 스레드수
	//protected int playThreadCount;
	// 스레드 풀 size
	//protected int playThreadPoolSize;
	// thread당 game 진행 수
	//protected long playGameCount;
	
	
	// ============
	// 게임 socket 정의 (스테이지 정의)
	protected int[] groundScale;
	
	// 릴 심블 구성 <objectCode,count>
	protected EmulatorReelProperty[] reelProperties;
	
	// win line pattern
	protected List<IFEmulatorWinningPattern> winningPatterns = new ArrayList<IFEmulatorWinningPattern>();
	
	// 심블 정의 및 payout: <objectCode,SymbolDefine>
	protected SymbolDefineMapWrapper symbolDefineMapWrapper = new SymbolDefineMapWrapper();
	
	
	public void setGroundScale(int...  socketCountPerReel) {
		reelProperties = new EmulatorReelProperty[socketCountPerReel.length];
		groundScale = new int[socketCountPerReel.length];
		for (int i=0 ; i < socketCountPerReel.length ; i++) {
			groundScale[i] = socketCountPerReel[i];
		}		
	}
	
	
	public void addSymbolDefine(SymbolDefine symbolDefine) {
		symbolDefineMapWrapper.addSymbolDefine(symbolDefine);
	}
	
	public SymbolDefine getSymbolDefineByObjectCode(String objectCode) {
		return symbolDefineMapWrapper.getSymbolDefineByObjectCode(objectCode);
	}
	
	public SimulatorPayoutPropertyTable getPayoutTable() {
		return symbolDefineMapWrapper.getPayoutTable();
	}
	
	public void setPayoutTable(SimulatorPayoutPropertyTable payoutTable) {
		symbolDefineMapWrapper.setPayoutTable(payoutTable);
	}
	
	public boolean hasWildSymbolId() {
		String wildSymbolId = getWildSymbolId();
		return (!StringUtils.isEmpty(wildSymbolId));
	}
	
	public String getWildSymbolId() {
		return symbolDefineMapWrapper.getWildSymbolCode();
	}
	
	public List<String> getScatterSymbolCodes() {
		return symbolDefineMapWrapper.getScatterSymbolCodes();
	}
	
	

	public void setReelProperty(int idx, EmulatorReelProperty reelProperty) {
		if(reelProperties==null) throw new InvalidSimulatorProperty("groundScale is not setted. First call setSocket() ");
		
		List<String> objectCodes = reelProperty.getSymbolObjectCodes();
		for (String objectCode : objectCodes) {
			if( ! symbolDefineMapWrapper.containsObjectCode(objectCode)) {
				throw new InvalidSimulatorProperty("["+objectCode+"] is not defined. ");
			}
		}
		
		reelProperties[idx] = reelProperty;
		
	}
	
	public void addWinningPattern(IFEmulatorWinningPattern pattern) {
		winningPatterns.add(pattern);
	}

	public void addWinLine(WinLinePattern winLine) {
		
		if( groundScale == null || groundScale.length != winLine.length()  ) {		
			throw new InvalidSimulatorProperty("Must set GroundScale");
		}
		
		int winLength = winLine.length();
		for(int i=0; i< winLength ; i++) {
			
			if( winLine.indexAt(i) < 0 ||  winLine.indexAt(i) >= groundScale[i] ) {
				throw new InvalidSimulatorProperty();
			}
		}
		
		winLine.length();
		
		
		winningPatterns.add(winLine);
	}

	public List<IFEmulatorWinningPattern> getWinningPatterns() {
		return winningPatterns;
	}

	public int countWinLines() {
		return winningPatterns.size();
	}
	
	// 총 bet---max line
	public int getTotalBet() {
		int result = 0;
		for(IFEmulatorWinningPattern pattern : winningPatterns) {
			if(PatternType.LINE.equals(pattern.getPatternType())) {
				result++;
			}
		}
		return result;
	}
	

}
