package kr.gooroom.gpms.common.utils;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.gooroom.gpms.grm.serveragent.service.ScheduleJobService;

@Component
public class JobScheduler {
	
    @Resource(name = "scheduleJobService")
    private ScheduleJobService scheduleJobService;
    
    //private static final int STORAGE_PERIOD = 3; 	//days
    private static final int DOING_PERIOD = 3;		//days
    private static final int READY_PERIOD = 180;	//days
    private static final boolean SCHED_MODE = false;
    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);
    
	@Scheduled(cron="0 */5 * * * *")
	void afterJob() {
		logger.info("[AFTER JOB] Doing...");

		try {
			/*
			 * 종료된 JOB TARGET 지우기
			 * JOB 상태가 Complete,Error인 것들은 즉시 HISTORY로 복사 후 삭제.
			 * JOB 상태가 Doing인 것들은 저장기간이 지난 것들만 복사 후 삭제.
			 * JOB 상태가 Ready인 것들은 지우지 않는 것이 정책.
			scheduleJobService.copyFinishedJobTrgtToHist(STORAGE_PERIOD);
			scheduleJobService.deleteFinishedJobTrgt(STORAGE_PERIOD);
			logger.info("[AFTER JOB] move finished jobs from job_trgt to job_trgt_hist");
			*/
			
			
			/*
			 * 종료된 JOB TARGET 지우기
			 * JOB상태가 
			 * Complete 이거나
			 * Error 이거나
			 * Quit 이거나
			 * Doing-저장기간이 지난 Doing이거나
			 * Ready-저장기간이 지난 Ready 상태인
			 * 동일한 job_id를 가진 모든 타겟을 BK로 복사 후 삭제
			 */
			HashMap<String, Integer> periods = new HashMap<String, Integer>();
			periods.put("doingPeriod", DOING_PERIOD);
			periods.put("readyPeriod",  READY_PERIOD);
			scheduleJobService.copyAllFinishedJobTrgtToBk(periods);
			scheduleJobService.deleteAllFinishedJobTrgt(periods);
			logger.info("[AFTER JOB] move all finished jobs from job_trgt to job_trgt_bk");
			
			/*
			 * 인증서가 폐기되었거나 만료된 클라이언트의 JOB TARGET 지우기
			 */
			//scheduleJobService.copyAbnormalClientJobTrgtToHist();
			//scheduleJobService.deleteAbnormalClientJobTrgt();
			//logger.info("[AFTER JOB] move devoked|expired jobs from job_trgt to job_trgt_bk");
			
			/*
			 * 종료된 JOB MASTER 지우기
			 * JOB TARGET이 더이상 존재하지않는 JOB MASTER를 HISTORY로 복사 후 삭제
			 */
			scheduleJobService.copyFinishedJobMstrToHist();
			scheduleJobService.deleteFinishedJobMstr();
			logger.info("[AFTER JOB] move finished jobs from job_mstr to job_mstr_bk");
			
			if (SCHED_MODE) {
				/*
				 * 종료된 SCHED JOB TARGET 지우기
				 * JOB MASTER의 상태가 C,QC,E인 JOB TARGET 삭제
				 */
				//scheduleJobService.deleteFinishedSchedJobTrgt();
				//logger.info("[AFTER JOB] delete finished sched-jobs from sched_job_trgt");
				
				/*
				 * 종료된 SCHED JOB MASTER 지우기
				 * JOB MASTER의 상태가 C,QC,E인 JOB MASTER 삭제
				 */
				//scheduleJobService.deleteFinishedSchedJobMstr();
				//logger.info("[AFTER JOB] delete finished sched-jobs from sched_job_mstr");
			}
			
			logger.info("[AFTER JOB] Done.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}