package com.wind.framework;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
/**
 * json 관련 ModelAndView
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
public class JsonModelAndView extends ModelAndView {

	private static final String JSON_VIEW = "jsonView";
	Map<String, Object> resultMap;
	
	public JsonModelAndView(){
		super(JSON_VIEW);
	}
	
	public JsonModelAndView(Map<String, ?> paramMap) {
		super(JSON_VIEW,  paramMap);
	}

	public JsonModelAndView( String attributeName, Object attributeValue){
		this();
		put(attributeName, attributeValue);		
	}
	
	
	public JsonModelAndView put( String attributeName, Object attributeValue){		
		addObject(attributeName, attributeValue);
		return this;
	}
	

}
