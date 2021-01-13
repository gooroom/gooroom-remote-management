package kr.gooroom.gpms.grm.serveragent.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ServerJobService {

	public List<JobVO> selectListForClientReady(String clientId) throws Exception;
	public int updateReadyToDoing(HashMap<String, Object> param) throws Exception;
	public int updateDoingToComplete(JobVO job) throws Exception;
	public int insertOrUpdateJobResult(JobTargetVO jobTargetVo) throws Exception;
	public int insertOrUpdateClientConnStatus(ClientAccessVO cav) throws Exception;
	public int updateDoingToError(JobVO job) throws Exception;
	public int insertOrUpdateOnlineClientConnStatus(ClientAccessVO cav) throws Exception;
	public int insertOrUpdateLocalClientConnStatus(ClientAccessVO cav) throws Exception;
	public int insertOrUpdateNotLogginedClientConnStatus(ClientAccessVO cav) throws Exception;
	public int selectIsJob(String clientId) throws Exception;
	public int updateIsJobTo0(String clientId) throws Exception;
	public String selectOnlineAccessDiffTime(String clientId) throws Exception;
	public String selectLocalAccessDiffTime(String clientId) throws Exception;
	public String selectNotLoginAccessDiffTime(String clientId) throws Exception;
	public int insertJobTrgt(HashMap<String, Object> param) throws Exception;
	public int selectPollingTimeColumnCntInClientAccess() throws Exception;
}
