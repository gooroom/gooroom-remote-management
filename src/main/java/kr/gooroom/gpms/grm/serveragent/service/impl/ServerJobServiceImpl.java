package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

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
	public List<JobVO> selectListForClientReady(String clientId) throws Exception {
		return serverJobDAO.selectListForClientReady(clientId);
	}

	@Override
	public int updateReadyToDoing(HashMap<String, Object> param) throws Exception {
		return serverJobDAO.updateReadyToDoing(param);
	}
	
	@Override
	public int updateDoingToComplete(JobVO job) throws Exception {
		return serverJobDAO.updateDoingToComplete(job);
	}
	
	@Override
	public int insertOrUpdateJobResult(JobTargetVO jobTargetVo) throws Exception {
		return serverJobDAO.insertOrUpdateJobResult(jobTargetVo);
	}
	
	@Override
	public int insertOrUpdateClientConnStatus(ClientAccessVO cav) throws Exception {
		return serverJobDAO.insertOrUpdateClientConnStatus(cav);
	}
	
	@Override
	public int updateDoingToError(JobVO job) throws Exception {
		return serverJobDAO.updateDoingToError(job);
	}
	
	@Override
	public int insertOrUpdateOnlineClientConnStatus(ClientAccessVO cav) throws Exception {
		return serverJobDAO.insertOrUpdateOnlineClientConnStatus(cav);
	}
	
	@Override
	public int insertOrUpdateLocalClientConnStatus(ClientAccessVO cav) throws Exception {
		return serverJobDAO.insertOrUpdateLocalClientConnStatus(cav);
	}
	
	@Override
	public int insertOrUpdateNotLogginedClientConnStatus(ClientAccessVO cav) throws Exception {
		return serverJobDAO.insertOrUpdateNotLogginedClientConnStatus(cav);
	}
	
	@Override
	public int selectIsJob(String clientId) throws Exception {
		return serverJobDAO.selectIsJob(clientId);
	}
	
	@Override
	public int updateIsJobTo0(String clientId) throws Exception {
		return serverJobDAO.updateIsJobTo0(clientId);
	}
	
	@Override
	public String selectOnlineAccessDiffTime(String clientId) throws Exception {
		return serverJobDAO.selectOnlineAccessDiffTime(clientId);
	}
	
	@Override
	public String selectLocalAccessDiffTime(String clientId) throws Exception {
		return serverJobDAO.selectLocalAccessDiffTime(clientId);
	}
	
	@Override
	public String selectNotLoginAccessDiffTime(String clientId) throws Exception {
		return serverJobDAO.selectNotLoginAccessDiffTime(clientId);
	}
	
	@Override
	public int insertJobTrgt(HashMap<String, Object> param) throws Exception {
		return serverJobDAO.insertJobTrgt(param);
	}
	
	@Override
	public int selectPollingTimeColumnCntInClientAccess() throws Exception {
		return serverJobDAO.selectPollingTimeColumnCntInClientAccess();
	}
}
