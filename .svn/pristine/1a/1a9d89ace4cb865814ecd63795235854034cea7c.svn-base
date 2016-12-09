/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.comm.reslover;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * 리졸버를 통해 받는 값과 파일을 객체에 저장
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
public class CommandMap {
	Map<String,Object> map = new HashMap<>();
	MultiValueMap filesMap;
	Map<String,Object> userInfo;;
	

	/**
	 * 생성자
	 */
	public CommandMap() {
		// Default Constructor
	}

	private static final Logger LOG = LoggerFactory.getLogger(ArgumentResolver.class);
	
	public HashMap<String, Object> getMap() {
		return (HashMap<String, Object>) map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	public List<MultipartFile> getFile(String key) {
		return (List<MultipartFile>) filesMap.get(key);
	}
	
	public MultiValueMap getFilesMap() {
		return filesMap;
	}
	
	public void setFilesMap(MultiValueMap files) {
		this.filesMap = files;
	}
	
	public Object getValue(String key) {
		return map.get(key);
	}
	
	public void put(String key,Object value) {
		map.put(key, value);
	}
	
	public Object remove(String key) {
		return map.remove(key);
	}
	
	public boolean isFiles(String key) { // 해당키에 존재하는 파일리스트가 있는지 확인
		if (filesMap == null) {
			return false;
		}
		return filesMap.containsKey(key);
	}
	
	/**
	 * 파일들을 보여줌
	 */
	public void showFilesMap() { // 멀티파트인경우 파일들의 이름을 찍어줌.
		if (filesMap==null) { 
			return;
		}
		Set<String> keySet = filesMap.keySet();
		Iterator<String> keyIter = keySet.iterator();
		while(keyIter.hasNext()){
			String filesKey = keyIter.next();
			LOG.info(" -- fileKey = "+filesKey+" -- ");
			List<MultipartFile> files = (List<MultipartFile>) filesMap.get(filesKey);
			for(MultipartFile file : files){
				double doublefilesize = (double)file.getSize()/1024;
				LOG.info("fileName = "+file.getOriginalFilename()+" // fileSize = "+file.getSize() +"( "+doublefilesize+" KB)");
			}
		}
	}
	
	/**
	 * 키값에 넣음
	 */
	public void showKeyValue() {
		if(map==null){
			return;
		}
		Set<String> keySet = map.keySet();
		Iterator<String> keyIter = keySet.iterator();
		while(keyIter.hasNext()){
			String key = keyIter.next();
			LOG.info("key = "+key+" // value = "+map.get(key));
		}
	}
	
	public boolean isKey(String key) {
		if(key == null){
			return false;
		}
		return map.containsKey(key);
	}
	
	/**
	 * 세션 설정
	 * @param session 받는 세션
	 */
	public void setSession(HttpSession session) {
		userInfo = (Map<String, Object>) session.getAttribute("userInfo");
	}
	
	
	/**
	 * 세션의 값을 불러옴
	 * @param key - 받아올 값의 키
	 * @return 
	 */
	public Object getSessionValue(String key) {
		if (userInfo != null) {
			return userInfo.get(key);
		}
		return null;
	}
	
	/**
	 * @param key 세션정보에서 얻어옴
	 */
	public void sessionToMap(String key) {
		if(userInfo.containsKey(key)) {
			map.put(key, userInfo.get(key));
		}
	}
	
	public void showSessionUser() {
		LOG.info("command map in Session User");
		if(userInfo==null){
			return;
		}
		Set<String> keySet = userInfo.keySet();
		Iterator<String> keyIter = keySet.iterator();
		while(keyIter.hasNext()){
			String key = keyIter.next();
			LOG.info("key = "+key+" // value = "+userInfo.get(key));
		}
	}
	

}

