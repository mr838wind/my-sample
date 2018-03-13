package com.wdfall.slot.rungame.service;

import static com.wdfall.slot.rungame.RunGameConstants.RunGameStage.BASIC;
import static com.wdfall.slot.rungame.RunGameConstants.RunGameType.BONUS;
import static com.wdfall.slot.rungame.RunGameConstants.RunGameType.FREESPIN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.wdfall.slot.rungame.RunGameConstants.RunGameProgress;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
import com.wdfall.slot.rungame.RunGameConstants.RunGameType;
import com.wdfall.slot.rungame.games.AbstractRun;
import com.wdfall.slot.spring.SpringBootstrap;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Runner은 com.wdfall.slot.rungame.games아래에 있음.
 * 	Runner subclass naming rule: 
 * 	1. {stage}{type}Run
 *  2. stage,type -- 첫문자 대문자로
 * 
 *
 */
@Slf4j
@Service
public class RunGameServiceProvider {
	
	@Autowired
	ApplicationContext applicationContext;

	private Map<String, IFRunGameService> servicePool = new HashMap<>();
	private List<String> invalidStageAndTypeList = new ArrayList<>();
	
	/**
	 * getRunGameService
	 * @param stage
	 * @param type
	 * @return
	 */
	public IFRunGameService getRunGameService(RunGameStage stage, RunGameType type) {
		return servicePool.get(getKeyString(stage, type));
	}
	
	/**
	 * stage,type 마다 하나씩 service instance 만든다.
	 */
	@PostConstruct
	public void init() {
		
		initInvalidStageAndTypeList();
		
		for (RunGameStage stage : RunGameStage.values()) {
			for (RunGameType type : RunGameType.values()) {
				if (isInValidStageAndType(stage, type)) {
					continue;
				}

				try {
					IFRunGameService service = createServiceInstance(stage, type);
					servicePool.put(getKeyString(stage, type), service);
				} catch (BeansException e) {
					// stage, type 다 실현하지 않았을때 warning 준다.
					log.warn("EXCEPTION init runner instance: stage={}, type={}", stage, type);
				}
			}
		}
	}

	private void initInvalidStageAndTypeList() {
		invalidStageAndTypeList.add(getKeyString(BASIC, BONUS));
		invalidStageAndTypeList.add(getKeyString(BASIC, FREESPIN));
	}
	
	private boolean isInValidStageAndType(RunGameStage stage, RunGameType type) {
		return invalidStageAndTypeList.contains(getKeyString(stage, type));
	}

	private IFRunGameService createServiceInstance(RunGameStage stage, RunGameType type) {
		BasicRunGameService service = new BasicRunGameService();
		service.setProgress(new RunGameProgress());
		AbstractRun<?,?,?,?,?> runner = createRunnerInstance(stage, type);
		service.setRunner(runner);
		service.setSheetName(type.name().toLowerCase());
		return service;
	}
	
	/**
	 * createRunnerInstance 
	 * 
	 * subclass naming rule: 
	 * 	1. {stage}{type}Run
	 *  2. stage,type -- 첫문자 대문자로
	 *  
	 * subclass instance naming rule: 
	 *  1. instance 이름은  class이름 첫문자를 소문자로 변경
	 *  
	 *  e.g. className = Stage01BonusRun, instanceName = stage01BonusRun
	 * @return
	 */
	private AbstractRun<?,?,?,?,?> createRunnerInstance(RunGameStage stage, RunGameType type) {
		String stageString = StringUtils.capitalize(stage.name().toLowerCase());
		String typeString = StringUtils.capitalize(type.name().toLowerCase());
		String instanceName = StringUtils.uncapitalize(String.format("%s%sRun", stageString, typeString));
		
		AbstractRun<?, ?, ?, ?, ?> runner = (AbstractRun<?,?,?,?,?>) applicationContext.getBean(instanceName);
		return runner;
	}
	
	private String getKeyString(RunGameStage stage, RunGameType type) {
		return stage.name() + "_" + type.name();
	}
	
	

	public static void main(String[] args) {
			//log.debug("hello , {}", "log", new RuntimeException("TestMyException"));
			SpringBootstrap.getInstance().getContext().getBean(RunGameServiceProvider.class);
			SpringBootstrap.getInstance().close();
	}
	
}
