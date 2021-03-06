/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sns.platform.comm.reslover.CommandMap;
import sns.platform.comm.util.ResponseMap;

/**
 * <pre>
 * 게시물 관련 처리 Controller
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */

@Controller
public class BoardController {
	private static final Logger LOG = LoggerFactory.getLogger(BoardController.class);

	@Inject
	private BoardService boardService;

	/**
	 * 생성자
	 */
	public BoardController() {
		// Default Constructor
	
	}

	/**
	 * 게시물 생성
	 * 
	 * @param map - 생성할 게시물 내용과 파일들 포함
	 * @return Jsonview 형태로 결과 반환
	 */
	@RequestMapping(value = "/board", method = RequestMethod.POST)
	public ModelAndView createBoard(CommandMap map, @ModelAttribute CommandMap param) {
		ResponseMap res;
		if (map != null && map.isKey("boardContent")) {
			map.sessionToMap("userNum");

			// 파일이 있는 경우
			if (map.isFiles("files")) {
				boolean success = boardService.createBoard(map.getMap(), map.getFile("files"));
				if (success) {
					res = new ResponseMap(1, "게시물 등록(파일o) 성공");
					return new ModelAndView("jsonView", res);
				}

				// 파일이 없는 경우
			} else {
				boolean success = boardService.createBoard(map.getMap());
				if (success) {
					res = new ResponseMap(1, "게시물 등록(파일x) 성공");
					return new ModelAndView("jsonView", res);
				}
			}
		}
		res = new ResponseMap(0, "게시물 등록 실패");
		return new ModelAndView("jsonView", res);

	}

	/**
	 * 게시물 상세검색
	 * 
	 * @param boardNum - 검색해야하는 게시물 번호
	 * @return Jsonview 형태로 결과 반환
	 */
	@RequestMapping(value = "/board/{boardNum}", method = RequestMethod.GET)
	public ModelAndView searchDetailBoard(@PathVariable int boardNum) {
		ResponseMap res;
		if (boardNum >= 0) {
			Map<String, Object> resMap = boardService.searchDetailBoard(boardNum);
			if (resMap != null) {
				res = new ResponseMap(1, "게시물 찾기 성공", resMap);
			} else {
				res = new ResponseMap(0, "찾는 게시물 없음");
			}
		} else {
			res = new ResponseMap(0, "게시물 상세조회 실패");
		}	
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 게시물 목록검색
	 * 
	 * @param pageNum - 넘겨줄 게시물 갯수
	 * @param lastBoardNum - 마지막으로 받은 게시물 번호
	 * @param map - searchType와 keyWord를 포함
	 * @return Jsonview 형태로 결과 반환
	 * 
	 */
	@RequestMapping(value = "/boards", method = RequestMethod.GET)
	public ModelAndView searchBoard(CommandMap map) {
		ResponseMap res;
		List<Map<String, Object>> boards;
		if (map == null) {
			map = new CommandMap();
		}

		int userNum = NumberUtils.toInt((String) map.getValue("userNum"), 0);
		int pageNum = NumberUtils.toInt((String) map.getValue("pageNum"), 5);
		int lastBoardNum = NumberUtils.toInt((String) map.getValue("lastBoardNum"), -1);
		LOG.info(userNum + "");
		map.put("userNum", userNum);
		map.put("pageNum", pageNum);
		map.put("lastBoardNum", lastBoardNum);
		boards = boardService.searchBoards(map.getMap());

		if (boards != null && boards.size() > 0) {
			res = new ResponseMap(1, "게시물 검색 성공", boards);
			return new ModelAndView("jsonView", res);
		}
		res = new ResponseMap(1, "게시물 검색값이 없습니다.", boards);
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 게시물 수정
	 * 
	 * @param map - 게시물에 관한 정보 포함
	 * @param boardNum - 수정해야 하는 게시물 번호
	 * @return Jsonview 형태로 결과 반환
	 */
	@RequestMapping(value = "/board/{boardNum}", method = RequestMethod.POST)
	public ModelAndView updateBoard(CommandMap map, @PathVariable int boardNum) {
		ResponseMap res;
		boolean success = false;
		if (map != null && boardNum > 0) {
			map.sessionToMap("userNum");

			// 지울 파일목록이 있는지 키값이 없으면 null값 삽입
			List<Integer> delFiles = new ArrayList<>();
			if (map.isKey("delFiles") && map.getValue("delFiles") != null) {
				if (map.getValue("delFiles").getClass().equals("".getClass())) {
					LOG.info(" 게시물 수정 - 지우는 파일을 배열로 받아야함");
					delFiles.add(NumberUtils.toInt((String) map.getValue("delFiles")));
				} else {

					// 받은 배열을 인트형으로 바꿔 전달
					String[] delfilesArray = (String[]) map.getValue("delFiles");

					for (String del : delfilesArray) {
						delFiles.add(NumberUtils.toInt(del,0));
					}
				}
			} else {
				delFiles = null;
			}

			// 올릴 파일들이 있지 않으면 null삽입
			List<MultipartFile> files;
			if (map.isFiles("files") && map.getFile("files") != null) {
				files = map.getFile("files");
			} else {
				files = null;
			}

//			 둘다 없을경우 게시물 내용만 업데이트
			 if (files == null && delFiles == null) {
			 success = boardService.modifyBoard(boardNum, map.getMap());
			 } else {
			 success = boardService.modifyBoard(boardNum, map.getMap(),
			 delFiles, files);
			 }

			if (success) {
				res = new ResponseMap(1, "게시물 수정 성공");
				return new ModelAndView("jsonView", res);
			}
		}
		res = new ResponseMap(0, "게시물 수정 실패");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 게시물 삭제
	 * 
	 * @param map - 자신의 유저번호 포함
	 * @param boardNum- 삭제할 게시물 번호
	 * @return Jsonview 형태로 결과 반환
	 */
	@RequestMapping(value = "/board/{boardNum}", method = RequestMethod.DELETE)
	public ModelAndView removeBoard(CommandMap map, @PathVariable int boardNum) {
		ResponseMap res;
		if (boardNum > 0) {
			map.put("boardNum", boardNum);
			map.sessionToMap("userNum");
			boolean success = boardService.removeBoard(map.getMap());
			if (success) {
				res = new ResponseMap(1, "게시물 삭제 성공");
				return new ModelAndView("jsonView", res);
			}
		}
		res = new ResponseMap(0, "게시물 삭제 실패");
		return new ModelAndView("jsonView", res);
	}
	
	

}
