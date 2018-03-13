package com.wdfall.slot.business.simulator.display;

import java.util.ArrayList;
import java.util.List;

import com.wdfall.slot.business.simulator.EmulatorReel;
import com.wdfall.slot.business.simulator.EmulatorSymbol;
import com.wdfall.slot.business.simulator.factories.IFReelSymbolSelector;
import com.wdfall.slot.business.simulator.property.EmulatorReelProperty;
import com.wdfall.slot.business.simulator.property.GameProperty;

/**
 * 모든 릴의 결과 생성 class
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class DisplayCreator implements IFDisplayCreator {
	
	private IFReelSymbolSelector symbolSelector;
	private GameProperty gameProperties;
	
	public DisplayCreator() {
		super();
	}
	
	public void setGameProperties(GameProperty gameProperties) {
		this.gameProperties = gameProperties;
	}
	
	public void setSymbolSelector(IFReelSymbolSelector symbolSelector) {
		this.symbolSelector = symbolSelector;
	}

	@Override
	public Snapshot createReels() {
		List<EmulatorReel> reels = new ArrayList<EmulatorReel>();
		
		for(int i=0; i<gameProperties.getGroundScale().length; i++) {
			int socketCount = gameProperties.getGroundScale()[i];
			EmulatorReelProperty reelProperty = gameProperties.getReelProperties()[i];
			
			List<EmulatorSymbol> symbolIds = symbolSelector.select(reelProperty, socketCount);
			
			EmulatorReel reel = new EmulatorReel();			
			for( EmulatorSymbol symbol : symbolIds ){
				reel.add( symbol );
			}			
			reels.add(reel);	
			
		}
		
		Snapshot spinResult = new Snapshot();
		spinResult.setPlayReels(reels);
		
		return spinResult;
	}

}
