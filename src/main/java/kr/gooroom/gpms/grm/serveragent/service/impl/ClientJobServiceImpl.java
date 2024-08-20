package kr.gooroom.gpms.grm.serveragent.service.impl;

import jakarta.annotation.Resource;
import kr.gooroom.gpms.grm.serveragent.service.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("clientJobService")
public class ClientJobServiceImpl implements ClientJobService {

    @Resource(name = "clientJobDAO")
    private ClientJobDAO clientJobDAO;

	@Override
	public int insertLogUpdate(LogUpdateVO log) {
		return clientJobDAO.insertLogUpdate(log);
	}
	
	@Override
	public int insertOrUpdateClientExt(ClientInfoVO clientInfo) {
		return clientJobDAO.insertOrUpdateClientExt(clientInfo);
	}
	
	@Override
	public PackageServerVO selectUpdateServerFiles(String clientId) {
		return clientJobDAO.selectUpdateServerFiles(clientId);
	}
	
	@Override
	public String selectEtcHostsContents(String clientId) {
		return clientJobDAO.selectEtcHostsContents(clientId);
	}
	
	@Override
	public String selectCert(String clientId) {
		return clientJobDAO.selectCert(clientId);
	}
	
	@Override
	public int insertOrUpdatePackage(List<PackageVO> packageVoList) {
		return clientJobDAO.insertOrUpdatePackage(packageVoList);
	}
	
	@Override
	public int insertOrUpdatePackageState(String clientId) {
		return clientJobDAO.insertOrUpdatePackageState(clientId);
	}
	
	@Override
	public int deletePackage(PackageVO packageVo) {
		return clientJobDAO.deletePackage(packageVo);
	}
	
	@Override
	public int deletePackageMstr() {
		return clientJobDAO.deletePackageMstr();
	}
	
	@Override
	public int insertPackageMstr(List<PackageVO> packageVoList) {
		return clientJobDAO.insertPackageMstr(packageVoList);
	}
	
	@Override
	public int insertLogSecurity(List<LogSecurityVO> logSecurityVoList) {
		return clientJobDAO.insertLogSecurity(logSecurityVoList);
	}
	
	@Override
	public int insertLogSecurity2(List<LogSecurity2VO> logSecurity2VoList) {
		return clientJobDAO.insertLogSecurity2(logSecurity2VoList);
	}
	
	@Override
	public int insertOrUpdateClientSecurityState(ClientSecurityStateVO clientSecurityStateVo) {
		return clientJobDAO.insertOrUpdateClientSecurityState(clientSecurityStateVo);
	}
	
	@Override
	public int insertOrUpdateClientSecurityStateClear(ClientSecurityStateVO clientSecurityStateVo) {
		return clientJobDAO.insertOrUpdateClientSecurityStateClear(clientSecurityStateVo);
	}
	
	@Override
	public int insertClientExtHist(String clientId) {
		return clientJobDAO.insertClientExtHist(clientId);
	}
	
	@Override
	public int insertClientSecurityStateHist(String clientId) {
		return clientJobDAO.insertClientSecurityStateHist(clientId);
	}
	
	@Override
	public String selectOneServerjobDispatchTime(String siteName) {
		return clientJobDAO.selectOneServerjobDispatchTime(siteName);
	}

	@Override
	public String selectOneServerjobMaxMediaCnt(String siteName) {
		return clientJobDAO.selectOneServerjobMaxMediaCnt(siteName);
	}

	@Override
	public String selectRegisterReqMod(String siteName) {
		return clientJobDAO.selectRegisterReqMod(siteName);
	}

	@Override
	public String selectDeleteReqMod(String siteName) {
		return clientJobDAO.selectDeleteReqMod(siteName);
	}

	@Override
	public int insertLogGeneral(List<LogGeneralVO> vos) {
		return clientJobDAO.insertLogGeneral(vos);
	}
	
	@Override
	public String selectClientCertificate(String clientId) {
		return clientJobDAO.selectClientCertificate(clientId);
	}
	
	@Override
	public String selectHypervisorOperation(String clientId) {
		return clientJobDAO.selectHypervisorOperation(clientId);
	}
	
	@Override
	public String selectUpdateOperation(ClientLoginVO clVo) {
		return clientJobDAO.selectUpdateOperation(clVo);
	}
	
	@Override 
	public List<String> selectPackageList(String clientId) {
		return clientJobDAO.selectPackageList(clientId);
	}
	
	@Override
	public int insertProfilingPackage(List<ProfileVO> profileVoList) {
		return clientJobDAO.insertProfilingPackage(profileVoList);
	}
	
	@Override 
	public List<String> selectProfilingPackageList(String profileNo) {
		return clientJobDAO.selectProfilingPackageList(profileNo);
	}
	
	@Override
	public String selectServerVersion(String siteName) {
		return clientJobDAO.selectServerVersion(siteName);
	}
	
	@Override
	public String selectPasswordCycle(ClientLoginVO clVo) {
		return clientJobDAO.selectPasswordCycle(clVo);
	}
	
	@Override
	public String selectScreenTime(ClientLoginVO clVo) {
		return clientJobDAO.selectScreenTime(clVo);
	}
	
	@Override
	public List<String> selectAppBlackList(ClientLoginVO clVo) {
		return clientJobDAO.selectAppBlackList(clVo);
	}
	
	@Override
	public String selectHomefolderOperation(String clientId) {
		return clientJobDAO.selectHomefolderOperation(clientId);
	}
	
