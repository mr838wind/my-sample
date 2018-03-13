package com.wdfall.slot.business.simulator.property;

import java.util.Map;

import lombok.Data;
/**
 * Stage01GameProperty
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Data
public class Stage01GameProperty extends GameProperty {

	public Stage01GameProperty(GameProperty property) {
		this.playThreadCount = property.getPlayThreadCount();
		this.playThreadPoolSize = property.getPlayThreadPoolSize();
		this.playGameCount = property.getPlayGameCount();
		this.groundScale = property.getGroundScale();
		this.reelProperties = property.getReelProperties();
		this.winningPatterns = property.getWinningPatterns();
		this.symbolDefineMapWrapper = property.getSymbolDefineMapWrapper();
	}
		
	public Stage01GameProperty() {
		super();
	}
	
	// 좌측심블 속성
	private Map<String, Integer> leftBoxProperty;
	// 우측배수 속성
	private Map<String, Integer> rightBoxProperty;
	
	
}
