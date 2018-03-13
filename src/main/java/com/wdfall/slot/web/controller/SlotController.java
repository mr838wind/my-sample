package com.wdfall.slot.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wdfall.slot.model.service.SlotServiceProvider;
import com.wdfall.slot.model.vo.BasicResultVO;
import com.wdfall.slot.rungame.RunGameConstants.RunGameStage;
import com.wdfall.slot.rungame.RunGameConstants.RunGameType;

import lombok.extern.slf4j.Slf4j;

/**
 * slot web 컨트롤러
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Slf4j
@Controller
public class SlotController {
	
	@Autowired
	SlotServiceProvider slotServiceProvider;
	
	/**
	 * main 페이지
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/main")
	public ModelAndView main( HttpServletRequest req ) {
	    
		return new ModelAndView("/slot/main");
	}
	
	
	/**
	 * list 페이지
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list/{stage}/{type}")
	public ModelAndView list( HttpServletRequest req
			,@PathVariable(value = "stage") RunGameStage stage
			,@PathVariable(value = "type") RunGameType type) {
	    
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("stage", stage);
		resultMap.put("type", type);
		
		List<?> list = slotServiceProvider.getSlotService(type).selectResultList(stage);
		resultMap.put("result", list);
		
		return new ModelAndView("/slot/list", resultMap);
	}
	
	/**
	 * detail 페이지
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/detail/{stage}/{type}/{seq}")
	public ModelAndView detail( HttpServletRequest req 
			,@PathVariable(value = "stage") RunGameStage stage
			,@PathVariable(value = "type") RunGameType type
			,@PathVariable(value = "seq") int seq) {
	    
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("stage", stage);
		resultMap.put("type", type);
		resultMap.put("seq", seq);
		
		String viewName = String.format("/slot/detail-%s", type.name().toLowerCase());
		
		BasicResultVO vo = slotServiceProvider.getSlotService(type).selectResultDetail(seq);
		resultMap.put("result", vo);
		
		return new ModelAndView(viewName, resultMap);
	}
	
	
}
