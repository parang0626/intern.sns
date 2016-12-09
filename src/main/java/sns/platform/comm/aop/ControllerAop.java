package sns.platform.comm.aop;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sns.platform.comm.reslover.CommandMap;

public class ControllerAop {
	private static final Logger LOG = LoggerFactory.getLogger(ControllerAop.class);

	public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
		String signatureString = joinPoint.getSignature().toShortString();

		LOG.info("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  " + signatureString + " AOP InputValues 시작 ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");

		Object args[] = joinPoint.getArgs();
		for (Object param : args) {
			if (param.getClass().equals(CommandMap.class)) {
				LOG.info("<---- Command Map Values ---->");
				CommandMap map = (CommandMap) param;
				if (map.getMap() != null) {
					Set<String> keyset = map.getMap().keySet();
					Iterator<String> it = keyset.iterator();
					while (it.hasNext()) {
						String key = it.next();
						LOG.info("value->  " + key + " : " + map.getMap().get(key));
					}
				} else {
					LOG.info("  CommandMap에 입력된 값이 없습니다.");
				}
				if (map.getFilesMap() != null) {
					LOG.info("<-- Command FileMap Values -->");
					Set<String> keyset = map.getFilesMap().keySet();
					Iterator<String> it = keyset.iterator();
					while (it.hasNext()) {
						String key = it.next();
						LOG.info("Key : " + key + "▽");
						List<MultipartFile> fileList = (List<MultipartFile>) map.getFilesMap().get(key);
						for (MultipartFile file : fileList) {
							LOG.info("이름 : " + file.getOriginalFilename() + " 사이즈 : " + file.getSize() + " 타입 : " + file.getContentType());
						}
					}
				}
			} else {
				LOG.info("inputValue = " + param + " // " + param.getClass() + " // " + param.toString());
			}
		}

		LOG.info("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  " + signatureString + " AOP InputValues 끝    ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		long start = System.currentTimeMillis();
		try {
			Object result = joinPoint.proceed();

			LOG.info("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  " + signatureString + " AOP OutputValues 시작  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			if (result.getClass().equals(ModelAndView.class)) {
				ModelAndView res = (ModelAndView) result;
				Map<String, Object> resMap = res.getModel();
				String tab = "\t";
				loggingJson(resMap, tab);
			}
			return result;
		} finally {
			long finish = System.currentTimeMillis();
			LOG.info("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  " + signatureString + " 실행 시간 : " + (finish - start) + " ms");
			LOG.info("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒  " + signatureString + " AOP OutputValues 끝    ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");

		}
	}

	String tab = "";

	public void loggingJson(Map<String, Object> map, String tab) {
		// 재귀 함수는 메모리소모가 크다...
		Set<String> keyset = map.keySet();
		Iterator<String> it = keyset.iterator();
		tab += "\t";
		while (it.hasNext()) {
			String key = it.next();
			Object value = map.get(key);
			if (value instanceof List) {
				LOG.debug(tab + "└ " + key + ": (" + value.getClass() + " )");
				List<Map<String, Object>> innerList = (List<Map<String, Object>>) value;
				int len = innerList.size();
				for (int i = 0; i < len; i++) {
					LOG.debug(tab + "------ "+i+"번째 " + key);
					loggingJson(innerList.get(i), tab);
				}
			} else if(value instanceof Map){
				LOG.debug(tab + "└ " + key + ": (" + value.getClass() + " )");
				Map<String, Object> valueMap = (Map<String, Object>) value;
				loggingJson(valueMap, tab);
			} else {
				LOG.debug(tab + "└ " + key + " : " + value.toString());
			}
		}
	}

}
