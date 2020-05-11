package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import kr.gooroom.gpms.common.service.dao.SqlSessionMetaDAO;
import kr.gooroom.gpms.grm.serveragent.service.ClientInfoVO;
import kr.gooroom.gpms.grm.serveragent.service.ClientLoginVO;
import kr.gooroom.gpms.grm.serveragent.service.ClientSecurityStateVO;
import kr.gooroom.gpms.grm.serveragent.service.LogBrowserVO;
import kr.gooroom.gpms.grm.serveragent.service.LogGeneralVO;
import kr.gooroom.gpms.grm.serveragent.service.LogSecurity2VO;
import kr.gooroom.gpms.grm.serveragent.service.LogSecurityVO;
import kr.gooroom.gpms.grm.serveragent.service.LogUpdateVO;
import kr.gooroom.gpms.grm.serveragent.service.NotiVO;
import kr.gooroom.gpms.grm.serveragent.service.PackageServerVO;
import kr.gooroom.gpms.grm.serveragent.service.PackageVO;
import kr.gooroom.gpms.grm.serveragent.service.PollingTimeVO;
import kr.gooroom.gpms.grm.serveragent.service.ProfileVO;
import kr.gooroom.gpms.grm.serveragent.service.SchedInfoVO;

/**
 * @Class Name : ClientJobDAO.java
 * @Description : ClientJobDAO Class
 * @Modification Information
 * @
 * @ 수정일       수정자     수정내용
 * @ ---------- --------- -------------------------------
 * @ 2017.06.20 hmkim     최초생성
 *
 * @since 2017.06.20
 * @version 1.0
 * @see
 *
 *  Copyright (C) by Hancom All right reserved.
 */
@Repository("clientJobDAO")
public class ClientJobDAO extends SqlSessionMetaDAO {

	/**
	 * update log를 저장
	 * 
	 * @param LogUpdateVO log
	 * @return int
	 * @throws Exception
	 */
	public int insertLogUpdate(LogUpdateVO log) throws Exception {
		return sqlSessionMeta.update("ClientJobManagerDAO.insertLogUpdate", log);
	}
	
