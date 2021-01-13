package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import kr.gooroom.gpms.grm.serveragent.service.ScheduleJobService;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Resource(name = "scheduleJobDAO")
    private ScheduleJobDAO scheduleJobDAO;
	
	@Override
	public int copyFinishedJobTrgtToHist(int storagePeriod) throws Exception {
		return scheduleJobDAO.copyFinishedJobTrgtToHist(storagePeriod);
	}
	
	@Override
	public int deleteFinishedJobTrgt(int storagePeriod) throws Exception {
		return scheduleJobDAO.deleteFinishedJobTrgt(storagePeriod);
	}
	
	@Override
	public int copyAbnormalClientJobTrgtToHist() throws Exception {
		return scheduleJobDAO.copyAbnormalClientJobTrgtToHist();
	}
	
	@Override
	public int deleteAbnormalClientJobTrgt() throws Exception {
		return scheduleJobDAO.deleteAbnormalClientJobTrgt();
	}
	
	@Override
	public int copyFinishedJobMstrToHist() throws Exception {
		return scheduleJobDAO.copyFinishedJobMstrToHist();
	}
	
	@Override
	public int deleteFinishedJobMstr() throws Exception {
		return scheduleJobDAO.deleteFinishedJobMstr();
	}
	
	@Override
	public int deleteFinishedSchedJobTrgt() throws Exception {
		return scheduleJobDAO.deleteFinishedSchedJobTrgt();
	}
	
	@Override
	public int deleteFinishedSchedJobMstr() throws Exception {
		return scheduleJobDAO.deleteFinishedSchedJobMstr();
	}
	
	@Override
	public int copyAllFinishedJobTrgtToBk(HashMap<String, Integer> periods) throws Exception {
		return scheduleJobDAO.copyAllFinishedJobTrgtToBk(periods);
	}
	
	@Override
	public int deleteAllFinishedJobTrgt(HashMap<String, Integer> periods) throws Exception {
		return scheduleJobDAO.deleteAllFinishedJobTrgt(periods);
	}
}
