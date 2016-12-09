package sns.platform.dbtest;

import java.io.File;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sns.platform.biz.file.FileDAO;
import sns.platform.biz.user.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/resources/config/root-context.xml"})
public class UserDAOTest {
	
	@Inject
	public UserDAO userDAO;
	@Inject
	public FileDAO fileDAO;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserDAOTest.class);
//	@Test
//	public void testTime() throws Exception{
//		LOG.info("이야이호"+userDAO.getTime());
//	}
	
	@Test
	public void testFactory() throws Exception{
		
		File del = new File("C:\\sns.platform.files\\board\\20161111\\test3.jpg.jpg");
		
		LOG.info("e "+del.exists());
		LOG.info("d "+del.delete());
		
		File del2 = new File("c:/sns.platform.files/board/20161111/test3.jpg");
		LOG.info("ee "+del2.exists());
		LOG.info("dd "+del2.delete());
//		List<Map<String, Object>> delFileList = new ArrayList<>();
//		Map<String, Object> map1 = new HashMap<>();
//		map1.put("filePath", "/board/20161111/test1.jpg");
//		delFileList.add(map1);
//		Map<String, Object> map2 = new HashMap<>();
//		map2.put("filePath", "/board/20161111/test2.jpg");
//		delFileList.add(map2);
//		Map<String, Object> map3 = new HashMap<>();
//		map3.put("filePath", "/board/20161111/test3.jpg");
//		delFileList.add(map3);
//		for (Map<String, Object> map : delFileList) {
//			File del = new File(Constant.FILE_DIRPATH+map.get("filePath"));
//			if (del.delete()) {
//				LOG.info("파일삭제 : " + Constant.FILE_DIRPATH+map.get("filePath"));
//			} else {
//				LOG.info("파일삭제실패 : " + Constant.FILE_DIRPATH+map.get("filePath"));
//			}
//		}
	}
}