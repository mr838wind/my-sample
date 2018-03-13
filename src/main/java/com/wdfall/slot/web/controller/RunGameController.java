package com.wdfall.slot.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.wdfall.slot.business.utils.ControllerUtil;
import com.wdfall.slot.business.utils.ControllerUtil.UpFileInfo;
import com.wdfall.slot.business.utils.ControllerUtil.UploadDomain;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStatus;
import com.wdfall.slot.rungame.RunGameConstants.RunGameType;
import com.wdfall.slot.rungame.service.IFRunGameService;
import com.wdfall.slot.rungame.service.RunGameServiceProvider;
import com.wdfall.slot.spring.JsonModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * simulator 실행 controller
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
@Controller
public class RunGameController {
	
	@Autowired
	RunGameServiceProvider runGameServiceProvider;
	
	/**
	 * 진행률
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/ajax/run/{stage}/{type}/fetchProgress")
	public ModelAndView fetchProgress( HttpServletRequest req 
			,@PathVariable(value = "stage") RunGameStage stage
			,@PathVariable(value = "type") RunGameType type) {
		Map<String, Object> resultMap = new HashMap<>();
		
		IFRunGameService runGameService = runGameServiceProvider.getRunGameService(stage, type);
		resultMap.put("progress", runGameService.getProgress()); 
		
		return new JsonModelAndView(resultMap);
	}
	
	/**
	 * 실행
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/ajax/run/{stage}/{type}/runSimulator")
	public ModelAndView runSimulator( MultipartHttpServletRequest req
			,@PathVariable(value = "stage") RunGameStage stage
			,@PathVariable(value = "type") RunGameType type
			,@RequestParam(name="username") String username) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		
		IFRunGameService runGameService = runGameServiceProvider.getRunGameService(stage, type);
		runGameService.getProgress().setStatus(RunGameStatus.RUNNING);
		
		UpFileInfo upFileInfo = ControllerUtil.handleFileUpload(req, "excelFile", UploadDomain.DOMAIN_EXCEL);
		
		runGameService.runSimulator(upFileInfo.getUpfile(), upFileInfo, username);
		
		resultMap.put("progress", runGameService.getProgress());
		return new JsonModelAndView(resultMap);
	}
	
	/**
	 * reset
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/ajax/run/{stage}/{type}/reset")
	public ModelAndView reset( HttpServletRequest req 
			,@PathVariable(value = "stage") RunGameStage stage
			,@PathVariable(value = "type") RunGameType type) {
		Map<String, Object> resultMap = new HashMap<>();
		
		IFRunGameService runGameService = runGameServiceProvider.getRunGameService(stage, type);
		runGameService.getProgress().clear();
		
		resultMap.put("progress", runGameService.getProgress());
		return new JsonModelAndView(resultMap);
	}
	
	
}
