package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.util.HashMap;
import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.gooroom.gpms.grm.serveragent.service.ServerJobService;
import kr.gooroom.gpms.grm.serveragent.service.ClientAccessVO;
import kr.gooroom.gpms.grm.serveragent.service.JobTargetVO;
import kr.gooroom.gpms.grm.serveragent.service.JobVO;

@Service("serverJobService")
public class ServerJobServiceImpl implements ServerJobService {

    @Resource(name = "serverJobDAO")
    private ServerJobDAO serverJobDAO;
    
	@Override
	public List<JobVO> selectListForClientReady(String clientId) {
		return serverJobDAO.selectListForClientReady(clientId);
	}

	@Override
	public int updateReadyToDoing(HashMap<String, Object> param) {
		return serverJobDAO.updateReadyToDoing(param);
	}
	
	@Override
	public int updateDoingToComplete(JobVO job) {
		return serverJobDAO.updateDoingToComplete(job);
	}
	
	@Override
	public int insertOrUpdateJobResult(JobTargetVO jobTargetVo) {
		return serverJobDAO.insertOrUpdateJobResult(jobTargetVo);
	}
	
	@Override
	public int insertOrUpdateClientConnStatus(ClientAccessVO cav) {
		return serverJobDAO.insertOrUpdateClientConnStatus(cav);
	}
	
	@Override
	public int updateDoingToError(JobVO job) {
		return serverJobDAO.updateDoingToError(job);
	}
	
	@Override
	public int insertOrUpdateOnlineClientConnStatus(ClientAccessVO cav) {
		return serverJobDAO.insertOrUpdateOnlineClientConnStatus(cav);
	}
	
	@Override
	public int insertOrUpdateLocalClientConnStatus(ClientAccessVO cav) {
		return serverJobDAO.insertOrUpdateLocalClientConnStatus(cav);
	}
	
	@Override
	public int insertOrUpdateNotLogginedClientConnStatus(ClientAccessVO cav) {
		return serverJobDAO.insertOrUpdateNotLogginedClientConnStatus(cav);
	}
	
	@Override
	public int selectIsJob(String clientId) {
		return serverJobDAO.selectIsJob(clientId);
	}
	
	@Override
	public int updateIsJobTo0(String clientId) {
		return serverJobDAO.updateIsJobTo0(clientId);
	}
	
	@Override
	public String selectOnlineAccessDiffTime(String clientId) {
		return serverJobDAO.selectOnlineAccessDiffTime(clientId);
	}
	
	@Override
	public String selectLocalAccessDiffTime(String clientId) {
		return serverJobDAO.selectLocalAccessDiffTime(clientId);
	}
	
	@Override
	public String selectNotLoginAccessDiffTime(String clientId) {
		return serverJobDAO.selectNotLoginAccessDiffTime(clientId);
	}
	
	@Override
	public int insertJobTrgt(HashMap<String, Object> param) {
		return serverJobDAO.insertJobTrgt(param);
	}
	
	@Override
	public int selectPollingTimeColumnCntInClientAccess() {
		return serverJobDAO.selectPollingTimeColumnCntInClientAccess();
	}
}