	@Override
	public List<String> selectIpWhiteList(String clientId) {
		return clientJobDAO.selectIpWhiteList(clientId);
	}

	@Override
	public ThemeVO selectThemeInfo(HashMap<String, Object> options) {
		return clientJobDAO.selectThemeInfo(options);
	}
	
	@Override
	public SchedInfoVO selectSchedInfo(String clientId) {
		return clientJobDAO.selectSchedInfo(clientId);
	}
	
	@Override
	public int insertSchedInfo(SchedInfoVO schedInfo) {
		return clientJobDAO.insertSchedInfo(schedInfo);
	}
	
	@Override
	public int updateSchedInfo(List<SchedInfoVO> schedInfo) {
		return clientJobDAO.updateSchedInfo(schedInfo);
	}
	
	@Override
	public List<NotiVO> selectNoti(ClientLoginVO vo) {
		return clientJobDAO.selectNoti(vo);
	}
	
	@Override
	public String selectDefaultNotiDomain() {
		return clientJobDAO.selectDefaultNotiDomain();
	}
	
	@Override
	public List<NotiVO> selectInstanceNoti(Map<String, Object> params) {
		return clientJobDAO.selectInstanceNoti(params);
	}
	
	@Override
	public int insertLogBrowser(List<LogBrowserVO> vos) {
		return clientJobDAO.insertLogBrowser(vos);
	}
	
	@Override
	public int deleteClientPackage(String clientId) {
		return clientJobDAO.deleteClientPackage(clientId);
	}
	
	@Override
	public List<String> selectAllClients() {
		return clientJobDAO.selectAllClients();
	}
	
	@Override
	public List<String> selectControlcenterItems(ClientLoginVO clVo) {
		return clientJobDAO.selectControlcenterItems(clVo);
	}
	
	@Override
	public int updatePollingTime(PollingTimeVO vo) {
		return clientJobDAO.updatePollingTime(vo);
	}
	
	@Override
	public String selectRootUse(String clientId) {
		return clientJobDAO.selectRootUse(clientId);
	}
	
	@Override
	public String selectSudoUse(String clientId) {
		return clientJobDAO.selectSudoUse(clientId);
	}
	
	@Override
	public String selectPolkitAdmin(String clientId) {
		return clientJobDAO.selectPolkitAdmin(clientId);
	}

	@Override
	public String selectCleanModeUse(String clientId) {
		return clientJobDAO.selectCleanModeUse(clientId);
	}

	@Override
	public int insertUserReqMstr(UserReqVO urmVo) {
		return clientJobDAO.insertUserReqMstr(urmVo);
	}

	@Override
	public int insertUserReqProp(UserReqVO urVo) {
		return clientJobDAO.insertUserReqProp(urVo);
	}

	@Override
	public String selectUserReqSeq(UserReqVO urmVo) {
		return clientJobDAO.selectUserReqSeq(urmVo);
	}

	@Override
	public String selectUserReqPropSeq(UserReqVO urmVo) {
		return clientJobDAO.selectUserReqPropSeq(urmVo);
	}

	@Override
	public int deleteUserReqMstr(UserReqVO urmVo) {
		return clientJobDAO.deleteUserReqMstr(urmVo);
	}

	@Override
	public int deleteUserReqProp(UserReqVO urmVo) {
		return clientJobDAO.deleteUserReqProp(urmVo);
	}

	@Override
	public List<UserReqVO> selectUserUsbMediaList(UserReqVO urmVo) {
		return clientJobDAO.selectUserUsbMediaList(urmVo);
	}

	@Override
	public int updateUserReqProp(UserReqVO urmVo) {
		return clientJobDAO.updateUserReqProp(urmVo);
	}

	@Override
	public String selectExistMediaRegisterReq(UserReqVO urmVo) {
		return clientJobDAO.selectExistMediaRegisterReq(urmVo);
	}

	@Override
	public String selectExistMediaUnRegisterReq(UserReqVO urmVo) {
		return clientJobDAO.selectExistMediaUnRegisterReq(urmVo);
	}

	@Override
	public String selectExistMedia(UserReqVO urmVo) {
		return clientJobDAO.selectExistMedia(urmVo);
	}

	@Override
	public String selectRegisteredReqSeq(UserReqVO urmVo) {
		return clientJobDAO.selectRegisteredReqSeq(urmVo);
	}

	@Override
	public int updateReqProp(UserReqVO urmVo) {
		return clientJobDAO.updateReqProp(urmVo);
	}

	@Override
	public int updateReqMstr(UserReqVO urmVo) {
		return clientJobDAO.updateReqMstr(urmVo);
	}

	@Override
	public UserReqVO selectUserReq(String reqSeq) {
		return clientJobDAO.selectUserReq(reqSeq);
	}

	@Override
	public int insertUserReqHist(UserReqVO vo) {
		return clientJobDAO.insertUserReqHist(vo);
	}

	@Override
	public String selectReqSeqNo(UserReqVO urmVo) {
		return clientJobDAO.selectReqSeqNo(urmVo);
	}

	@Override
	public String selectOnlineClientIdInClientId(String reqSeq) {
		return clientJobDAO.selectOnlineClientIdInClientId(reqSeq);
	}

	@Override
	public long createJobMaster(JobVO jobVO) {
		return clientJobDAO.createJobMaster(jobVO);
	}

	@Override
	public long createJobTarget(JobVO jobVO) {
		return clientJobDAO.createJobTarget(jobVO);
	}
}