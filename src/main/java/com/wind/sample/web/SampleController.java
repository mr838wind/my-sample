package com.wind.sample.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wind.sample.service.SampleService;
import com.wind.sample.service.SampleVO;

/**
 * sample controller
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Controller
public class SampleController {
	
	@Autowired
	SampleService sampleService;
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView main( HttpServletRequest req ) {
		
		List<SampleVO> list = sampleService.selectSampleList();
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("result", list);
	    
		return new ModelAndView("/sample/list", resultMap);
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public ModelAndView detail( HttpServletRequest req ) {
		
		List<SampleVO> list = sampleService.selectSampleList();
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("result", list);
	    
		return new ModelAndView("/sample/detail", resultMap);
	}
	
	
}
