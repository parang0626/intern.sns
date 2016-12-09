/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.reply;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sns.platform.comm.reslover.CommandMap;
import sns.platform.comm.util.ResponseMap;

/**
 * <pre>
 * 댓글 관련 처리 Controller
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */

@Controller
public class ReplyController {
	
	@Inject
	private ReplyService replyService;

	/**
	 * 생성자
	 */
	public ReplyController() {
		//Default Constructor
	};

	/**
	 * 댓글 등록
	 * @param map 등록할 댓글 내용, 작성자 유저번호 포함
	 * @param boardNum 등록할 댓글 해당 게시물번호
	 * @return jsonview 형태로 결과 반환
	 */
	@RequestMapping(value = "/board/{boardNum}/reply", method = RequestMethod.POST)
	public ModelAndView createReply(CommandMap map, @PathVariable int boardNum) {
		ResponseMap res;
		if (boardNum > 0) {
			if (map.isKey("replyContent")) {
				String replyContent = (String) map.getValue("replyContent");
				int userNum = (int) map.getSessionValue("userNum");
				boolean success = replyService.createReply(boardNum, userNum, replyContent);
				if (success) {
					res = new ResponseMap(1, "댓글 작성 성공");
					return new ModelAndView("jsonView", res);
				}
			}
		}
		res = new ResponseMap(0, "댓글 작성 실패");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 댓글목록 조회
	 * @param map 댓글 갯수,마지막 댓글 번호 포함
	 * @param boardNum 댓글 목록을 불러올 게시물 번호
	 * @return jsonview 형태로 결과 반환
	 */
	@RequestMapping(value = "/board/{boardNum}/replys", method = RequestMethod.GET)
	public ModelAndView searchReplys(CommandMap map, @PathVariable int boardNum) {
		ResponseMap res;
		if(map == null){
			map = new CommandMap();
		}
		if (boardNum > 0) {
				int pageNum = NumberUtils.toInt((String)map.getValue("pageNum"),5);
				int lastReplyNum =NumberUtils.toInt((String) map.getValue("lastReplyNum"),-1); 
				List<Map<String, Object>> replyList = replyService.searchReplyList(boardNum, pageNum, lastReplyNum);
				if (replyList != null && replyList.size() > 0) {
					res = new ResponseMap(1, "댓글 목록 불러오기 성공", replyList);
					return new ModelAndView("jsonView", res);
				}
		}
		res = new ResponseMap(0, "댓글 목록 불러오기 실패");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 댓글 수정
	 * @param map 수정할 댓글 내용, 작성자 번호 포함
	 * @param boardNum 댓글이 포함된 게시물
	 * @param replyNum 수정할 댓글 번호
	 * @return jsonview 형태로 결과 반환
	 */
	@RequestMapping(value = "/board/{boardNum}/reply/{replyNum}", method = RequestMethod.POST)
	public ModelAndView modifyReply(CommandMap map, @PathVariable int boardNum, @PathVariable int replyNum) {
		ResponseMap res;
		if (boardNum > 0 && replyNum > 0) {
			if (map.isKey("replyContent")) {
				String replyContent = (String) map.getValue("replyContent");
				int userNum = (int) map.getSessionValue("userNum");
				boolean success = replyService.modifyReply(boardNum, replyNum, userNum, replyContent);
				if (success) {
					res = new ResponseMap(1, "댓글 수정 성공");
					return new ModelAndView("jsonView", res);
				}
			}
		}
		res = new ResponseMap(0, "댓글 수정 실패");
		return new ModelAndView("jsonView", res);
	}

	/**
	 * 댓글 삭제
	 * @param map 작성자 유저번호가 포함
	 * @param boardNum 댓글이 있는 게시물 번호
	 * @param replyNum 삭제할 댓글 번호
	 * @return jsonview 형태로 결과 반환
	 */
	@RequestMapping(value = "/board/{boardNum}/reply/{replyNum}", method = RequestMethod.DELETE)
	public ModelAndView removeReply(CommandMap map, @PathVariable int boardNum, @PathVariable int replyNum) {
		ResponseMap res;
		if (boardNum > 0 && replyNum > 0) {
			int userNum = (int) map.getSessionValue("userNum");
			boolean success = replyService.removeReply(boardNum,replyNum, userNum);
			if (success) {
				res = new ResponseMap(1, "댓글 삭제 성공");
				return new ModelAndView("jsonView", res);
			}
		}
		res = new ResponseMap(0, "댓글 삭제 실패");
		return new ModelAndView("jsonView", res);
	}
}
