/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.board;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * 게시물 관련 데이터베이스와 연결
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
@Repository
public class BoardDAO {

	/**
	 * 생성자
	 */
	public BoardDAO() {
		// Default Constructor
	}

	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "sns.platform.mapper.boardMapper";

	/**
	 * 파일 없이 게시물 생성
	 * @param map - 게시물에 관한 정보
	 * @return 1성공
	 */
	public int insertBoardNoFile(Map<String, Object> map) {
		return sqlSession.insert(namespace + ".insertBoardNoFile", map);
	}

	/**
	 * 파일 포함 게시물 생성
	 * @param map - 게시물에 관한 정보와 파일 포함
	 * @return 1성공
	 */
	public int insertBoard(Map<String, Object> map) {
		int insertNum = sqlSession.insert(namespace + ".insertBoard", map);
		return insertNum;
	}

	
	/**
	 * 게시물 상세 보기
	 * @param boardNum - 상세보기할 게시물 번호
	 * @return 0실패 1성공
	 */
	public Map<String, Object> selectDetailBoard(int boardNum) {
		return sqlSession.selectOne(namespace + ".selectDetailBoard", boardNum);
	}

	
	
	/**
	 * 게시물 목록 보기
	 * @param map - 페이징 관련값 포함
	 * @return 게시물 정보 리스트
	 */
	public List<Map<String,Object>> selectBoardsNoKeyword(Map<String,Object> map) {
		return sqlSession.selectList(namespace + ".selectBoardsNoKeyword", map);
	}
	
	/**
	 * 작성일로 게시물 검색
	 * @param map - 페이징 관련값과 검색키워드 포함
	 * @return 게시물 정보 리스트
	 */
	public List<Map<String,Object>> selectBoards(Map<String,Object> map) {
		return sqlSession.selectList(namespace + ".selectBoards", map);
	}
	
	
	/**
	 * 게시물의 각 파일별 카운트와 첫번째 이미지 파일번호를 업데이트, 내용이 있을경우 내용 수정
	 * @param 각 파일 번호 및 첫번째 이미지 파일번호 
	 * @return 1성공 0실패
	 */
	public int updateBoard(Map<String,Object> board) {
		return sqlSession.update(namespace + ".updateBoard",board);
	}
	
	/**
	 * 게시물 내용만 수정
	 * @param board - 수정할 게시물 내용
	 * @return 1성공 0실패
	 */
	public int updateBoardContent(Map<String,Object> board) {
		return sqlSession.update(namespace + ".updateBoardContent",board);
	}
	
	/**
	 * 댓글 갯수 +1
	 * @param boardNum 해당 게시물 번호
	 * @return 0실패 1성공
	 */
	public int updateReplyCountPlus(int boardNum) {
		return sqlSession.update(namespace + ".updateReplyNumPlus",boardNum);
	}
	
	/**
	 * 댓글 갯수 -1
	 * @param boardNum 해당 게시물 번호
	 * @return 0실패 1성공
	 */
	public int updateReplyCountMinus(int boardNum) {
		return sqlSession.update(namespace + ".updateReplyNumMinus",boardNum);
	}
	
	
	/**
	 * 게시물 삭제
	 * @param boardNum - 삭제 할 게시물 번호
	 * @return 1성공 0실패
	 */
	public int deleteBoard(Map<String,Object> board) {
		return sqlSession.delete(namespace + ".deleteBoard", board);
	}

}
