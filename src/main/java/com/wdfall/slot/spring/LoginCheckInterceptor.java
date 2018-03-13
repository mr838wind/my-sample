package com.wdfall.slot.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 체크 인터셉터
 * @author hanchanghao
 * @since 2017. 4. 13.
 */
@Slf4j
public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	
	/**
	 * 로그인 체크
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
//		// 로그인 체크
//		AccountVO currentUser = null;
//		try {
//			currentUser = memberSessionHandler.load(request);
//		} catch (Exception e) {
//			// cookie가 문제있을 경우 clear해서 다시 로그인
//			memberSessionHandler.clearSession(request, response);
//		}
//		
//		if(!MemberSessionHandler.isUserLogin(currentUser)) {
//			if(isAjaxRequest(request)) {
//				throw new AjaxServiceException("세션이 만료되었습니다. 다시 로그인 하여 주세요.");
//			} else {
//				response.sendRedirect("/login");
//			}
//			return false;
//		}
			
		return true;
	}
	
	/**
	 * ajax request 판단
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");
		String origin = request.getHeader("ORIGIN");
		boolean isAjax = false;
		if ( ( requestedWith != null
				&&  "xmlhttprequest".equals(requestedWith.toLowerCase() ) )
				|| origin != null ){
			isAjax = true;
		}
		
		return isAjax;
	}
	
}
