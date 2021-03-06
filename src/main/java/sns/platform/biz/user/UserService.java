/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.user;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sns.platform.biz.file.FileDAO;
import sns.platform.biz.reply.ReplyDAO;
import sns.platform.comm.constant.Constant;

/**
 * <pre>
 * 회원 정보 및 로그인 에 관한 내부로직 처리
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
@Service
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Inject
	private UserDAO userDAO;
	@Inject
	private ReplyDAO replyDAO;
	@Inject
	private FileDAO fileDAO;

	@Value("#{properties['ipaddress']}")
	private String ipAddress;
	@Value("#{properties['file.dirpath']}")
	private String dirPath;
	/**
	 * 생성자
	 */
	public UserService() {
		// Default Constructor
	}

	/**
	 * 로그인 서비스
	 * @param map - 유저의 아이디와 비밀번호 정보가 들어가 있음
	 * @return 로그인한 유저의 정보
	 */
	public Map<String, Object> loginUser(Map<String, Object> map) {
		LOG.info("====> 로그인 - 유저아이디 = " + map.get("userId"));
		int pnsUpdate = userDAO.updatePnsToken(map);
		Map<String, Object> res = userDAO.login(map);
		if (res != null) {
			res.put("imageUrl", ipAddress + "/user/" + res.get("userNum") + "/image");
			return res;
		}
		return null;
	}

	/**
	 * 회원가입 서비스
	 * @param user  - 가입할 유저의 정보
	 * @param userImageFile - 가입하는 유저의 프로필 사진
	 * @return - 가입 성공여부
	 */
	public boolean createUser(Map<String, Object> user, MultipartFile userImageFile) {
		LOG.info("====> 회원 등록(파일O)");
		LOG.info("filname : " + userImageFile.getOriginalFilename());
		LOG.info("filsize : " + userImageFile.getSize());
		String filePath = saveUserProfile(userImageFile);
		if(filePath != null){
			user.put("userImagePath", filePath);
		} else {
			user.put("userImagePath", "/default.jpg");
		}
		return createUser(user);
	}

	/**
	 * 회원가입 서비스
	 * @param user- 가입할 유저 정보
	 * @return - 가입 성공여부
	 */
	public boolean createUser(Map<String, Object> user) {
		LOG.info("====> 회원 등록 - 유저아이디 = " + user.get("userId") + " 유저닉네임 = " + user.get("userNick"));

		if (!user.containsKey("userImagePath") || user.get("userImagePath") == null) {
			user.put("userImagePath", Constant.DATABASE_USERDEFAULTPATH);
		}
		int success = userDAO.insertUser(user);
		 	 
		LOG.info("user insert return int : " + success);
		if (success > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 회원 탈퇴
	 * @param userNum - 지울 유저 번호
	 * @return 성공유무
	 */
	public boolean removeUser(Map<String,Object> user) {
		LOG.info("====> 회원 탈퇴 ");
		int userNum = (int)user.get("userNum");
		
		// 유저가 등록한 게시물의 파일번호와 경로 획득
		List<Map<String, Object>> delFileList = userDAO.selectDelFilesList(userNum);
		
		// 유저가 등록한 댓글 모두 제거
		int delReplys = replyDAO.deleteReplysByUserNum(userNum);
		LOG.info("삭제된 댓글 : "+ delReplys);
		
		// 유저와 유저가 작성한 게시물 그리고 게시물 안에있는 댓글 동시에 제거
		int delUser = userDAO.deleteUserBoardReply(userNum);
		LOG.info("삭제된 유저게시물댓글 : "+ delUser);
	
		
		// 유저가 등록한 파일데이터베이스 제거
		int delFile=0;
		List<Integer> delfileNums = new ArrayList<>();
		if(delFileList != null && !delFileList.isEmpty()){
			for (Map<String, Object> map : delFileList) {
				LOG.info("-->"+map.get("fileNum"));
				delfileNums.add((int) map.get("fileNum"));
			}
			if(delfileNums.size()>0) {
				delFile = fileDAO.deleteFileList(delfileNums);
				LOG.info("삭제된 파일 : "+ delFile);
			}
			if(delFile > 0){
				// 유저가 등록한 실제 파일 제거
				for (Map<String, Object> map : delFileList) {
					File del = new File(dirPath+map.get("filePath"));
					if (del.delete()) {
						LOG.info("파일삭제 : " + map.get("filePath"));
					} else {
						LOG.info("파일삭제실패 : " + map.get("filePath"));
					}
				}
			}	
		}
		if (delUser > 0) {
			String path =(String)user.get("userImagePath");
			// 유저 프로필 로컬 제거 
			if(!path.startsWith("/default")){
				File usrProfile = new File(dirPath+user.get("userImagePath"));
				usrProfile.delete();
			}
			return true;
		}
		return false;

	}

	/**
	 * 회원 정보를 검색
	 * @param userNum - 검색할 유저번호
	 * @return 검색한 회원 정보
	 */
	public Map<String, Object> searchUser(int userNum) {
		LOG.info("====> 회원 조회 - 유저아이디 = " + userNum);

		Map<String, Object> resMap = userDAO.selectUser(userNum);
		if (resMap != null) {
			resMap.put("imageUrl", ipAddress + "/user/" + userNum + "/image");
			return resMap;
		}
		return null;
	}

	/**
	 * 회원 정보 수정 - 프로필 사진 제외
	 * @param user - 수정할 유저번호 포함 수정할 유저 정보
	 * @return 유저수정완료
	 */
	public Map<String, Object> modifyUser(Map<String, Object> user) {
		LOG.info("====> 회원정보 수정(파일제외) 진입");
		int success = userDAO.updateUser(user);
		if (success > 0) {
			return user;
		}
		return null;
	}

	/**
	 * 회원 정보 수정 - 프로필 사진 수정
	 * @param user  - 수정할 유저번호
	 * @param multipartFile  - 수정할 유저번호
	 * @return - 회원 정보 수정으로 이동
	 */
	public Map<String, Object> modifyUser(Map<String, Object> user, MultipartFile multipartFile) {
		LOG.info("====> 회원정보 파일 수정 진입");
		
		// 기존 유저의 이미지 패스 얻어옴
		String oldImagePath = (String) user.get("userImagePath");
		LOG.info("====> 회원정보 수정 - 기존 이미지 패스 :" +oldImagePath);
		String userImagePath= saveUserProfile(multipartFile);
		user.put("userImagePath", userImagePath);
		LOG.info("====> 회원정보 수정 - 새로운 이미지 패스 :" +userImagePath);
		
		// 기존 이미지파일이 디폴트가 아니면 로컬 파일 삭제
		if(!oldImagePath.equals("/dafault.jpg")) {
			String oldImageRealPath = dirPath+oldImagePath;
			File file = new File(oldImageRealPath);
			if(file.exists()){
				LOG.info("====> 회원정보 수정 - 기존프로필 이미지 파일 삭제 = "+file.delete());
			}
		}
		
		// 파일 처리 후 수정으로 이동
		return modifyUser(user); 
	}

	/**
	 * 유저 프로필 이미지 다운을 위해 이미지 주소를 얻어옴
	 * @param userNum 프로필 이미지를 얻어오기 위한 유저번호
	 * @return 현재 이미지 패스 반환
	 */
	public String searchUserImagePath(int userNum) {
		String path = userDAO.selectUserImagePath(userNum);
		if (path != null) {
			path = dirPath + path;
			return path;
		}
		return null;
	}

	/**
	 * 아이디 중복 확인
	 * @param userid   - 중복확인할 아이디
	 * @return 중복 여부
	 */
	public Boolean checkId(String userid) {
		String res = userDAO.selectUserId(userid);
		if (res == null) {
			return true;
		}
		return false;
	}

	/**
	 * 이름 중복 확인
	 * @param nickName  - 중복할 이름 확인
	 * @return 중복 여부
	 */
	public Boolean checkNick(String nickName) {
		String res = userDAO.selectUserNick(nickName);
		if (res == null) {
			return true;
		}
		return false;
	}

	/**
	 * 비밀번호 확인
	 * @param userNum - 다시확인할 유저번호
	 * @param password - 다시 확인할 비밀번호
	 * @return
	 */
	public Boolean checkPass(int userNum, String password) {
		String realPassword = userDAO.selectUserPass(userNum);
		if (password.equals(realPassword)) {
			return true;
		}
		return false; 
	}

	/**
	 * 유저 프로필 사진을 로컬에 저장
	 * @param userId - 저장할 유저 아이디
	 * @param multipartFile - 저장할 유저 프로필
	 */
	private String saveUserProfile(MultipartFile multipartFile) {
		UUID uid = UUID.randomUUID();
		String fileName = uid.toString() + "_" + multipartFile.getOriginalFilename();
		String filePath = dirPath+"/user";
		File file = new File(filePath, fileName);
		String returnPath;
		try {
			multipartFile.transferTo(file);
			returnPath = "/user/" + fileName;
			return returnPath;
		} catch (IllegalStateException | IOException e) {
			LOG.info("파일 로컬 저장 실패");
			e.printStackTrace();
			return null;
		}
	}

}
