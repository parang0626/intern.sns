/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sns.platform.biz.board.BoardDAO;
import sns.platform.comm.push.service.PushService;

/**
 * <pre>
 * 댓글 관련 내부로직 처리
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
@Service
public class ReplyService {

	@Inject
	ReplyDAO replyDAO;
	@Inject
	BoardDAO boardDAO;
	@Autowired
	private PushService pushService;

	private static final Logger LOG = LoggerFactory.getLogger(ReplyService.class);

	/**
	 * 생성자
	 */
	public ReplyService(){
		// Default Constructor
	}

	
	/**
	 * @param boardNum 댓글을 등록할 게시물 번호
	 * @param userNum 댓글 작성자 번호
	 * @param replyContent 등록할 댓글 내용
	 * @return
	 * @throws Exception 
	 */
	public boolean createReply(int boardNum,int userNum,String replyContent){
		LOG.info("====> 댓글 등록 - boardNum = "+boardNum +" 댓글 내용: "+replyContent);
		Map<String,Object> reply = new HashMap<>();
		reply.put("boardNum",boardNum);
		reply.put("userNum",userNum);
		reply.put("replyContent",replyContent);
		int res = replyDAO.insertReply(reply);
		if (res > 0) {
			int suc = boardDAO.updateReplyCountPlus(boardNum);
			if(suc>0){
				
				try{
					pushService.pushForWriteReply(boardNum);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				return true;
			}	
		}
		return false;
	}

	
	/**
	 * 댓글 목록 
	 * @param boardNum 댓글 목록 받아올 게시물 번호
	 * @param pageNum 댓글 갯수
	 * @param lastReplyNum 마지막 댓글 번호
	 * @return
	 */
	public List<Map<String, Object>> searchReplyList(int boardNum,int pageNum,int lastReplyNum) {
		LOG.info("====> 댓글 목록 - boardNum = " + boardNum + " pageNum = " + pageNum + " lastReplyNum = " + lastReplyNum);
		
		Map<String,Object> reply = new HashMap<>();
		reply.put("boardNum",boardNum);
		reply.put("pageNum",pageNum);
		reply.put("lastReplyNum",lastReplyNum);
		
		List<Map<String, Object>> res = replyDAO.selectReplys(reply);
		if (res != null || res.size()>0){
			return res;
		}
		return null;
	}

	/**
	 * 댓글 수정 
	 * @param boardNum 댓글이 있는 게시물 번호
	 * @param replyNum 댓글 번호
	 * @param userNum 작성자 번호
	 * @param replyContent 수정할 댓글 내용
	 * @return 성공 실패
	 */
	public boolean modifyReply(int boardNum,int replyNum,int userNum,String replyContent) {
		LOG.info("====> 댓글 수정 - boardNum = " + boardNum + " replyNum = " + replyNum);
		Map<String,Object> reply = new HashMap<>();
		reply.put("replyNum", replyNum);
		reply.put("replyContent", replyContent);
		reply.put("userNum", userNum);
		int res = replyDAO.updateReply(reply);
		if (res > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 댓글 삭제
	 * @param replyNum 삭제할 댓글 번호
	 * @param userNum 작성자 번호
	 * @return
	 */
	public boolean removeReply(int boardNum,int replyNum,int userNum) {
		LOG.info("====> 댓글 삭제 - replyNum = " + replyNum);
		Map<String, Object> reply = new HashMap<>();
		reply.put("replyNum", replyNum);
		reply.put("userNum", userNum);
		int res = replyDAO.deleteReply(reply);
		if (res > 0) {
			int suc = boardDAO.updateReplyCountMinus(boardNum);
			if (suc > 0) {
				return true;
			}
		}
		return false;
	}
}
