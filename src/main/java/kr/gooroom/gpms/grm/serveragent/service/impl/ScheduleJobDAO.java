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
	 */
	public int copyFinishedJobTrgtToHist(int storagePeriod) {
		return sqlSessionMeta.insert("ScheduleJobManagerDAO.copyFinishedJobTrgtToHist", storagePeriod);
	}
	
	/**
	 *종료된 JOB타겟을 삭제
	 * 
	 * @param int
	 * @return int
	 */
	public int deleteFinishedJobTrgt(int storagePeriod) {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteFinishedJobTrgt", storagePeriod);
	}
	
	/**
	 *비정상 클라이언트 JOB타겟을 HIST로 복사
	 * 
	 * @param 
	 * @return int
	 */
	public int copyAbnormalClientJobTrgtToHist() {
		return sqlSessionMeta.insert("ScheduleJobManagerDAO.copyAbnormalClientJobTrgtToHist");
	}
	
	/**
	 *비정상 클라이언트 JOB타겟을 삭제
	 * 
	 * @param 
	 * @return int
	 */
	public int deleteAbnormalClientJobTrgt() {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteAbnormalClientJobTrgt");
	}
	
	/**
	 *종료된 JOB마스터를 HIST로 복사
	 * 
	 * @param 
	 * @return int
	 */
	public int copyFinishedJobMstrToHist() {
		return sqlSessionMeta.insert("ScheduleJobManagerDAO.copyFinishedJobMstrToHist");
	}
	
	/**
	 *종료된 JOB마스터를 삭제
	 * 
	 * @param 
	 * @return int
	 */
	public int deleteFinishedJobMstr() {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteFinishedJobMstr");
	}
	
	/**
	 *스케쥴러가 생성한 종료된 SCHED JOB타겟을 삭제
	 * 
	 * @param 
	 * @return int
	 */
	public int deleteFinishedSchedJobTrgt() {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteFinishedSchedJobTrgt");
	}
	
	/**
	 *스케쥴러가 생성한 종료된 SCHED JOB마스터를 삭제
	 * 
	 * @param 
	 * @return int
	 */
	public int deleteFinishedSchedJobMstr() {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteFinishedSchedJobMstr");
	}
	
	/**
	 *모든 클라이언트에서 종료된 JOB타겟을 BK로 복사
	 * 
	 * @param HashMap<String, Integer>
	 * @return int
	 */
	public int copyAllFinishedJobTrgtToBk(HashMap<String,Integer> periods) {
		return sqlSessionMeta.insert("ScheduleJobManagerDAO.copyAllFinishedJobTrgtToBk", periods);
	}
	
	/**
	 *모든 클라이언트에서 종료된 JOB타겟을 삭제
	 * 
	 * @param HashMap<String, Integer>
	 * @return int
	 */
	public int deleteAllFinishedJobTrgt(HashMap<String, Integer> periods) {
		return sqlSessionMeta.delete("ScheduleJobManagerDAO.deleteAllFinishedJobTrgt", periods);
	}
	
}
