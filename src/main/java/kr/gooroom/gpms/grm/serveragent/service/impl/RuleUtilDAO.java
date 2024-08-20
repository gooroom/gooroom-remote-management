package kr.gooroom.gpms.grm.serveragent.service.impl;

import kr.gooroom.gpms.common.service.dao.SqlSessionMetaDAO;
import kr.gooroom.gpms.common.utils.GPMSConstants;
import kr.gooroom.gpms.grm.serveragent.service.CtrlItemVO;
import kr.gooroom.gpms.grm.serveragent.service.CtrlPropVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository("ruleUtilDAO")
public class RuleUtilDAO extends SqlSessionMetaDAO {

    private static final Logger logger = LoggerFactory.getLogger(RuleUtilDAO.class);

    /**
     * 
     * 
     * @param String objId
     * @return CtrlItemVO
	 */
    public CtrlItemVO selectCtrlItem(String objId) {

	CtrlItemVO re = null;
	try {

	    re = sqlSessionMeta.selectOne("RuleUtilDAO.selectCtrlItem", objId);

	} catch (Exception ex) {
	    logger.error("RuleUtilDAO.selectCtrlItem Exception occurred. ", ex);
	}

	return re;
    }

    /**
     * 
     * 
     * @param String objId
     * @return List<CtrlPropVO>
	 */
    public List<CtrlPropVO> selectCtrlPropList(String objId) {
	List<CtrlPropVO> re = null;
	try {

	    re = sqlSessionMeta.selectList("RuleUtilDAO.selectCtrlPropList", objId);

	} catch (Exception ex) {
	    logger.error("RuleUtilDAO.selectCtrlPropList Exception occurred. ", ex);
	}

	return re;
    }

    /**
     * 
     * 
     * @param HashMap param
     * @return String
	 */
    public String selectItemIdByMap(HashMap param) {

	String re = "";
	try {
	    re = sqlSessionMeta.selectOne("RuleUtilDAO.selectItemIdByMap", param);
	} catch (Exception ex) {
	    logger.error("RuleUtilDAO.selectItemIdByMap Exception occurred. ", ex);
	}

	return re;
    }

    /**
     * 
     * 
     * @param HashMap param
     * @return String
	 */
    public String selectItemIdWithMapInClientRule(HashMap param) {

	String re = "";
	try {
	    re = sqlSessionMeta.selectOne("RuleUtilDAO.selectItemIdWithMapInClientRule", param);
	} catch (Exception ex) {
	    logger.error("RuleUtilDAO.selectItemIdByMap Exception occurred. ", ex);
	}

	return re;
    }

    /**
     * 
     * @return String
	 */
    public String selectSiteVersion() {

	String re = "";
	String siteName = GPMSConstants.SITE_NAME;
	try {
	    re = sqlSessionMeta.selectOne("RuleUtilDAO.selectSiteVersion", siteName);
	} catch (Exception ex) {
	    logger.error("RuleUtilDAO.selectSiteVersion Exception occurred. ", ex);
	}

	return re;
    }
}
