/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.reply;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;



/**
 * <pre>
 * 댓글 관련 DAO
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
@Repository
public class ReplyDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace = "sns.platform.mapper.ReplyMapper";
	
	/**
	 * 생성자
	 */
	public ReplyDAO(){
		//Default Constructor
	}
	
	/**
	 * 댓글 작성
	 * @param reply 게시물번호,유저번호,게시물 내용
	 * @return 1성공
	 */
	public int insertReply(Map<String,Object> reply){
			return sqlSession.insert(namespace+".insertReply",reply);
	} 
	
	
	/**
	 * 댓글 목록 검색
	 * @param reply 게시물번호,받을 댓글 갯수, 마지막 댓글번호
	 * @return 게시물의 댓글리스트
	 */
	public List<Map<String,Object>> selectReplys(Map<String,Object> reply){
		return sqlSession.selectList(namespace+".selectReplys", reply);
	}
	

	
	/**
	 * 댓글 수정
	 * @param reply 수정할 댓글 내용, 수정하려는 댓글 번호
	 * @return 1 성공 0 실패
	 */
	public int updateReply(Map<String,Object> reply){
		return sqlSession.update(namespace+".updateReply", reply);
	}
	
	/**
	 * 댓글 삭제
	 * @param replyNum 삭제하려는 댓글번호
	 * @return 1 성공 0 실패
	 */
	public int deleteReply(Map<String,Object> reply) {
		return sqlSession.delete(namespace+".deleteReply", reply);
	}
	
	/**
	 * 게시물 번호로된 댓글 다 삭제
	 * @param boardNum 게시물번호
	 * @return 삭제한 댓글 수
	 */
	public int deleteReplysByBoardNum(int boardNum) {
		return sqlSession.delete(namespace+".deleteReplysByBoardNum", boardNum);
	}
	
	/**
	 * 유저번호를 통해 댓글삭제
	 * @param userNum 유저 번호
	 * @return 삭제한 댓글 수
	 */
	public int deleteReplysByUserNum(int userNum) {
		return sqlSession.delete(namespace+".deleteReplysByUserNum", userNum);
	}
	
}
