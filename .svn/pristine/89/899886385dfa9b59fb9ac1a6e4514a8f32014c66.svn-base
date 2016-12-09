package sns.platform.dbtest;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/resources/config/root-context.xml"})
public class DataSourceTest {
	
	@Inject
	private DataSource dataSource;
	
	private static final Logger LOG = LoggerFactory.getLogger(DataSourceTest.class);
	
	@Test
	public void testConection() throws Exception{
		 
		try(Connection con = dataSource.getConnection()){
			LOG.info("성공 "+con);
		}catch(Exception e){
			LOG.info("실패 ");
			e.printStackTrace();
		}
	}
}

