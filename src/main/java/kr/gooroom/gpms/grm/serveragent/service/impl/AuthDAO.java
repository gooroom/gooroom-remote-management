package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import kr.gooroom.gpms.common.service.dao.SqlSessionMetaDAO;
import kr.gooroom.gpms.grm.serveragent.service.AuthVO;

/**
 * @Class Name : AuthDAO.java
 * @Description : AuthDAO Class
 * @Modification Information
 * @
 * @ 수정일       수정자     수정내용
 * @ ---------- --------- -------------------------------
 * @ 2017.05.10 hmkim     최초생성
 *
 * @since 2017.05.10
 * @version 1.0
 * @see
 *
 *  Copyright (C) by Hancom All right reserved.
 */
@Repository("authDAO")
public class AuthDAO extends SqlSessionMetaDAO {

	/**
	 * 클라이언트 정보
	 * 
	 * @param String trmutId
	 * @return List<AuthVO>
	 * @throws Exception
	 */
	public List<AuthVO> selectTrmut(String trmutId) throws Exception {
		return sqlSessionMeta.selectList("AuthManagerDAO.selectTrmut", trmutId);
	}

}
