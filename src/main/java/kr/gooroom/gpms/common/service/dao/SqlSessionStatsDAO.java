package kr.gooroom.gpms.common.service.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * @Class Name : SqlSessionMetaDAO.java
 * @Description : SqlSessionMetaDAO Class
 * @Modification Information
 * @
 * @ 수정일       수정자     수정내용
 * @ ---------- --------- -------------------------------
 * @ 2017.05.08 LDH     최초생성
 *
 * @since 2017.05.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by Hancom All right reserved.
 */

public abstract class SqlSessionStatsDAO {
    
    @Resource(name="sqlSessionStats")
    protected SqlSessionTemplate  sqlSessionStats;
}
