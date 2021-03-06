/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.comm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



public class RedirectInterceptor extends HandlerInterceptorAdapter {
	
	

	/**
	 * 생성자
	 */
	public RedirectInterceptor() {
		super();
	}
	
	
	private static final Logger LOG = LoggerFactory.getLogger(RedirectInterceptor.class);

	/**
	 *  모든 처리요청이 들어오기전에 통과하는 부분
	 *  로그인이 안되는 부분을 리다이렉트 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		if(session == null){
			LOG.info("로그인 필요 ");
			response.sendRedirect("/signin");
			return false;
		}
		
		if(session.getAttribute("userInfo") == null){
			response.sendRedirect("/signin");
			return false;
		}
		
		LOG.info("interceptor --> sessionid : "+session.getId().toString());
		return super.preHandle(request, response, handler);
	}

	
}
