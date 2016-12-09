/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.file;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * 파일 관련 DAO
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
@Repository
public class FileDAO {

	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "sns.platform.mapper.FileMapper";

	/**
	 * 생성자
	 */
	public FileDAO() {
		// Default Constructor
	}

	/**
	 * 파일 정보 생성
	 * 
	 * @param file  - 게시물 번호, 파일 이름, 파일 타입, 파일사이즈,파일경로 포함
	 * @return 1성공
	 */
	public int insertFile(Map<String, Object> file) {
		return sqlSession.insert(namespace + ".insertFile", file);
	}

	/**
	 * 파일 정보 검색
	 * @param fileNum  - 파일 번호
	 * @return 파일 정보
	 */
	public String selectFilePath(int fileNum) {
		return sqlSession.selectOne(namespace + ".selectFilePath", fileNum);
	}

	/**
	 * 파일 목록 검색
	 * @param boardNum - 게시물 번호
	 * @return 파일 정보 리스트
	 */
	public List<Map<String, Object>> selectFiles(int boardNum) {
		return sqlSession.selectList(namespace + ".selectFiles", boardNum);
	}

	/**
	 * 파일 삭제
	 * @param fileNum - 삭제하려는 파일번호
	 * @return 1 성공 0 실패
	 */
	public int deleteFile(int fileNum) {
		return sqlSession.delete(namespace + ".deleteFile", fileNum);
	}

	/**
	 * 게시물 파일 모두 삭제
	 * @param fileNum - 게시물 번호
	 * @return 0 실패 1이상 성공
	 */
	public int deleteFilesInBoard(int boardNum) {
		return sqlSession.delete(namespace + ".deleteFilesInBoard", boardNum);
	}

	/**
	 * 해당 게시물의 지울 파일들의 실제 경로를 알아옴
	 * @param boardNum - 해당 게시물번호
	 * @return 파일 경로 리스트
	 */
	public List<String> selectDelFilesPath(int boardNum){
		return sqlSession.selectList(namespace+".selectDelFilePath", boardNum);
	}
	
	/**
	 * 해당 파일 번호안에 있는 파일들의 경로를 얻어옴
	 * @param fileNums - 해당 파일 번호 리스트
	 * @return 파일 경로 리스트
	 */
	public List<Map<String,Object>> selectDelFilesPathInList(List<Integer> fileNums) {
		return sqlSession.selectList(namespace+".selectDelFilesPathInList", fileNums);
	}
	
	/**
	 * 해당 게시물의  파일들의 타입과 번호를 알아옴
	 * @param boardNum - 해당 게시물번호
	 * @return 파일 타입과 번호
	 */
	public List<Map<String,Object>> selectDelFilesType(int boardNum){
		return sqlSession.selectList(namespace+".selectDelFilesType", boardNum);
	}

	/**
	 * 삭제할 파일들의 번호를 받아 삭제
	 * @param fileNums - 삭제할 파일 번호 리스트
	 * @return 삭제한 파일 갯수
	 */
	public int deleteFileList(List<Integer> fileNums){
		return sqlSession.delete(namespace+".deleteFilesInNumbers", fileNums);
	}
	
	
}
