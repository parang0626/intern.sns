/**
 * 
 */
package sns.platform.comm.push.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import sns.platform.comm.push.vo.SPPushReqVO;

/**
 * @author openit
 *
 */
@Repository
public class PushDao {
	
	@Inject
	private SqlSession session;

	private static String NAME_SPACE = "PushMapper";
	
	/**
	 * 푸시발송등록
	 * SP : SP_PUT_PUSH_DATA
	 * 
	 * @param boardNum
	 */
	public void putPush(String type, Integer boardNum) {
		session.selectOne(NAME_SPACE + ".spPutPushData", new SPPushReqVO(type, boardNum));
	}
	

}
