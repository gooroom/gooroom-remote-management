package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;
import java.util.Date;

public class JobVO implements Serializable{

	private static final long serialVersionUID = -1156268382492615495L;

	private String  clientId; 
	private String  job;  
	private String jobTp;

	private String jobNo;
	private String jobName;

	private String jobStatus;
	private String jobData;

	private Date regDate;
	private String regUserId;

	private Date endDate;
	private String runAmount;

	private int clientCount;
	private int compCount;
	private int errorCount;
	private int runCount;
	private int readyCount;
	private int cancelCount;


	public String getJobTp() {
		return jobTp;
	}
	public void setJobTp(String jobTp) {
		this.jobTp = jobTp;
	}

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) { this.job = job; }

	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getJobData() {
		return jobData;
	}
	public void setJobData(String jobData) {
		this.jobData = jobData;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public int getClientCount() {
		return clientCount;
	}
	public void setClientCount(int clientCount) {
		this.clientCount = clientCount;
	}
	public int getCompCount() {
		return compCount;
	}
	public void setCompCount(int compCount) {
		this.compCount = compCount;
	}
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	public int getRunCount() {
		return runCount;
	}
	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}
	public int getReadyCount() {
		return readyCount;
	}
	public void setReadyCount(int readyCount) {
		this.readyCount = readyCount;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getRunAmount() {
		return runAmount;
	}
	public void setRunAmount(String runAmount) {
		this.runAmount = runAmount;
	}
	public int getCancelCount() {
		return cancelCount;
	}
	public void setCancelCount(int cancelCount) {
		this.cancelCount = cancelCount;
	}

}
