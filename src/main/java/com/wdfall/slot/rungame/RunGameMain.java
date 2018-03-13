package com.wdfall.slot.rungame;

import java.io.File;

import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
import com.wdfall.slot.rungame.RunGameConstants.RunGameType;
import com.wdfall.slot.rungame.service.IFRunGameService;
import com.wdfall.slot.rungame.service.RunGameServiceProvider;
import com.wdfall.slot.spring.SpringBootstrap;

import lombok.extern.slf4j.Slf4j;
/**
 * simulator desktop 실행하는  main함수
 * (local test할때 사용)
 * 
 * Arguments: ./docs/0_product/input/vslot_param_basic.xlsx basic simulator wind
 * 설명: filepath, stage, type, username
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
public class RunGameMain {

	/**
	 * 실행 파일
	 */
	public static void main(String[] args) throws Exception {
		log.info(">>>>>>>>> RunGameMain start >>>>>>>>>>");
		
		ParseArgs param  = new ParseArgs(args);
		param.printLog(); 
		
		RunGameStage stage = RunGameStage.valueOf(param.getStage());
		RunGameType type = RunGameType.valueOf(param.getType());
		
		RunGameServiceProvider serviceProvider = SpringBootstrap.getInstance().getContext().getBean(RunGameServiceProvider.class);
		IFRunGameService runGameService = serviceProvider.getRunGameService(stage, type);
		
		runGameService.runSimulator(param.getFile(), null, param.getUsername());
		
		SpringBootstrap.getInstance().close();
		log.info(">>>>>>>>> RunGameMain end >>>>>>>>>>");
	}

	/**
	 * Arguments:         ./docs/0_product/input/vslot_param_basic.xlsx basic simulator wind
	 * 설명: filepath, stage, type, username
	 * @author chhan
	 *
	 */
	private static class ParseArgs {
		private String[] args;
		
		public ParseArgs(String[] args) {
			this.args = args;
		}
		
		public String getFileName() {
			return args[0];
		}
		
		public File getFile() {
			return new File(getFileName());
		}
		
		public String getStage() {
			return args[1].toUpperCase();
		}
		
		public String getType() {
			return args[2].toUpperCase();
		}
		
		public String getUsername() {
			return args[3];
		}
		
		public void printLog() {
			log.info("==========================");
			log.info("getAbsolutePath = {}", getFile().getAbsolutePath());
			log.info("getStage = {}", getStage());
			log.info("getType = {}", getType());
			log.info("getUsername = {}", getUsername());
			log.info("==========================");
		}
		
	}
	
}
