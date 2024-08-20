package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.util.HashMap;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import kr.gooroom.gpms.grm.serveragent.service.ScheduleJobService;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Resource(name = "scheduleJobDAO")
    private ScheduleJobDAO scheduleJobDAO;
	
	@Override
	public int copyFinishedJobTrgtToHist(int storagePeriod) {
		return scheduleJobDAO.copyFinishedJobTrgtToHist(storagePeriod);
	}
	
	@Override
	public int deleteFinishedJobTrgt(int storagePeriod) {
		return scheduleJobDAO.deleteFinishedJobTrgt(storagePeriod);
	}
	
	@Override
	public int copyAbnormalClientJobTrgtToHist() {
		return scheduleJobDAO.copyAbnormalClientJobTrgtToHist();
	}
	
	@Override
	public int deleteAbnormalClientJobTrgt() {
		return scheduleJobDAO.deleteAbnormalClientJobTrgt();
	}
	
	@Override
	public int copyFinishedJobMstrToHist() {
		return scheduleJobDAO.copyFinishedJobMstrToHist();
	}
	
	@Override
	public int deleteFinishedJobMstr() {
		return scheduleJobDAO.deleteFinishedJobMstr();
	}
	
	@Override
	public int deleteFinishedSchedJobTrgt() {
		return scheduleJobDAO.deleteFinishedSchedJobTrgt();
	}
	
	@Override
	public int deleteFinishedSchedJobMstr() {
		return scheduleJobDAO.deleteFinishedSchedJobMstr();
	}
	
	@Override
	public int copyAllFinishedJobTrgtToBk(HashMap<String, Integer> periods) {
		return scheduleJobDAO.copyAllFinishedJobTrgtToBk(periods);
	}
	
	@Override
	public int deleteAllFinishedJobTrgt(HashMap<String, Integer> periods) {
		return scheduleJobDAO.deleteAllFinishedJobTrgt(periods);
	}
}
