package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import kr.gooroom.gpms.grm.serveragent.service.ClientInfoVO;
import kr.gooroom.gpms.grm.serveragent.service.ClientJobService;
import kr.gooroom.gpms.grm.serveragent.service.ClientLoginVO;
import kr.gooroom.gpms.grm.serveragent.service.ClientSecurityStateVO;
import kr.gooroom.gpms.grm.serveragent.service.LogBrowserVO;
import kr.gooroom.gpms.grm.serveragent.service.LogGeneralVO;
import kr.gooroom.gpms.grm.serveragent.service.LogSecurityVO;
import kr.gooroom.gpms.grm.serveragent.service.LogSecurity2VO;
import kr.gooroom.gpms.grm.serveragent.service.LogUpdateVO;
import kr.gooroom.gpms.grm.serveragent.service.NotiVO;
import kr.gooroom.gpms.grm.serveragent.service.PackageServerVO;
import kr.gooroom.gpms.grm.serveragent.service.PackageVO;
import kr.gooroom.gpms.grm.serveragent.service.PollingTimeVO;
import kr.gooroom.gpms.grm.serveragent.service.ProfileVO;
import kr.gooroom.gpms.grm.serveragent.service.SchedInfoVO;

@Service("clientJobService")
public class ClientJobServiceImpl implements ClientJobService {

    @Resource(name = "clientJobDAO")
    private ClientJobDAO clientJobDAO;

	@Override
	public int insertLogUpdate(LogUpdateVO log) throws Exception {
		return clientJobDAO.insertLogUpdate(log);
	}
	
	@Override
	public int insertOrUpdateClientExt(ClientInfoVO clientInfo) throws Exception {
		return clientJobDAO.insertOrUpdateClientExt(clientInfo);
	}
	
	@Override
	public PackageServerVO selectUpdateServerFiles(String clientId) throws Exception {
		return clientJobDAO.selectUpdateServerFiles(clientId);
	}
	
	@Override
	public String selectEtcHostsContents(String clientId) throws Exception {
		return clientJobDAO.selectEtcHostsContents(clientId);
	}
	
	@Override
	public String selectCert(String clientId) throws Exception {
		return clientJobDAO.selectCert(clientId);
	}
	
	@Override
	public int insertOrUpdatePackage(List<PackageVO> packageVoList) throws Exception {
		return clientJobDAO.insertOrUpdatePackage(packageVoList);
	}
	
	@Override
	public int insertOrUpdatePackageState(String clientId) throws Exception {
		return clientJobDAO.insertOrUpdatePackageState(clientId);
	}
	
	@Override
	public int deletePackage(PackageVO packageVo) throws Exception {
		return clientJobDAO.deletePackage(packageVo);
	}
	
	@Override
	public int deletePackageMstr() throws Exception {
		return clientJobDAO.deletePackageMstr();
	}
	
	@Override
	public int insertPackageMstr(List<PackageVO> packageVoList) throws Exception {
		return clientJobDAO.insertPackageMstr(packageVoList);
	}
	
	@Override
	public int insertLogSecurity(List<LogSecurityVO> logSecurityVoList) throws Exception {
		return clientJobDAO.insertLogSecurity(logSecurityVoList);
	}
	
	@Override
	public int insertLogSecurity2(List<LogSecurity2VO> logSecurity2VoList) throws Exception {
		return clientJobDAO.insertLogSecurity2(logSecurity2VoList);
	}
	
	@Override
	public int insertOrUpdateClientSecurityState(ClientSecurityStateVO clientSecurityStateVo) throws Exception {
		return clientJobDAO.insertOrUpdateClientSecurityState(clientSecurityStateVo);
	}
	
	@Override
	public int insertOrUpdateClientSecurityStateClear(ClientSecurityStateVO clientSecurityStateVo) throws Exception {
		return clientJobDAO.insertOrUpdateClientSecurityStateClear(clientSecurityStateVo);
	}
	
	@Override
	public int insertClientExtHist(String clientId) throws Exception {
		return clientJobDAO.insertClientExtHist(clientId);
	}
	
	@Override
	public int insertClientSecurityStateHist(String clientId) throws Exception {
		return clientJobDAO.insertClientSecurityStateHist(clientId);
	}
	
	@Override
	public String selectOneServerjobDispatchTime(String siteName) throws Exception {
		return clientJobDAO.selectOneServerjobDispatchTime(siteName);
	}

