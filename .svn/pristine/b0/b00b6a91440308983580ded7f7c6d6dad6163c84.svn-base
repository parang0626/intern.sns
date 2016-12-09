/**
 * (주)오픈잇 |http://www.openit.co.kr
 * Copyright (c)2006-2016, openit Inc.
 * All rights reserved
 */

package sns.platform.comm.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 클라이언트에게 Json형태로 줄때 사용할 기본 포맷
 * </pre>
 * 
 * @author <a href="mailto:support@openit.co.kr">(주)오픈잇 | openit Inc.</a>
 * @version 0.1
 * @since 0.1
 * @created 2016.11.01
 * 
 */
public class ResponseMap extends HashMap<String,Object>{
	
	private static final Logger LOG = LoggerFactory.getLogger(ResponseMap.class);
	
	public ResponseMap(){
		super();
	}
	
	public ResponseMap(int code,String message){
		put("code",code);
		put("message",message);
	}
	
	public ResponseMap(int code,String message,Object data){
		put("code",code);
		put("message",message);
		put("data",data);
	}
	
	/**
	 * 키벨류를 로그로 표시
	 */
	public void logKeyValue(){
		LOG.info(" <---- ResponseMap KeyValue ---->");
		if(isEmpty()){
			LOG.info("ResponseMap is empty");
			return;
		}
		Set keySet = keySet();
		Iterator il = keySet.iterator();
		String key;
		while(il.hasNext()){
			key = (String) il.next();
			LOG.info("Key : "+key+" // Value : "+get(key));
		}
		LOG.info(" <----------------->");
	}

}
