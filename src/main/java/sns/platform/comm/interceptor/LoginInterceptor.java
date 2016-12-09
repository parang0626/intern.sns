/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.comm.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);
	

	/**
	 * 생성자
	 */
	public LoginInterceptor() {
		super();
	}

	/**
	 * 로그인 후에 처리되는 부분
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		LOG.info("로그인 -> postHandle 진입");
		Map<String,Object> map =  modelAndView.getModel();
		if(map.containsKey("code")){
			if(map.get("code").equals(1)){
				Map<String,Object> newdata = new HashMap<String,Object>();
				Map<String,Object> data = (Map<String, Object>) map.get("data");
				newdata.putAll(data);
				session.setAttribute("userInfo", newdata);
				data.remove("userImagePath");
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOG.info("로그인 인터셉트 진입");
		if(request.getMethod().equals(RequestMethod.DELETE)){
			return false;
		}
		return super.preHandle(request, response, handler);
	}

}
