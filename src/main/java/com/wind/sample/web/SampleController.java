package com.wind.sample.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	@RequestMapping(value = "/detail/{id}")
	public ModelAndView detail( HttpServletRequest req,
			@PathVariable(name = "id") String id ) {
		
		List<SampleVO> list = sampleService.selectSampleList();
		
		// 
		SampleVO result = new SampleVO();
		if(list != null) {
			for(SampleVO item : list) {
				if(item.getId().equals(id)) {
					result = item;
					break;
				}
			}
		}
		
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("result", result);
	    
		return new ModelAndView("/sample/detail", resultMap);
	}
	
	
}
