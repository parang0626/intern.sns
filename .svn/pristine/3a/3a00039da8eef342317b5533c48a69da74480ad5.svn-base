package sns.platform.dbtest;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/resources/config/root-context.xml"
		})
public class MybatisTest {
	
	@Inject
	private SqlSessionFactory sqlFactory;
	
	private static final Logger LOG = LoggerFactory.getLogger(SqlSessionFactory.class);
	
	@Test
	public void testFactoryss() throws Exception{
		 
		try(SqlSession session = sqlFactory.openSession()){
			LOG.info("성공 "+session);
		}catch(Exception e){
			LOG.info("실패 ");
			e.printStackTrace();
		}
	}
}