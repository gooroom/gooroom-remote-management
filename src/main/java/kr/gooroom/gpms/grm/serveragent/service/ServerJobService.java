package kr.gooroom.gpms.grm.serveragent.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ServerJobService {

	List<JobVO> selectListForClientReady(String clientId) throws Exception;
	int updateReadyToDoing(HashMap<String, Object> param) throws Exception;
	int updateDoingToComplete(JobVO job) throws Exception;
	int insertOrUpdateJobResult(JobTargetVO jobTargetVo) throws Exception;
	int insertOrUpdateClientConnStatus(ClientAccessVO cav) throws Exception;
	int updateDoingToError(JobVO job) throws Exception;
	int insertOrUpdateOnlineClientConnStatus(ClientAccessVO cav) throws Exception;
	int insertOrUpdateLocalClientConnStatus(ClientAccessVO cav) throws Exception;
	int insertOrUpdateNotLogginedClientConnStatus(ClientAccessVO cav) throws Exception;
	int selectIsJob(String clientId) throws Exception;
	int updateIsJobTo0(String clientId) throws Exception;
	String selectOnlineAccessDiffTime(String clientId) throws Exception;
	String selectLocalAccessDiffTime(String clientId) throws Exception;
	String selectNotLoginAccessDiffTime(String clientId) throws Exception;
	int insertJobTrgt(HashMap<String, Object> param) throws Exception;
	int selectPollingTimeColumnCntInClientAccess() throws Exception;
}
