package kr.gooroom.gpms.grm.serveragent.service;

import java.util.HashMap;

public interface ScheduleJobService {
	public int copyFinishedJobTrgtToHist(int storagePeriod) throws Exception;
	public int deleteFinishedJobTrgt(int storagePeriod) throws Exception;
	public int copyAbnormalClientJobTrgtToHist() throws Exception;
	public int deleteAbnormalClientJobTrgt() throws Exception;
	public int copyFinishedJobMstrToHist() throws Exception;
	public int deleteFinishedJobMstr() throws Exception;
	public int deleteFinishedSchedJobTrgt() throws Exception;
	public int deleteFinishedSchedJobMstr() throws Exception;
	public int copyAllFinishedJobTrgtToBk(HashMap<String, Integer> periods) throws Exception;
	public int deleteAllFinishedJobTrgt(HashMap<String, Integer> periods) throws Exception;
}