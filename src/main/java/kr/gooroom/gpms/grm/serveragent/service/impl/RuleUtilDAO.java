package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.gooroom.gpms.common.service.dao.SqlSessionMetaDAO;
import kr.gooroom.gpms.common.utils.GPMSConstants;
import kr.gooroom.gpms.grm.serveragent.service.CtrlItemVO;
import kr.gooroom.gpms.grm.serveragent.service.CtrlPropVO;

@Repository("ruleUtilDAO")
public class RuleUtilDAO extends SqlSessionMetaDAO {

    private static final Logger logger = LoggerFactory.getLogger(RuleUtilDAO.class);

    /**
     * 
     * 
     * @param String objId
     * @return CtrlItemVO
     * @throws Exception
     */
    public CtrlItemVO selectCtrlItem(String objId) throws SQLException {

	CtrlItemVO re = null;
	try {

	    re = sqlSessionMeta.selectOne("RuleUtilDAO.selectCtrlItem", objId);

	} catch (Exception ex) {
	    re = null;
	    logger.error("RuleUtilDAO.selectCtrlItem Exception occurred. ", ex);
	}

	return re;
    }

    /**
     * 
     * 
     * @param String objId
     * @return List<CtrlPropVO>
     * @throws Exception
     */
    public List<CtrlPropVO> selectCtrlPropList(String objId) throws SQLException {
	List<CtrlPropVO> re = null;
	try {

	    re = sqlSessionMeta.selectList("RuleUtilDAO.selectCtrlPropList", objId);

	} catch (Exception ex) {
	    re = null;
	    logger.error("RuleUtilDAO.selectCtrlPropList Exception occurred. ", ex);
	}

	return re;
    }

    /**
     * 
     * 
     * @param HashMap param
     * @return String
     * @throws Exception
     */
    public String selectItemIdByMap(HashMap param) throws SQLException {

	String re = "";
	try {
	    re = sqlSessionMeta.selectOne("RuleUtilDAO.selectItemIdByMap", param);
	} catch (Exception ex) {
	    re = "";
	    logger.error("RuleUtilDAO.selectItemIdByMap Exception occurred. ", ex);
	}

	return re;
    }

    /**
     * 
     * 
     * @param HashMap param
     * @return String
     * @throws Exception
     */
    public String selectItemIdWithMapInClientRule(HashMap param) throws SQLException {

	String re = "";
	try {
	    re = sqlSessionMeta.selectOne("RuleUtilDAO.selectItemIdWithMapInClientRule", param);
	} catch (Exception ex) {
	    re = "";
	    logger.error("RuleUtilDAO.selectItemIdByMap Exception occurred. ", ex);
	}

	return re;
    }

    /**
     * 
     * @return String
     * @throws Exception
     */
    public String selectSiteVersion() throws SQLException {

	String re = "";
	String siteName = GPMSConstants.SITE_NAME;
	try {
	    re = sqlSessionMeta.selectOne("RuleUtilDAO.selectSiteVersion", siteName);
	} catch (Exception ex) {
	    re = "";
	    logger.error("RuleUtilDAO.selectSiteVersion Exception occurred. ", ex);
	}

	return re;
    }
}
