/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sns.platform.biz.file.FileDAO;
import sns.platform.comm.push.service.PushService;

/**
 * <pre>
 * 게시물 관련 내부 로직 처리
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
@Service
public class BoardService {

	private static final Logger LOG = LoggerFactory.getLogger(BoardService.class);
	
	@Value("#{properties['ipaddress']}")
	private String ipAddress;
	@Value("#{properties['file.dirpath']}")
	private String dirPath;

	/**
	 * 생성자
	 */
	public BoardService() {
		// Default Constructor
	}

	@Inject
	private BoardDAO boardDAO;
	@Inject
	private FileDAO fileDAO;
	@Autowired
	private PushService pushService;//push 알림 서비스 인젝션

	/**
	 * 파일없이 게시물 생성
	 * @param map - 게시물 내용 및 유저번호 포함
	 * @return 성공여부
	 */
	public boolean createBoard(Map<String, Object> map) {
		LOG.info("board Content = " + map.get("boardContent"));
		int insertNum = boardDAO.insertBoardNoFile(map);
		if (insertNum > 0) {
			//TODO:푸시발송
			
			try{
				pushService.pushForWriteBoard(insertNum);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			return true;
		}
		return false;
	}

	/**
	 * 파일포함 게시물 생성
	 * @param map - 게시물 내용,유저번호 포함
	 * @param files - 업로드할 파일들
	 * @return 성공여부
	 */
	public boolean createBoard(Map<String, Object> map, List<MultipartFile> files) {
		LOG.info("====> 게시물 생성 - 진입 ");

		// 폴더가 없으면 만들어둠
		SimpleDateFormat formatterDay = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date();
		String dDay = formatterDay.format(currentTime);
		String newDirPath = dirPath + "/board/" + dDay;
		File dateDir = new File(newDirPath);
		if (!dateDir.exists()) {
			dateDir.mkdirs();
		}

		// 파일을 로컬에 저장
		List<String> filePaths = new ArrayList<>();
		String filePath = "";
		for (MultipartFile mFile : files) {
			filePath = saveUploadFile(mFile, dDay);
			if (filePath == null) {
				return false;
			} else {
				filePaths.add(filePath);
			}
		}

		// 보드 DB 생성
		boardDAO.insertBoard(map);
		LOG.info("====> 게시물 생성 - 게시물 DB 입력성공 " + map.get("insertNum"));
		int inputBoardNum = (int) map.get("insertNum");
		if (inputBoardNum < 0) {
			return false;
		}

		// 파일 DB 생성, 동시에 파일 타입 카운트
		int imgCnt = 0;
		int audCnt = 0;
		int vidCnt = 0;
		String fileType;
		int successFileNum = 0;
		int boardFirstFileNum = 0;

		Map<String, Object> file;
		for (int i = 0; i < files.size(); i++) {
			file = new HashMap<>();
			file.put("fileName", files.get(i).getOriginalFilename());
			fileType = files.get(i).getContentType();
			file.put("fileType", fileType);
			file.put("fileSize", files.get(i).getSize() / 1024.0);
			file.put("filePath", filePaths.get(i));
			file.put("boardNum", inputBoardNum);
			int suc = fileDAO.insertFile(file);
			successFileNum += suc;
			if (fileType.startsWith("image")) {
				imgCnt++;
				if (imgCnt == 1) {
					LOG.info("====> 게시물 생성 - 첫이미지 파일번호 : " + file.get("insertFileNum"));
					boardFirstFileNum = (int) file.get("insertFileNum");
				}
			} else if (fileType.startsWith("audio")) {
				audCnt++;
			} else if (fileType.startsWith("video")) {
				vidCnt++;
			}
		}

		// 성공한 숫자와 파일 숫자가 맞지 않으면 실패
		if (successFileNum != files.size()) {
			return false;
		}

		// 파일 카운트 및 첫번째 이미지 카운트 업데이트
		Map<String, Object> fileCnt = new HashMap<>();
		fileCnt.put("boardImgCnt", imgCnt);
		fileCnt.put("boardAudCnt", audCnt);
		fileCnt.put("boardVidCnt", vidCnt);
		fileCnt.put("userNum", map.get("userNum"));
		LOG.info("22" + imgCnt + "/" + audCnt + "/" + vidCnt);
		if (boardFirstFileNum > 0) {
			fileCnt.put("boardFirstFileNum", boardFirstFileNum);
		}
		fileCnt.put("boardNum", inputBoardNum);
		int updateSuccess = boardDAO.updateBoard(fileCnt);
		LOG.info("====> 게시물 생성 - 게시물 카운트 업데이트 " + updateSuccess);
		if (updateSuccess < 0) {
			return false;
		}
		
		//TODO:푸시발송
		
		try{
			pushService.pushForWriteBoard(inputBoardNum);
		}catch(Exception ex){
			ex.printStackTrace();
		}

		return true;

	}

	/**
	 * 게시물 상세 검색
	 * @param boardNum - 검색할 게시물 번호
	 * @return 게시물 정보 
	 */
	public Map<String, Object> searchDetailBoard(int boardNum) {
		LOG.info("====> 게시물 조회 - 상세조회 진입");
		Map<String, Object> res = boardDAO.selectDetailBoard(boardNum);
		if (res != null) {
			return res;
		}
		return null;
	}
	

	/**
	 * 게시물 검색
	 * @param input 검색 할 게시물 정보
	 * @return 게시물 리스트
	 */
	public List<Map<String, Object>> searchBoards(Map<String, Object> input) {
		String searchType = (String) input.get("searchType");
		LOG.info("====> 게시물 조회 - 목록 검색 진입 pageNum : "+input.get("pageNum")+" / LastboardNum :"+input.get("lastBoardNum"));

		// 검색어가 있는경우
		if (searchType != null) {
			String keyword = (String) input.get("keyWord");
			LOG.info("====> 게시물 조회 - 검색어  searchType : " + searchType + " / KeyWord = " + keyword);
			if (searchType.endsWith("date")) {
				String startDay = keyword + "000000";
				String endDay = keyword + "235959";
				input.put("startDay", startDay);
				input.put("endDay", endDay);
			}
		}
		
		//게시물 목록 URL을 만들어서 넣어줌
		List<Map<String, Object>> boards;
		boards = boardDAO.selectBoards(input);
		if (boards != null) {
			LOG.info("====> 게시물 조회 - 이미지URL 작성");
			String firstImageUrl;
			for (Map<String, Object> board : boards) {
				if(board.get("boardImgNum")!=null){
					firstImageUrl = ipAddress+ "/board/" + board.get("boardNum") + "/file/"
							+ board.get("boardImgNum");
					board.put("firstImageUrl", firstImageUrl);
					board.remove("boardImgNum");
				}
			}
			return boards;
		}
		return null;
	}

	/**
	 * 게시물 수정
	 * @param boardNum - 수정할 게시물 번호
	 * @param map - 수정할 게시물 정보
	 * @param delFiles - 삭제할 게시물 번호 리스트
	 * @param files - 추가할 파일
	 * @return 성공여부 반환
	 */
	public boolean modifyBoard(int boardNum, Map<String, Object> map, List<Integer> delFiles,
			List<MultipartFile> files) {

		// 폴더가 없으면 만들어둠
		SimpleDateFormat formatterDay = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date currentTime = new Date();
		String dDay = formatterDay.format(currentTime);
		String newDirPath = dirPath + "/board/" + dDay;
		File dateDir = new File(newDirPath);
		if (!dateDir.exists()) {
			dateDir.mkdirs();
			LOG.info("====> 게시물 수정 - 폴더 생성 :"+dateDir);
		}

		// 멀티파트 파일이 있으면 파일을 로컬에 저장
		if(files != null) {
			LOG.info("====> 게시물 수정 - 올릴 파일 로컬 저장");
			int fileUploadSuccess = 0;
			List<String> filePaths = new ArrayList<>();
			String filePath = null; // null 공백으로잡지말자
			for (MultipartFile mFile : files) {
				filePath = saveUploadFile(mFile, dDay);
				if (filePath != null) {
					filePaths.add(filePath);
					fileUploadSuccess++;// 의미가없음
				} else {
					return false;
				}
			}
			// 파일 DB 생성
			int successFileNum=0;
			if(fileUploadSuccess == files.size()){ // 의미없는 검사
				LOG.info("====> 게시물 수정 - 올릴 파일 DB 생성");
				Map<String, Object> file;
				for (int i = 0; i < files.size(); i++) {
					file = new HashMap<>();
					file.put("fileName", files.get(i).getOriginalFilename());
					file.put("fileType", files.get(i).getContentType());
					file.put("fileSize", files.get(i).getSize() / 1024.0);
					file.put("filePath", filePaths.get(i));
					file.put("boardNum", boardNum);
					int suc = fileDAO.insertFile(file);
					successFileNum += suc;
				}
			}
			if(fileUploadSuccess != successFileNum){
				return false;
			} // 카운트를 세는게 위험 할 수 있음.
		}
		
		
		// 지울 파일 경로 얻어옴
		List<Map<String,Object>> delFilePaths = new ArrayList<>();
		if (delFiles != null && !delFiles.isEmpty()) {
			LOG.info("====> 게시물 수정 - 지울 파일 경로 얻어옴 지울 파일 갯수 :"+delFilePaths);
			delFilePaths = fileDAO.selectDelFilesPathInList(delFiles);

			// 지울 파일 DB 제거
			int delFileSuc = fileDAO.deleteFileList(delFiles);
			LOG.info("====> 게시물 수정 - 파일 DB 삭제 ");
			if (delFileSuc != delFilePaths.size()) {
				return false;
			}// 위험함
		}

		// 해당 게시물의 파일 번호 타입을 다시 읽어와서 업데이트
		List<Map<String, Object>> typelist = fileDAO.selectDelFilesType(boardNum);
		int imgCnt = 0;
		int audCnt = 0;
		int vidCnt = 0;
		int boardImageNum = 0;
		String type;
		for (Map<String, Object> typeMap : typelist) {
			type = (String) typeMap.get("fileType");
			if (type.startsWith("image")) {
				imgCnt++;
				if (imgCnt == 1) {
					boardImageNum = (int) typeMap.get("fileNum");
					LOG.info("====> 게시물 수정 - 첫 이미지 파일 수정 : "+boardImageNum);
				}
			} else if (type.startsWith("audio")) {
				audCnt++;
			} else if (type.startsWith("video")) {
				vidCnt++;
			}
		}
		
		
		Map<String, Object> fileCnt = new HashMap<>();
		fileCnt.put("boardImgCnt", imgCnt);
		fileCnt.put("boardAudCnt", audCnt);
		fileCnt.put("boardVidCnt", vidCnt);
		fileCnt.put("boardContent", map.get("boardContent"));
		fileCnt.put("userNum", map.get("userNum"));
		LOG.info("====> 게시물 수정 - 계산된 게시물 카운트 :" + imgCnt + " / " + audCnt + " / " + vidCnt);
		if (boardImageNum > 0) {
			fileCnt.put("boardFirstFileNum", boardImageNum);
		}
		fileCnt.put("boardNum", boardNum);
		
		int updateSuccess = boardDAO.updateBoard(fileCnt);
		LOG.info("====> 게시물 수정 - 업뎃성공" + updateSuccess); // debug레벨
		if (updateSuccess < 0) {
			return false;
		}

		// 실제 로컬 파일 제거
		if( !delFilePaths.isEmpty()) {
			for (Map<String,Object> path : delFilePaths) {
				String filePath = dirPath+(String)path.get("filePath");
				File del = new File(filePath);
				if (!del.delete()) {
					LOG.info("====> 게시물 수정 - 파일삭제실패 : " + path);
				}
			}
		}
		return true;
	}

	/**
	 * 파일 수정 없이 게시물 내용 만 수정
	 * @param boardNum - 수정할 게시물 번호
	 * @param map - 수정할 게시물 내용
	 * @return
	 */
	public boolean modifyBoard(int boardNum, Map<String, Object> map) {
		LOG.info("====> 게시물 수정(내용만) -  boardNum = " + boardNum);
		map.put("boardNum", boardNum);
		int suc = boardDAO.updateBoardContent(map);
		if(suc >0){
			return true;
		}
		return false;
	}

	/**
	 * 게시물 삭제
	 * @param board - 삭제할 게시물
	 * @return 성공여부반환
	 */
	public boolean removeBoard(Map<String,Object> board) {
		int boardNum = (int) board.get("boardNum");
		LOG.info("====> 게시물 삭제 - 진입  boardNum = " + boardNum);
		List<String> delFiles;

		// 지울 파일 목록 얻어옴
		delFiles = fileDAO.selectDelFilesPath(boardNum);
		if(delFiles.size()>0){
			// 파일 DB 제거
			int del = fileDAO.deleteFilesInBoard(boardNum);
			if (delFiles.size() != del) {
				return false;
			}
		}
		

		// 게시물 DB 및 댓글 DB 삭제
		int boardDelsuccess = 0;
		boardDelsuccess += boardDAO.deleteBoard(board);

		if (boardDelsuccess > 0) {
			// 로컬 파일 삭제 -> 삭제되지않아도 롤백하지 않음.
			for (String path : delFiles) {
				File del = new File(path);
				if (!del.delete()) {
					LOG.info("====> 게시물 삭제 -파일삭제실패 : " + path);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 파일을 실제 로컬에 저장
	 * @param multipartFile - 실제 저장할 파일
	 * @param dDay - 오늘 날짜
	 * @return - 데이터베이스에 저장될 파일경로
	 */
	private String saveUploadFile(MultipartFile multipartFile, String dDay) {

		UUID uid = UUID.randomUUID();
		String fileName = uid.toString() + "_" + multipartFile.getOriginalFilename();
		String newDirPath = dirPath + "/board/" + dDay;
		String returnPath;
		File file = new File(newDirPath, fileName);

		try {
			multipartFile.transferTo(file);
			returnPath = "/board/" + dDay + "/" + fileName;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			returnPath = null;
		}
		return returnPath;
	}

}
