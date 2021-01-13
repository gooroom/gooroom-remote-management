package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;

public class JobVO implements Serializable{

	private static final long serialVersionUID = -1156268382492615495L;

	private int jobNo = 0;
	private String  clientId; 
	private String  job;  
	private String jobTp;
	
	public String getJobTp() {
		return jobTp;
	}
	public void setJobTp(String jobTp) {
		this.jobTp = jobTp;
	}
	public int getJobNo() {
		return jobNo;
	}
	public void setJobNo(int jobNo) {
		this.jobNo = jobNo;
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
	public void setJob(String job) {
		this.job = job;
	}
	

		
}
