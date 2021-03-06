/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.biz.file;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sns.platform.comm.reslover.CommandMap;
import sns.platform.comm.util.ResponseMap;

/**
 * <pre>
 * 파일 관련 처리 Controller
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */




@Controller
public class FileController {
	private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
	@Inject
	private FileService fileService;
	
	@Value("#{properties['file.dirpath']}")
	private String dirPath;
	

	/**
	 * 생성자
	 */
	public FileController() {
		// Default Constructor
	}

	/**
	 * 게시물에 포함된 파일 리스트 목록
	 * @param boardNum - 해당 게시물 번호 
	 * @return 파일정보 리스트 
	 */
	@RequestMapping(value = "/board/{boardNum}/files", method = RequestMethod.GET)
	public ModelAndView searchFileList(@PathVariable int boardNum) {
		ResponseMap res;
		if (boardNum > 0) {
			List<Map<String, Object>> fileList = fileService.searchFileList(boardNum);
			if (fileList != null) {
				LOG.info("2");
				res = new ResponseMap(1,"파일리스트 검색 성공",fileList);
				return new ModelAndView("jsonView",res);
			}
		}
		res = new ResponseMap(0,"파일리스트 검색 실패");
		return new ModelAndView("jsonView",res);
	}

	/**
	 * 게시물 파일 다운로드 
	 * @param boardNum - 해당 게시물 번호
	 * @param fileNum - 해당 파일 번호
	 * @return Filedownload 형태로 반환
	 */
	@RequestMapping(value = "/board/{boardNum}/file/{fileNum}", method = RequestMethod.GET)
	public ModelAndView fileDown(CommandMap map, @PathVariable int boardNum, @PathVariable int fileNum) {
		if (boardNum > 0 && fileNum > 0) {
				String filePath = dirPath+fileService.serachFilePath(fileNum);
				LOG.info("properties 확인 " + filePath);
				if (filePath != null) {
					File downloadFile = new File(filePath);
					return new ModelAndView("download", "downloadFile", downloadFile);
				}
		}
		return null;
	}
	
	

}
