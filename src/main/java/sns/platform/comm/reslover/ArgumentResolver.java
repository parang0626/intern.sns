/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.comm.reslover;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * <pre>
 * 받는 값,파일과 세션의 유저정보를 CommandMap에 넣어줌
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
public class ArgumentResolver implements HandlerMethodArgumentResolver {

	private static final Logger LOG = LoggerFactory.getLogger(ArgumentResolver.class);


	/**
	 * 생성자
	 */
	public ArgumentResolver() {
		super();
	}
	

	/**
	 * 바디부분을 맵에 담아줌
	 */
	@Override
	public Object resolveArgument(MethodParameter arg0, ModelAndViewContainer arg1, NativeWebRequest arg2,
			WebDataBinderFactory arg3) throws Exception {
		
		CommandMap map = new CommandMap(); // 기본 리졸버로 넘어가지 않게 하기 위해 래퍼맵 생성
		LOG.debug("Argument Resolver 진입!");
		HttpServletRequest request = (HttpServletRequest) arg2.getNativeRequest();
		Enumeration<String> requestKeys = request.getParameterNames();
		String requestContentType = request.getContentType();
		
		

		
		if(requestContentType!=null&&!requestContentType.equals("")){
			if (requestContentType.startsWith("multipart/form-data")) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				if (multipartRequest.getMultiFileMap() != null) {
					map.setFilesMap(multipartRequest.getMultiFileMap());
					map.showFilesMap();
				}
			} 
		}
		
		String key;
		String[] values;
		while (requestKeys.hasMoreElements()) {
			key = requestKeys.nextElement();
			values = request.getParameterValues(key);
			if (values != null) {
				if (values.length <= 1) {
					map.put(key, values[0]);
				} else {
					map.put(key, values);
				}
			}
		}
//		map.showKeyValue();
		
		HttpSession session = request.getSession(false);
		if(session != null){
			map.setSession(session);

		}
//		map.showSessionUser();
		
		
		return map;
	}

	@Override
	public boolean supportsParameter(MethodParameter arg0) {
		// TODO Auto-generated method stub
		LOG.info(arg0.getParameterType().toString());
		return CommandMap.class.isAssignableFrom(arg0.getParameterType());
	}

}
