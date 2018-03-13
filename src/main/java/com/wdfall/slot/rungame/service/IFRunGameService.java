package com.wdfall.slot.rungame.service;

import java.io.File;

import com.wdfall.slot.business.utils.ControllerUtil.UpFileInfo;
import com.wdfall.slot.rungame.RunGameConstants.RunGameProgress;

public interface IFRunGameService {
	
	public RunGameProgress getProgress();
	
	public void runSimulator(File file, UpFileInfo upFileInfo, String username);
	
	public String getSheetName();
	
}
