package kr.gooroom.gpms.grm.serveragent.service;

import java.util.List;
import java.util.Map;

public interface ClientJobService {

	public int insertLogUpdate(LogUpdateVO log) throws Exception;
	public int insertOrUpdateClientExt(ClientInfoVO clientInfo) throws Exception;
	public PackageServerVO selectUpdateServerFiles(String clientId) throws Exception;
	public String selectEtcHostsContents(String clientId) throws Exception;
	public String selectCert(String clientId) throws Exception;
	public int insertOrUpdatePackage(List<PackageVO> packageVoList) throws Exception;
	public int insertOrUpdatePackageState(String clientId) throws Exception;
	public int deletePackage(PackageVO packageVo) throws Exception;
	public int deletePackageMstr() throws Exception;
	public int insertPackageMstr(List<PackageVO> packageVoList) throws Exception;
	public int insertLogSecurity(List<LogSecurityVO> logSecurityVoList) throws Exception;
	public int insertLogSecurity2(List<LogSecurity2VO> logSecurity2VoList) throws Exception;
	public int insertOrUpdateClientSecurityState(ClientSecurityStateVO clientSecurityStateVo) throws Exception;
	public int insertOrUpdateClientSecurityStateClear(ClientSecurityStateVO clientSecurityStateVo) throws Exception;
	public int insertClientExtHist(String clientId) throws Exception;
	public int insertClientSecurityStateHist(String clientId) throws Exception;
	public String selectOneServerjobDispatchTime(String siteName) throws Exception;
	public int insertLogGeneral(List<LogGeneralVO> vos) throws Exception;
	public String selectClientCertificate(String clientId) throws Exception;
	public String selectHypervisorOperation(String clientId) throws Exception;
	public String selectUpdateOperation(ClientLoginVO clVo) throws Exception;
	public List<String> selectPackageList(String clientId) throws Exception;
	public int insertProfilingPackage(List<ProfileVO> profileVoList) throws Exception;
	public List<String> selectProfilingPackageList(String profileNo) throws Exception;
	public String selectServerVersion(String siteName) throws Exception;
	public String selectPasswordCycle(ClientLoginVO clVo) throws Exception;
	public String selectScreenTime(ClientLoginVO clVo) throws Exception;
	public List<String> selectAppBlackList(ClientLoginVO clVo) throws Exception;
	public String selectHomefolderOperation(String clientId) throws Exception;
	public List<String> selectIpWhiteList(String clientId) throws Exception;
	public SchedInfoVO selectSchedInfo(String clientId) throws Exception;
	public int insertSchedInfo(SchedInfoVO schedInfo) throws Exception;
	public int updateSchedInfo(List<SchedInfoVO> schedInfo) throws Exception;
	public List<NotiVO> selectNoti(ClientLoginVO vo) throws Exception;
	public String selectDefaultNotiDomain() throws Exception;
	public List<NotiVO> selectInstanceNoti(Map<String, Object> params) throws Exception;
	public int insertLogBrowser(List<LogBrowserVO> vos) throws Exception;
	public int deleteClientPackage(String clientId) throws Exception;
	public List<String> selectAllClients() throws Exception;
	public List<String> selectControlcenterItems(ClientLoginVO clVo) throws Exception;
	public int updatePollingTime(PollingTimeVO vo) throws Exception;
	public String selectRootUse(String clientId) throws Exception;
	public String selectSudoUse(String clientId) throws Exception;
	public String selectPolkitAdmin(String clientId) throws Exception;
}
