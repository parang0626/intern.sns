/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.user;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;



/**
 * <pre>
 * 유저 관련 데이터베이스 연결
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
@Repository
public class UserDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace = "sns.platform.mapper.UserMapper";

	/**
	 * 생성자
	 */
	public UserDAO(){
		//Default Constructor
	}
	
	/**
	 * 로그인 처리
	 * @param userId 검색하려는 유저 아이디
	 * @return 검색유저 정보
	 */
	public Map<String,Object> login(Map<String,Object> user){
		return sqlSession.selectOne(namespace+".loginUser",user);
	}
	
	
	/**
	 * 회원 가입
	 * @param user 회원가입 정보
	 * @return 성공시 1
	 */
	public int insertUser(Map<String,Object> user){
			return sqlSession.insert(namespace+".insertUser",user);
	} // 중복되는 아이디나 이름이 입력됬을경우 j ava.sql.BatchUpdateException 에러가 발생
	
	
	/**
	 * 유저 검색
	 * @param userNum 검색할 유저번호 
	 * @return 검색한 유저 정보
	 */
	public Map<String,Object> selectUser(int userNum){
		return sqlSession.selectOne(namespace+".selectUser", userNum);
	}
	
	/**
	 * 유저 코멘트 수정
	 * @param user 해당유저 정보와 수정내용 포함
	 * @return 0실패 1성공
	 */
	public int updateUser(Map<String,Object> user){
		return sqlSession.update(namespace+".updateUser",user);
	}
	
	/**
	 * 유저 닉네임 수정
	 * @param user 해당유저 정보와 수정내용 포함
	 * @return 0실패 1성공
	 */
	public int updateUserNick(Map<String,Object> user){
		return sqlSession.update(namespace+".updateUserNick",user);
	}
	
	/**
	 * 유저 프로필 이미지 수정
	 * @param user 해당유저 정보와 수정내용 포함
	 * @return 0실패 1성공
	 */
	public int updateUserFile(Map<String,Object> user){
		return sqlSession.update(namespace+".updateUserFile",user);
	}
	
	/**
	 * 유저 비밀번호 수정
	 * @param user 해당유저 정보와 수정내용 포함
	 * @return 0실패 1성공
	 */
	public int updateUserPass(Map<String,Object> user){
		return sqlSession.update(namespace+".updateUserPass",user);
	}
	
	/**
	 * 아이디 중복확인을 위해 해당아이디가 있는지 확인
	 * @param userid 확인하려는 아이디
	 * @return 0없음 1있음
	 */
	public String selectUserId(String userId){
		return sqlSession.selectOne(namespace+".selectUserId", userId);
	}
	
	/**
	 * 닉네임 중복확인을 위해 해당닉네임이 있는지 확인
	 * @param nickName 확인하려는 닉네임
	 * @return 0없음 1있음
	 */
	public String selectUserNick(String nickName) {
		return sqlSession.selectOne(namespace+".selectUserNick", nickName);
	}
	
	/**
	 * 비밀번호 확인을 위해 해당 비밀번호 전달
	 * @param userNum 유저 번호
	 * @return 비밀번호 반환
	 */
	public String selectUserPass(int userNum) {
		return sqlSession.selectOne(namespace+".selectUserPass", userNum);
	}
	
	/**
	 * 유저 이미지 파일 주소 반환
	 * @param userNum 유저 번호
	 * @return 유저의 이미지 파일주소 반환
	 */
	public String selectUserImagePath(int userNum){
		return sqlSession.selectOne(namespace+".selectImagePath", userNum);
	}
	
	/**
	 * 유저 정보와 유저가 작성한 게시물 , 그 게시물에 달려있는 댓글 삭제
	 * @param userNum - 유저번호
	 * @return - 삭제한 칼럼수 반환
	 */
	public int deleteUserBoardReply(int userNum) {
		return sqlSession.delete(namespace+".deleteUserAll", userNum);
	}
	
	/**
	 * 유저가 등록한 파일들의 번호와 경로 반환
	 * @param userNum - 유저번호
	 * @return 삭제한 칼럼수 반환
	 */
	public List<Map<String,Object>> selectDelFilesList(int userNum) {
		return sqlSession.selectList(namespace+".selectDelFilesList", userNum);
	}
	
	/**
	 * pnsToken 업데이트
	 * @param map - 유저번호 
	 * @return 업데이트 성공유무
	 */
	public int updatePnsToken(Map<String,Object> map) {
		return sqlSession.update(namespace+".updatePnsToken",map);
	}
}
