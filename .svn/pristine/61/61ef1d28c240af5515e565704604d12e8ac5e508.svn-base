package sns.platform.comm.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowMapUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(ShowMapUtil.class);
	
	public static void show(Map<String,Object> map){
		LOG.info("=========ShowMapUtil Start==========");
		Set keySet = map.keySet();
		Iterator<String> it = keySet.iterator();
		String key;
		while(it.hasNext()){
			key = it.next();
			LOG.info("= "+key+" : "+map.get(key).toString());
		}
		LOG.info("=========ShowMapUtil End============");
	}

}
