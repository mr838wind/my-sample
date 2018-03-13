package com.wdfall.slot.rungame.games;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import com.wdfall.slot.business.simulator.loader.IFPropertyLoader;
import com.wdfall.slot.business.simulator.loader.PropertyDataMap;
import com.wdfall.slot.business.simulator.loader.excel.ExcelPropertyLoader;
import com.wdfall.slot.business.simulatorframework.AbstractBasicTask;
import com.wdfall.slot.business.simulatorframework.BasicAccResult;
import com.wdfall.slot.business.simulatorframework.BasicProperty;
import com.wdfall.slot.business.simulatorframework.BasicSimulator;
import com.wdfall.slot.business.utils.ControllerUtil.UpFileInfo;
import com.wdfall.slot.model.service.IFSlotService;
import com.wdfall.slot.model.service.SlotServiceProvider;
import com.wdfall.slot.model.vo.BasicInputVO;
import com.wdfall.slot.model.vo.BasicResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameProgress;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
import com.wdfall.slot.rungame.RunGameConstants.RunGameType;

import lombok.extern.slf4j.Slf4j;

/**
 * AbstractRun
 * 
 * subclass naming rule: 
 * 	1. {stage}{type}Run
 *  2. stage,type -- 첫문자 대문자로
 * @author chhan
 */
@Slf4j
public abstract class AbstractRun<P extends BasicProperty, R extends BasicAccResult, T extends AbstractBasicTask<P,R>, IV extends BasicInputVO, RV extends BasicResultVO  > {

	// === classes
	protected Class<R> clazzResult;
	protected Class<T> clazzTask;
	protected Class<IV> clazzInputVO;
	protected Class<RV> clazzResultVO;
	
	// === input
	protected File file;
	protected String sheetName;
	protected String username;
	protected UpFileInfo upFileInfo;	//uploaded file 정보: (web)
	
	
	// === stage...
	protected RunGameStage currentStage;
	protected RunGameType currentType;
	protected volatile RunGameProgress progress; 	// 진행률 보고
	
	
	// === simulator
	protected IFPropertyLoader loader = new ExcelPropertyLoader();
	protected PropertyDataMap loadedData;
	protected P property;
	protected BasicSimulator<P,R,T> basicSimulator;
	
	
	// === db
	@Autowired
	protected SlotServiceProvider slotServiceProvider;
	protected IFSlotService<IV, RV> slotService;
	protected IV inputVO;
	
	public void setProgress(RunGameProgress progress) {
		this.progress = progress;
	}
	
	public void setUpFileInfo(UpFileInfo upFileInfo) {
		this.upFileInfo = upFileInfo;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@PostConstruct
	@SuppressWarnings("unchecked")
	private void init() {
		initStageAndType();
		
		this.clazzResult = (Class<R>) GenericTypeResolver.resolveTypeArguments(getClass(), AbstractRun.class)[1];
		this.clazzTask = (Class<T>) GenericTypeResolver.resolveTypeArguments(getClass(), AbstractRun.class)[2];
		this.clazzInputVO = (Class<IV>) GenericTypeResolver.resolveTypeArguments(getClass(), AbstractRun.class)[3];
		this.clazzResultVO = (Class<RV>) GenericTypeResolver.resolveTypeArguments(getClass(), AbstractRun.class)[4];
		
		//
		slotService = (IFSlotService<IV, RV>) slotServiceProvider.getSlotService(currentType);
	}
	
	
	
	/**
	 * main template method
	 */
	public final void runMain() {
		try {
			
			loadExcelData();
			
			insertInputToDb();
			
			parseProperty();
			
			run();
			
			insertResultToDb();
		
		} catch (Exception e) {
			throw new RuntimeException("AbstractRun exception", e);
		}
	}
	
	
	
	protected abstract void initStageAndType();

	private void loadExcelData() {
		loadedData = loader.load(file, sheetName);
	}
	
	protected void insertInputToDb() throws Exception {
		inputVO = clazzInputVO.newInstance();
		inputVO.setData(loadedData);
		inputVO.setStage(currentStage);
		if(upFileInfo != null) {
			inputVO.setDbPath(upFileInfo.getDbPath());
			inputVO.setFilename(upFileInfo.getFilename());
		}
		
		slotService.insertInput(inputVO);
		
		log.info(" >>> inputVO.getSeq = {}", inputVO.getSeq());
	}

	protected abstract void parseProperty();
	
	protected void run() throws Exception {
		basicSimulator = new BasicSimulator<>(property, clazzResult, clazzTask);
		basicSimulator.setProgress(progress);
		basicSimulator.runTest();
	}
	
	protected abstract void insertResultToDb();
	
}