	@Override
	public int insertLogGeneral(List<LogGeneralVO> vos) throws Exception {
		return clientJobDAO.insertLogGeneral(vos);
	}
	
	@Override
	public String selectClientCertificate(String clientId) throws Exception {
		return clientJobDAO.selectClientCertificate(clientId);
	}
	
	@Override
	public String selectHypervisorOperation(String clientId) throws Exception {
		return clientJobDAO.selectHypervisorOperation(clientId);
	}
	
	@Override
	public String selectUpdateOperation(ClientLoginVO clVo) throws Exception {
		return clientJobDAO.selectUpdateOperation(clVo);
	}
	
	@Override 
	public List<String> selectPackageList(String clientId) throws Exception {
		return clientJobDAO.selectPackageList(clientId);
	}
	
	@Override
	public int insertProfilingPackage(List<ProfileVO> profileVoList) throws Exception {
		return clientJobDAO.insertProfilingPackage(profileVoList);
	}
	
	@Override 
	public List<String> selectProfilingPackageList(String profileNo) throws Exception {
		return clientJobDAO.selectProfilingPackageList(profileNo);
	}
	
	@Override
	public String selectServerVersion(String siteName) throws Exception {
		return clientJobDAO.selectServerVersion(siteName);
	}
	
	@Override
	public String selectPasswordCycle(ClientLoginVO clVo) throws Exception {
		return clientJobDAO.selectPasswordCycle(clVo);
	}
	
	@Override
	public String selectScreenTime(ClientLoginVO clVo) throws Exception {
		return clientJobDAO.selectScreenTime(clVo);
	}
	
	@Override
	public List<String> selectAppBlackList(ClientLoginVO clVo) throws Exception {
		return clientJobDAO.selectAppBlackList(clVo);
	}
	
	@Override
	public String selectHomefolderOperation(String clientId) throws Exception {
		return clientJobDAO.selectHomefolderOperation(clientId);
	}
	
	@Override
	public List<String> selectIpWhiteList(String clientId) throws Exception {
		return clientJobDAO.selectIpWhiteList(clientId);
	}
	
	@Override
	public SchedInfoVO selectSchedInfo(String clientId) throws Exception {
		return clientJobDAO.selectSchedInfo(clientId);
	}
	
	@Override
	public int insertSchedInfo(SchedInfoVO schedInfo) throws Exception {
		return clientJobDAO.insertSchedInfo(schedInfo);
	}
	
	@Override
	public int updateSchedInfo(List<SchedInfoVO> schedInfo) throws Exception {
		return clientJobDAO.updateSchedInfo(schedInfo);
	}
	
	@Override
	public List<NotiVO> selectNoti(ClientLoginVO vo) throws Exception {
		return clientJobDAO.selectNoti(vo);
	}
	
	@Override
	public String selectDefaultNotiDomain() throws Exception {
		return clientJobDAO.selectDefaultNotiDomain();
	}
	
	@Override
	public List<NotiVO> selectInstanceNoti(Map<String, Object> params) throws Exception {
		return clientJobDAO.selectInstanceNoti(params);
	}
	
	@Override
	public int insertLogBrowser(List<LogBrowserVO> vos) throws Exception {
		return clientJobDAO.insertLogBrowser(vos);
	}
	
	@Override
	public int deleteClientPackage(String clientId) throws Exception {
		return clientJobDAO.deleteClientPackage(clientId);
	}
	
	@Override
	public List<String> selectAllClients() throws Exception {
		return clientJobDAO.selectAllClients();
	}
	
	@Override
	public List<String> selectControlcenterItems(ClientLoginVO clVo) throws Exception {
		return clientJobDAO.selectControlcenterItems(clVo);
	}
	
	@Override
	public int updatePollingTime(PollingTimeVO vo) throws Exception {
		return clientJobDAO.updatePollingTime(vo);
	}
	
	@Override
	public String selectRootUse(String clientId) throws Exception {
		return clientJobDAO.selectRootUse(clientId);
	}
	
	@Override
	public String selectSudoUse(String clientId) throws Exception {
		return clientJobDAO.selectSudoUse(clientId);
	}
	
	@Override
	public String selectPolkitAdmin(String clientId) throws Exception {
		return clientJobDAO.selectPolkitAdmin(clientId);
	}
}
