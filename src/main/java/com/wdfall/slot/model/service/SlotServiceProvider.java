package com.wdfall.slot.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wdfall.slot.rungame.RunGameConstants.RunGameType;
/**
 * Service Factory
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Service
public class SlotServiceProvider {

	@Autowired
	SimulatorService simulatorService;
	
	@Autowired
	FreespinService freespinService;
	
	@Autowired
	BonusService bonusService;
	
	public IFSlotService<?,?> getSlotService(RunGameType type) {
		IFSlotService<?,?> service = null;
		
		if(RunGameType.SIMULATOR.equals(type)) {
			service = simulatorService;
		} else if(RunGameType.BONUS.equals(type)) {
			service = bonusService;
		} else if(RunGameType.FREESPIN.equals(type)) {
			service = freespinService;
		}
		return service;
	}
	
	
}
