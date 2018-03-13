package com.wdfall.slot.web.controller;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wdfall.slot.business.utils.ControllerUtil;
import com.wdfall.slot.spring.JsonModelAndView;
/**
 * download
 * 
 * @author chhan
 * 2017. 12. 28.
 *
 */
@Controller
public class DownloadController {

	/**
	 * download
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/download")
	public ModelAndView main(HttpServletRequest request 
			,HttpServletResponse response
			,@RequestParam(value="dbPath") String dbPath
			,@RequestParam(value="filename") String filename) {

		String realPath = request.getServletContext().getRealPath(dbPath);
		File aFile = new File(realPath);
		
		ControllerUtil.setContentsType(request, response, aFile , filename);
		ControllerUtil.download(aFile, response);
		
		return new JsonModelAndView(new HashMap<String,Object>());
	}
	
}