	/**
	 * update client정보를 저장
	 * 
	 * @param ClientInfoVO clientInfo
	 * @return int
	 * @throws Exception
	 */
	public int insertOrUpdateClientExt(ClientInfoVO clientInfo) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertOrUpdateClientExt", clientInfo);
	}
	
	/**
	 * update-server files 검색
	 * 
	 * @param 
	 * @return PackageServerVO
	 * @throws Exception
	 */
	public PackageServerVO selectUpdateServerFiles(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectUpdateServerFiles", clientId);
	}
	
	/**
	 * /etc/hosts 검색
	 * 
	 * @param 
	 * @return PackageServerVO
	 * @throws Exception
	 */
	public String selectEtcHostsContents(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectEtcHostsContents", clientId);
	}
	
	/**
	 * 인증서 검색
	 * 
	 * @param String clientId
	 * @return String 
	 * @throws Exception
	 */
	public String selectCert(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectCert",clientId);
	}
	
	/**
	 * 패키지 정보 저장
	 * 
	 * @param List<PackageVO> packageVoList
	 * @return int
	 * @throws Exception
	 */
	public int insertOrUpdatePackage(List<PackageVO> packageVoList) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertOrUpdatePackage",packageVoList);
	}
	
	/**
	 * 패키지 정보 상태 저장
	 * 
	 * @param String clientId
	 * @return int
	 * @throws Exception
	 */
	public int insertOrUpdatePackageState(String clientId) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertOrUpdatePackageState",clientId);
	}
	
	/**
	 * 패키지 정보 삭제
	 * 
	 * @param PackageVO packageVo
	 * @return int
	 * @throws Exception
	 */
	public int deletePackage(PackageVO packageVo) throws Exception {
		return sqlSessionMeta.delete("ClientJobManagerDAO.deletePackage",packageVo);
	}
	
	/**
	 * 패키지 마스터 정보 삭제
	 * 
	 * @param 
	 * @return int
	 * @throws Exception
	 */
	public int deletePackageMstr() throws Exception {
		return sqlSessionMeta.delete("ClientJobManagerDAO.deletePackageMstr");
	}
	
	/**
	 * 패키지 마스터 정보 입력
	 * 
	 * @param List<PackageVO> packageVoList
	 * @return int
	 * @throws Exception
	 */
	public int insertPackageMstr(List<PackageVO> packageVoList) throws Exception {
		return sqlSessionMeta.delete("ClientJobManagerDAO.insertPackageMstr", packageVoList);
	}
	
	/**
	 * 보안 로그 입력
	 * 
	 * @param List<LogSecurityVO> logSecurityVo
	 * @return int
	 * @throws Exception
	 */
	public int insertLogSecurity(List<LogSecurityVO> logSecurityVoList) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertLogSecurity", logSecurityVoList);
	}
	
	/**
	 * 보안 로그 입력(NEW)
	 * 
	 * @param List<LogSecurityVO> logSecurity2Vo
	 * @return int
	 * @throws Exception
	 */
	public int insertLogSecurity2(List<LogSecurity2VO> logSecurity2VoList) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertLogSecurity2", logSecurity2VoList);
	}
	
	/**
	 * 클라이언트 보안 상태 업데이트
	 * 
	 * @param List<LogSecurityVO> logSecurityVo
	 * @return int
	 * @throws Exception
	 */
	public int insertOrUpdateClientSecurityState(ClientSecurityStateVO clientSecurityStateVo) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertOrUpdateClientSecurityState", clientSecurityStateVo);
	}
	
	/**
	 * 클라이언트 보안 상태 클리어(안전상태)
	 * 
	 * @param List<LogSecurityVO> logSecurityVo
	 * @return int
	 * @throws Exception
	 */
	public int insertOrUpdateClientSecurityStateClear(ClientSecurityStateVO clientSecurityStateVo) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertOrUpdateClientSecurityStateClear", clientSecurityStateVo);
	}
	
	/**
	 * update client정보 히스토리를 저장
	 * 
	 * @param String clientId
	 * @return int
	 * @throws Exception
	 */
	public int insertClientExtHist(String clientId) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertClientExtHist", clientId);
	}
	
	/**
	 * 클라이언트 보안 상태 히스토리 업데이트
	 * 
	 * @param String clientId
	 * @return int
	 * @throws Exception
	 */
	public int insertClientSecurityStateHist(String clientId) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertClientSecurityStateHist", clientId);
	}
	
	/**
	 * 에이젼트의 폴링주기값을 검색
	 * 
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public String selectOneServerjobDispatchTime(String siteName) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectOneServerjobDispatchTime", siteName);
	}
	
	/**
	 * 일반 로그를 저장
	 * 
	 * @param List<LogGeneralVO>
	 * @return int
	 * @throws Exception
	 */
	public int insertLogGeneral(List<LogGeneralVO> vos) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertLogGeneral", vos);
	}
	
	/**
	 * 클라이언트의 인증서를 검색
	 * 
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public String selectClientCertificate(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectClientCertificate", clientId);
	}
	
	/**
	 * 경량하이퍼바이저 활성화/비활성화 상태를 검색
	 * 
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public String selectHypervisorOperation(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectHypervisorOperation", clientId);
	}
	
	/**
	 * 업데이트 추가/삭제 차단 검색
	 * 
	 * @param ClientLoginVO
	 * @return String
	 * @throws Exception
	 */
	public String selectUpdateOperation(ClientLoginVO clVo) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectUpdateOperation", clVo);
	}
	
	/**
	 * 단말의 패키지명 리스트 검색
	 * 
	 * @param String
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> selectPackageList(String clientId) throws Exception {
		return sqlSessionMeta.selectList("ClientJobManagerDAO.selectPackageList", clientId);
	}
	
	
	/**
	 * 프로파일링 패키지 정보를 저장
	 * 
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public int insertProfilingPackage(List<ProfileVO> profileVoList) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertProfilingPackage", profileVoList);
	}
	
	/**
	 * 프로파일링 패키지 정보를 검색
	 * 
	 * @param String
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> selectProfilingPackageList(String profileNo) throws Exception {
		return sqlSessionMeta.selectList("ClientJobManagerDAO.selectProfilingPackageList", profileNo);
	}
	
	/**
	 * 서버버전을 검색
	 * 
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public String selectServerVersion(String siteName) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectServerVersion", siteName);
	}
	
	
	/**
	 * 패스워드 변경주기 검색
	 * 
	 * @param ClientLoginVO
	 * @return String
	 * @throws Exception
	 */
	public String selectPasswordCycle(ClientLoginVO clVo) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectPasswordCycle", clVo);
	}
	
	/**
	 * 화면잠금시간 검색
	 * 
	 * @param ClientLoginVO
	 * @return String
	 * @throws Exception
	 */
	public String selectScreenTime(ClientLoginVO clVo) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectScreenTime", clVo);
	}
	
	/**
	 * 앱 블랙리스트 검색
	 * 
	 * @param ClientLoginVO
	 * @return List<String>
	 * @throws Exception
	 */
	public List<String> selectAppBlackList(ClientLoginVO clVo) throws Exception {
		return sqlSessionMeta.selectList("ClientJobManagerDAO.selectAppBlackList", clVo);
	}
	
	/**
	 * 홈폴더 초기화 기능 사용 여부 검색
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public String selectHomefolderOperation(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectHomefolderOperation", clientId);
	}
	
	/**
	 * 아이피 화이트리스트를 검색
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public List<String> selectIpWhiteList(String clientId) throws Exception {
		return sqlSessionMeta.selectList("ClientJobManagerDAO.selectIpWhiteList", clientId);
	}
	
	/**
	 * 스케줄 정보를 검색
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public SchedInfoVO selectSchedInfo(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectSchedInfo", clientId);
	}
	
	/**
	 * 스케줄 정보를 저장
	 * @param schedInfo
	 * @return
	 * @throws Exception
	 */
	public int insertSchedInfo(SchedInfoVO schedInfo) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertSchedInfo", schedInfo);
	}
	
	/**
	 * 스케줄 정보를 갱신
	 * @param schedInfo
	 * @return
	 * @throws Exception
	 */
	public int updateSchedInfo(List<SchedInfoVO> schedInfo) throws Exception {
		return sqlSessionMeta.update("ClientJobManagerDAO.updateSchedInfo", schedInfo);
	}
	
	/**
	 * 공지정보 조회
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<NotiVO> selectNoti(ClientLoginVO vo) throws Exception {
		return sqlSessionMeta.selectList("ClientJobManagerDAO.selectNoti", vo);
	}
	
	/**
	 * GPMS DOMAIN 조회
	 * @return
	 * @throws Exception
	 */
	public String selectDefaultNotiDomain() throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectDefaultNotiDomain");
	}
	
	/**
	 * 즉시공지정보 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<NotiVO> selectInstanceNoti(Map<String, Object> params) throws Exception {
		return sqlSessionMeta.selectList("ClientJobManagerDAO.selectInstanceNoti", params);
	}
	
	/**
	 * 대용량 브라우저 로그 저장
	 * @param vos
	 * @return
	 * @throws Exception
	 */
	public int insertLogBrowser(List<LogBrowserVO> vos) throws Exception {
		return sqlSessionMeta.insert("ClientJobManagerDAO.insertLogBrowser", vos);
	}
	
	/**
	 * 클라이언트 패키지정보 삭제
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public int deleteClientPackage(String clientId) throws Exception {
		return sqlSessionMeta.delete("ClientJobManagerDAO.deleteClientPackage", clientId);
	}
	
	/**
	 * 모든 클라이언트 아이디 조회
	 * @return
	 * @throws Exception
	 */
	public List<String> selectAllClients() throws Exception {
		return sqlSessionMeta.selectList("ClientJobManagerDAO.selectAllClients");
	}
	
	/**
	 * 제어판 항목 조회
	 * @param clVo
	 * @return
	 * @throws Exception
	 */
	public List<String> selectControlcenterItems(ClientLoginVO clVo) throws Exception {
		return sqlSessionMeta.selectList("ClientJobManagerDAO.selectControlcenterItems", clVo);
	}
	
	/**
	 * 클라이언트의 폴링타임을 업데이트
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updatePollingTime(PollingTimeVO vo) throws Exception {
		return sqlSessionMeta.update("ClientJobManagerDAO.updatePollingTime", vo);
	}
	
	/**
	 * 루트계정 활성화/비활성화
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public String selectRootUse(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectRootUse", clientId);
	}
	
	/**
	 * SUDO계정 활성화/비활성화
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public String selectSudoUse(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectSudoUse", clientId);
	}
	
	/**
	 * 폴킷 관리자 조회
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public String selectPolkitAdmin(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ClientJobManagerDAO.selectPolkitAdmin", clientId);
	}
}
