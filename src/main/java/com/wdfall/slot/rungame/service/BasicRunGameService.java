package com.wdfall.slot.rungame.service;

import java.io.File;

import com.wdfall.slot.business.utils.ControllerUtil.UpFileInfo;
import com.wdfall.slot.rungame.RunGameConstants.RunGameProgress;
import com.wdfall.slot.rungame.games.AbstractRun;

import lombok.Data;

/**
 * BasicRunGameService
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Data
public class BasicRunGameService implements IFRunGameService {
	
	private RunGameProgress progress;
	private AbstractRun<?,?,?,?,?> runner;
	private String sheetName;
	
	@Override
	public RunGameProgress getProgress() {
		return progress;
	}
	
	@Override
	public String getSheetName() {
		return sheetName;
	}
	
	@Override
	public void runSimulator(File file, UpFileInfo upFileInfo, String username) {
		// thread로 비동기 호출
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				runner.setFile(file);
				runner.setSheetName(sheetName);
				runner.setUsername(username);
				runner.setProgress(progress); 
				runner.setUpFileInfo(upFileInfo);
				runner.runMain();
			}
		});
		thread.start();
	}
	
}