/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.file;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



/**
 * <pre>
 * 파일 관련 내부로직 처리
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
@Service
public class FileService {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileService.class);
	
	@Inject
	FileDAO fileDAO;
	
	@Value("#{properties['ipaddress']}")
	private String ipAddress;
	

	/**
	 * 생성자
	 */
	public FileService() {
		// Default Constructor
	}

	/**
	 * 해당 게시물의 파일리스트 조회
	 * @param boardNum - 게시물 번호
	 * @return 파일정보 리스트
	 */
	public List<Map<String,Object>> searchFileList(int boardNum) {
		LOG.info("====> 파일 리스트 목록 - 게시물번호 = " + boardNum );
		List<Map<String, Object>> files = fileDAO.selectFiles(boardNum);
		if(files != null && files.size() > 0) {
			for(Map<String,Object> file : files){
				String fileUrl = ipAddress+"/board/"+boardNum+"/file/"+file.get("fileNum");
				file.put("fileUrl",fileUrl);
				file.remove("filePath");
			}
		}
		return files;		
	}

	/**
	 * 파일 경로 검색 - 파일 다운로드뷰 사용을 위해 필요
	 * @param fileNum - 파일 번호
	 * @return 파일 경로
	 */
	public String serachFilePath(int fileNum) {
		LOG.info("====> 파일 패스 목록 - - 파일번호  = " + fileNum);
		String filePath = fileDAO.selectFilePath(fileNum);
		if(filePath != null) {
			return filePath;
		}
		return null;
	}

}
