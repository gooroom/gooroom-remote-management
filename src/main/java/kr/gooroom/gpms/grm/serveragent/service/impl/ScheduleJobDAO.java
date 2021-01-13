package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Repository;
import kr.gooroom.gpms.common.service.dao.SqlSessionMetaDAO;

/**
 * @Class Name : ScheduleJobDAO.java
 * @Description : ScheduleJobDAO Class
 * @Modification Information
 * @
 * @ 수정일       수정자     수정내용
 * @ ---------- --------- -------------------------------
 * @ 2017.05.08 gooroom     최초생성
 *
 * @since 2017.05.08
 * @version 1.0
 * @see
 *
 */
@Repository("scheduleJobDAO")
public class ScheduleJobDAO extends SqlSessionMetaDAO {
	
	/**
	 *종료된 JOB타겟을 HIST로 복사
	 * 
	 * @param int
	 * @return int
	 * @throws Exception
	 */
	public int copyFinishedJobTrgtToHist(int storagePeriod) throws Exception {
		return sqlSessionMeta.insert("ScheduleJobManagerDAO.copyFinishedJobTrgtToHist", storagePeriod);
	}
	
	/**
	 *종료된 JOB타겟을 삭제
	 * 
	 * @param int
	 * @return int
	 * @throws Exception
	 */
	public int deleteFinishedJobTrgt(int storagePeriod) throws Exception {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteFinishedJobTrgt", storagePeriod);
	}
	
	/**
	 *비정상 클라이언트 JOB타겟을 HIST로 복사
	 * 
	 * @param 
	 * @return int
	 * @throws Exception
	 */
	public int copyAbnormalClientJobTrgtToHist() throws Exception {
		return sqlSessionMeta.insert("ScheduleJobManagerDAO.copyAbnormalClientJobTrgtToHist");
	}
	
	/**
	 *비정상 클라이언트 JOB타겟을 삭제
	 * 
	 * @param 
	 * @return int
	 * @throws Exception
	 */
	public int deleteAbnormalClientJobTrgt() throws Exception {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteAbnormalClientJobTrgt");
	}
	
	/**
	 *종료된 JOB마스터를 HIST로 복사
	 * 
	 * @param 
	 * @return int
	 * @throws Exception
	 */
	public int copyFinishedJobMstrToHist() throws Exception {
		return sqlSessionMeta.insert("ScheduleJobManagerDAO.copyFinishedJobMstrToHist");
	}
	
	/**
	 *종료된 JOB마스터를 삭제
	 * 
	 * @param 
	 * @return int
	 * @throws Exception
	 */
	public int deleteFinishedJobMstr() throws Exception {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteFinishedJobMstr");
	}
	
	/**
	 *스케쥴러가 생성한 종료된 SCHED JOB타겟을 삭제
	 * 
	 * @param 
	 * @return int
	 * @throws Exception
	 */
	public int deleteFinishedSchedJobTrgt() throws Exception {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteFinishedSchedJobTrgt");
	}
	
	/**
	 *스케쥴러가 생성한 종료된 SCHED JOB마스터를 삭제
	 * 
	 * @param 
	 * @return int
	 * @throws Exception
	 */
	public int deleteFinishedSchedJobMstr() throws Exception {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteFinishedSchedJobMstr");
	}
	
	/**
	 *모든 클라이언트에서 종료된 JOB타겟을 BK로 복사
	 * 
	 * @param HashMap<String, Integer>
	 * @return int
	 * @throws Exception
	 */
	public int copyAllFinishedJobTrgtToBk(HashMap<String,Integer> periods) throws Exception {
		return sqlSessionMeta.insert("ScheduleJobManagerDAO.copyAllFinishedJobTrgtToBk", periods);
	}
	
	/**
	 *모든 클라이언트에서 종료된 JOB타겟을 삭제
	 * 
	 * @param HashMap<String, Integer>
	 * @return int
	 * @throws Exception
	 */
	public int deleteAllFinishedJobTrgt(HashMap<String, Integer> periods) throws Exception {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteAllFinishedJobTrgt", periods);
	}
	
}
