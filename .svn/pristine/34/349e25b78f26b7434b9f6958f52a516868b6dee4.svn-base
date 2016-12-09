/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 유저 관련 url 처리
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
@Controller
public class ViewController {
	private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

	/**
	 * 생성자
	 */
	public ViewController() {
		// Default Constructor
	};
	
	// 뷰 연결 부분
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signin() {
		return "signin";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		return "signup";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String home() {
		return "main";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search() {
		return "search";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload() {
		return "upload";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update() {
		return "update";
	}
	
	
	@RequestMapping(value = "/test/json", method = RequestMethod.POST)
	public void testJson(@RequestBody MultiValueMap<String,?> param) {
		LOG.info("RequestBody    param1 > class : "+param.getClass()+" // toString : "+param.toString());
	}
	
	@RequestMapping(value= "/test/cnv")
	public ModelAndView testContentNegotiationVR() {
		
		Testss test = new Testss() ;
		test.setAaaa("한글");
		test.setTest("쿠쿠쿠");
		
		ModelAndView res = new ModelAndView();
		res.getModelMap().addAttribute(test);
		return res;
	}
	
	
	@RequestMapping(value= "/test/cnv2")
	public ModelAndView testContentNegotiationVR2() {
		
		Testss test = new Testss() ;
		test.setAaaa("123123123123123한ㄱ");
		test.setTest("zzz");
		

		
		ModelAndView res = new ModelAndView();
		res.getModelMap().addAttribute(test);
		return res;
	}
	
	
	

	

}
