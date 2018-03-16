package com.wind.sample.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * index 컨트롤러
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Controller
public class IndexController {
	
	/**
	 * index 페이지
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/")
	public ModelAndView index( HttpServletRequest req ) {
	    
		return new ModelAndView("redirect:/main");
	}
	
	/**
	 * main
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/main")
	public ModelAndView main( HttpServletRequest req ) {
	    
		return new ModelAndView("/slot/main");
	}
	
	
}
