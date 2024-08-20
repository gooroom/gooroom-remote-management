package kr.gooroom.gpms.grm.serveragent.service;

import java.util.HashMap;

public interface ScheduleJobService {
	int copyFinishedJobTrgtToHist(int storagePeriod) throws Exception;
	int deleteFinishedJobTrgt(int storagePeriod) throws Exception;
	int copyAbnormalClientJobTrgtToHist() throws Exception;
	int deleteAbnormalClientJobTrgt() throws Exception;
	int copyFinishedJobMstrToHist() throws Exception;
	int deleteFinishedJobMstr() throws Exception;
	int deleteFinishedSchedJobTrgt() throws Exception;
	int deleteFinishedSchedJobMstr() throws Exception;
	int copyAllFinishedJobTrgtToBk(HashMap<String, Integer> periods) throws Exception;
	int deleteAllFinishedJobTrgt(HashMap<String, Integer> periods) throws Exception;
}