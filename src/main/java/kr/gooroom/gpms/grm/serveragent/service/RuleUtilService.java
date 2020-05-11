package kr.gooroom.gpms.grm.serveragent.service;

import java.util.HashMap;

/**
 * @Class Name : ClientService.java
 * @Description : ClientService
 * @Modification Information
 *
 * @author syhan
 * @since 2017-05-08
 * @version 1.0
 * @see
 * 
 *      Copyright (C) All right reserved.
 */

public interface RuleUtilService {
    
    /**
     * 브라우져 정책 JSON 조회 - JSON STRING
     * 
     * @param String userId
	 * @param String clientId
     * @return String
     * @throws Exception
     */
    String getBrowserRuleJson(String userId, String clientId) throws Exception;

    /**
     * 브라우져정책 - 사이트설정 JSON JSON 조회
     * 
     * @param String userId
     * @param String clientId
     * @param String gubun
     * @return String
     * @throws Exception
     */
    String getBrowserRuleSiteSetupJson(String userId, String clientId, String gubun) throws Exception;

    /**
     * 브라우저정책 - 추가 설정 조회
     * 
     * @param String userId
     * @param String clientId
     * @param String gubun
     * @return String
     * @throws Exception
     */
    String getBrowserExtRuleJson(String userId, String clientId, String gubun) throws Exception;

    /**
     * 로그 컨피그 검색 (저널디 설정 제외) 
     * 
     * @param String clientId
     * @return String
     * @throws Exception
     */
    String getLogConfigJson(String clientId) throws Exception;

    /**
     * 로그 컨피그중 저널디 설정 검색 
     * 
     * @param String clientId
     * @return String
     * @throws Exception
     */
    String getJournaldConfigJson(String clientId) throws Exception;

    /**
     * 네트워크(단말기보안정책) 부분과 매체제어정채을 합한 JSON 조회 - JSON STRING
     * 
     * @param String userId
     * @return String
     * @throws Exception
     */
    String getNetworkAndMediaRuleJson(String userId, String clientId) throws Exception;

    /**
     * Policy Kit 정책을 위한 JSON 조회 - JSON STRING
     * 
     * @param String userId
     * @return String
     * @throws Exception
     */
    String getPolicyKitRuleJson(String userId, String clientId) throws Exception;
}
