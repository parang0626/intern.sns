/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.user;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sns.platform.comm.reslover.CommandMap;
import sns.platform.comm.util.ResponseMap;

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
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Inject
	private UserService userService;

	/**
	 * 생성자
	 */
	public UserController() {
		// Default Constructor
	};
	
	

	/**
	 * 로그인 처리
	 * @param map - 유저 아이디 비번 확인
	 * @return jsonView 형태로 리턴
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginUser(CommandMap map) {
		ResponseMap res;
		if (map.isKey("userId") && map.isKey("userPass") && map.isKey("userType")) {
			String userId = (String) map.getValue("userId");
			String userPass = (String) map.getValue("userPass");
			if (checkRexIdPass(userId, userPass)) {
				Map<String, Object> resMap = userService.loginUser(map.getMap());
				if (resMap != null) {
					res = new ResponseMap(1, "로그인 성공", resMap);
					return new ModelAndView("jsonView", res);
				}
			}
		}
		res = new ResponseMap(0, "로그인 실패");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 로그아웃 처리
	 * @param request - 세션처리를 위해 받아옴
	 * @return jsonview 형태로 리턴
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutUser(HttpServletRequest request) {
		request.getSession().invalidate();
		ResponseMap res = new ResponseMap(1, "로그아웃 성공");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 회원 가입 
	 * @param map - 입력받은 유저 정보
	 * @return JsonView 형태로 리턴
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ModelAndView createUser(CommandMap map) {
		ResponseMap res;
		boolean success;
		if (map.isKey("userId") && map.isKey("userPass") && map.isKey("userNick")) {
			if (map.isFiles("userFile")) {
				List<MultipartFile> files = map.getFile("userFile");
				MultipartFile userImageFile = files.get(0);
				success = userService.createUser(map.getMap(), userImageFile);
				if (success) {
					res = new ResponseMap(1, "유저 생성 성공 파일o");
					return new ModelAndView("jsonView", res);
				}
			} else {
				success = userService.createUser(map.getMap());
				if (success) {
					res = new ResponseMap(1, "유저 생성 성공 파일x");
					return new ModelAndView("jsonView", res);
				}
			}
		} 
		res = new ResponseMap(0, "회원 가입 실패");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 회원 정보 조회
	 * @param userNum - 검색할 번호
	 * @return 유저정보를 jsonView형태로 리턴
	 */
	@RequestMapping(value = "/user/{userNum}", method = RequestMethod.GET)
	public ModelAndView searchUser(@PathVariable int userNum) {
		ResponseMap res;
		if (userNum > 0) {
			Map<String, Object> resMap = userService.searchUser(userNum);
			if (resMap != null) {
				res = new ResponseMap(1, "유저 조회 성공", resMap);
				return new ModelAndView("jsonView", res);
			}
		}
		res = new ResponseMap(0, "회원 조회 실패");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 유저 정보 수정
	 * @param map - 수정될 유저정보 포함
	 * @return jsonview 형태로 리턴
	 */
	@RequestMapping(value = "/user/modify", method = RequestMethod.POST) // 수정해야됨
	public ModelAndView modifyUser(CommandMap map) {
		ResponseMap res;

		// 수정하고자 하는 키값이 하나라도 있는지 확인
		if (!map.isKey("userNick") && !map.isKey("userPass") && !map.isKey("userComment") && !map.isFiles("userFile")) {
			res = new ResponseMap(1, "수정할  값을 입력해 주세요");
			return new ModelAndView("jsonView", res);
		}
		map.sessionToMap("userNum");
		
		
		// 프로필 사진파일이 있는 경우와 없는 경우로 분기
		if (map.isFiles("userFile")) {
			List<MultipartFile> files = map.getFile("userFile");
			MultipartFile userImageFile = files.get(0);
			map.sessionToMap("userImagePath");
			Map<String, Object> usesInfo = userService.modifyUser(map.getMap(), userImageFile);
			if (usesInfo != null) {
				res = new ResponseMap(1, "유저 수정 성공",usesInfo);
				return new ModelAndView("jsonView", res);
			}
		} else {
			Map<String, Object> usesInfo = userService.modifyUser(map.getMap());
			if (usesInfo != null) {
				res = new ResponseMap(1, "유저 수정 성공",usesInfo);
				return new ModelAndView("jsonView", res);
			}
		}

		res = new ResponseMap(0, "유저 수정 실패");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 회원 탈퇴
	 * @return jsonview형태로 결과 반환
	 */
	@RequestMapping(value = "/user", method = RequestMethod.DELETE) // 수정해야됨
	public ModelAndView removeUser(CommandMap map,HttpServletRequest request) {
		ResponseMap res;
		LOG.info(""+(int)map.getSessionValue("userNum"));
		map.sessionToMap("userNum");
		map.sessionToMap("userImagePath");
		boolean success = userService.removeUser(map.getMap());
		if(success){
			request.getSession().invalidate();
			res = new ResponseMap(0, "성공");
			return new ModelAndView("jsonView",res);
		}
		res = new ResponseMap(0, "실패");
		return new ModelAndView("jsonView",res);
	}

	/**
	 * 아이디 중복체크
	 * 
	 * @param userId- 확인하려는 아이디
	 * @return jsonview형태로 결과 반환
	 */
	@RequestMapping(value = "/user/checkid/{userId}", method = RequestMethod.GET)
	public ModelAndView checkId(@PathVariable String userId) {
		ResponseMap res;
		if (userId != null) {
			boolean result = userService.checkId(userId);
			if (result) {
				res = new ResponseMap(1, "중복되는아이디가 존재하지 않습니다.");
				return new ModelAndView("jsonView", res);
			} else {
				res = new ResponseMap(0, "중복되는 아이디가 존재합니다.");
				return new ModelAndView("jsonView", res);
			}	
		}
		res = new ResponseMap(0, "입력받은 값이 올바르지 않습니다.");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 닉네임 확인
	 * @param userNick - 확인하려는 닉네임
	 * @return jsonview형태로 결과 반환
	 */
	@RequestMapping(value = "/user/checkname/{userNick}", method = RequestMethod.GET)
	public ModelAndView checkNick(@PathVariable String userNick) {
		ResponseMap res;
		if (userNick != null) {
			boolean success = userService.checkNick(userNick);
			if (success) {
				res = new ResponseMap(1, "중복되는 닉네임 존재하지 않습니다.");
				return new ModelAndView("jsonView", res);
			} else {
				res = new ResponseMap(0, "중복되는 닉네임이 존재합니다.");
				return new ModelAndView("jsonView", res);
			}	
		}
		res = new ResponseMap(0, "입력받은 값이 올바르지 않습니다.");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 비밀번호 재확인
	 * @param map -확인하려는 비밀번호와 유저정보 포함
	 * @return jsonview형태로 결과 반환
	 */
	@RequestMapping(value = "/user/checkpass", method = RequestMethod.POST)
	public ModelAndView checkPass(CommandMap map) {
		ResponseMap res;
		if (map.isKey("userPass")) {
			int userNum = (int) map.getSessionValue("userNum");
			boolean result = userService.checkPass(userNum, (String) map.getValue("userPass"));
			if (result) {
				res = new ResponseMap(1, "비밀번호가 일치합니다.");
				return new ModelAndView("jsonView", res);
			} else{
				res = new ResponseMap(0, "비밀번호가 일치하지 않습니다.");
				return new ModelAndView("jsonView", res);
			}
			
		}
		res = new ResponseMap(0, "입력받은 값이 올바르지 않습니다.");
		return new ModelAndView("jsonView", res);
	}
	
	/**
	 * 유저 프로필 이미지 
	 * @param userNum - 해당 유저 번호
	 * @return Filedownload 형태로 반환
	 */
	@RequestMapping(value = "/user/{userNum}/image", method = RequestMethod.GET)
	public ModelAndView userImageDown(CommandMap map,@PathVariable int userNum) {
		if (userNum > 0) {
			String filePath = userService.searchUserImagePath(userNum);
			if (filePath != null) {
				File downloadFile = new File(filePath);
				
				return new ModelAndView("download", "downloadFile", downloadFile);
			}
		}
		return null;
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ModelAndView duplicateErrorHandler(Exception e,HttpServletResponse response){
		LOG.debug("에러진입");
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("errorMessage", e.getMessage());
		ModelAndView res = new ModelAndView();
		res.addObject("response", resMap);
		return res;
	}
	

	/**
	 * 아이디와 비밀번호 형태 확인
	 * @param userId 확인하려는 아이디
	 * @param userPass 확인하려는 비밀번호
	 * @return 맞는지 아닌지 반환
	 */
	private boolean checkRexIdPass(String userId, String userPass) {
		if (userId.length() >= 6 && Pattern.matches("^[a-zA-Z0-9]*$", userId) && userPass.length() >= 6) {
			return true;
		}
		return false;
	}
}
