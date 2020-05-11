package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;

import kr.gooroom.gpms.common.service.dao.SqlSessionMetaDAO;
import kr.gooroom.gpms.grm.serveragent.service.ClientAccessVO;
import kr.gooroom.gpms.grm.serveragent.service.JobTargetVO;
import kr.gooroom.gpms.grm.serveragent.service.JobVO;

/**
 * @Class Name : ServerJobDAO.java
 * @Description : ServerJobDAO Class
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
@Repository("serverJobDAO")
public class ServerJobDAO extends SqlSessionMetaDAO {

	/**
	 * 클라이언트 Job 대기 목록
	 * 
	 * @param String clientId
	 * @return List<JobVO>
	 * @throws Exception
	 */
	public List<JobVO> selectListForClientReady(String clientId) throws Exception {
		return sqlSessionMeta.selectList("ServerJobManagerDAO.selectListForClientReady", clientId);
	}

	/**
	 * 클라이언트 Job 상태를 ready 에서 doing으로 업데이트
	 * 
	 * @param HashMap<String, Object> param
	 * @return int
	 * @throws Exception
	 */
	public int updateReadyToDoing(HashMap<String, Object> param) throws Exception {
		return sqlSessionMeta.update("ServerJobManagerDAO.updateReadyToDoing", param);
	}
	
	/**
	 * 클라이언트 Job 상태를 doing 에서 complete로 업데이트
	 * 
	 * @param JobVO job
	 * @return int
	 * @throws Exception
	 */
	public int updateDoingToComplete(JobVO job) throws Exception {
		return sqlSessionMeta.update("ServerJobManagerDAO.updateDoingToComplete", job);
	}
	
	/**
	 * 클라이언트 Job 결과를 삽입
	 * 
	 * @param JobVO job
	 * @return int
	 * @throws Exception
	 */
	public int insertOrUpdateJobResult(JobTargetVO jobTargetVo) throws Exception {
		return sqlSessionMeta.insert("ServerJobManagerDAO.insertOrUpdateJobResult", jobTargetVo);
	}
	
	/**
	 * 클라이언트 접속상태 갱신
	 * 
	 * @param ClientAccessVO cav
	 * @return int
	 * @throws Exception
	 */
	public int insertOrUpdateClientConnStatus(ClientAccessVO cav) throws Exception {
		return sqlSessionMeta.update("ServerJobManagerDAO.insertOrUpdateClientConnStatus", cav);
	}
	
	/**
	 * 클라이언트 Job 상태를 doing 에서 error로 업데이트
	 * 
	 * @param JobVO job
	 * @return int
	 * @throws Exception
	 */
	public int updateDoingToError(JobVO job) throws Exception {
		return sqlSessionMeta.update("ServerJobManagerDAO.updateDoingToError", job);
	}
	
	/**
	 * 온라인유저 클라이언트 접속상태 갱신
	 * 
	 * @param ClientAccessVO cav
	 * @return int
	 * @throws Exception
	 */
	public int insertOrUpdateOnlineClientConnStatus(ClientAccessVO cav) throws Exception {
		return sqlSessionMeta.update("ServerJobManagerDAO.insertOrUpdateOnlineClientConnStatus", cav);
	}
	
	/**
	 * 로컬유저 클라이언트 접속상태 갱신
	 * 
	 * @param ClientAccessVO cav
	 * @return int
	 * @throws Exception
	 */
	public int insertOrUpdateLocalClientConnStatus(ClientAccessVO cav) throws Exception {
		return sqlSessionMeta.update("ServerJobManagerDAO.insertOrUpdateLocalClientConnStatus", cav);
	}
	
	/**
	 * 미로그인 클라이언트 접속상태 갱신
	 * 
	 * @param ClientAccessVO cav
	 * @return int
	 * @throws Exception
	 */
	public int insertOrUpdateNotLogginedClientConnStatus(ClientAccessVO cav) throws Exception {
		return sqlSessionMeta.update("ServerJobManagerDAO.insertOrUpdateNotLogginedClientConnStatus", cav);
	}
	
	/**
	 * 클라이언트의 JOB이 존재하는지 확인
	 * 
	 * @param String clientId
	 * @return int
	 * @throws Exception
	 */
	public int selectIsJob(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ServerJobManagerDAO.selectIsJob", clientId);
	}
	
	/**
	 * JOB이 존재하지 않음을 표시
	 * 
	 * @param String clientId
	 * @return int
	 * @throws Exception
	 */
	public int updateIsJobTo0(String clientId) throws Exception {
		return sqlSessionMeta.update("ServerJobManagerDAO.updateIsJobTo0", clientId);
	}
	
	/**
	 * 온라인사용자의 이전 엑세스시간을 조회
	 * 
	 * @param String clientId
	 * @return String
	 * @throws Exception
	 */
	public String selectOnlineAccessDiffTime(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ServerJobManagerDAO.selectOnlineAccessDiffTime", clientId);
	}
	
	/**
	 * 로컬사용자의 이전 엑세스시간을 조회
	 * 
	 * @param String clientId
	 * @return String
	 * @throws Exception
	 */
	public String selectLocalAccessDiffTime(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ServerJobManagerDAO.selectLocalAccessDiffTime", clientId);
	}
	
	/**
	 * 로그인하지않은 클라이언트의 이전 엑세스시간을 조회
	 * @param String clientId
	 * @return String
	 * @throws Exception
	 */
	public String selectNotLoginAccessDiffTime(String clientId) throws Exception {
		return sqlSessionMeta.selectOne("ServerJobManagerDAO.selectNotLoginAccessDiffTime", clientId);
	}
	
	/**
	 * 전체 JOB일 때 GRM이 job_trgt에 row를 생성
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertJobTrgt(HashMap<String, Object> param) throws Exception {
		return sqlSessionMeta.insert("ServerJobManagerDAO.insertJobTrgt", param);
	}
	
	/**
	 * client_access 테이블에 polling_time 칼럼의 존재 유무 확인
	 * @return
	 * @throws Exception
	 */
	public int selectPollingTimeColumnCntInClientAccess() throws Exception {
		return sqlSessionMeta.selectOne("ServerJobManagerDAO.selectPollingTimeColumnCntInClientAccess");
	}
}
