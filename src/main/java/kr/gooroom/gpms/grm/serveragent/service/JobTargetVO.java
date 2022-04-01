package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;

public class JobTargetVO implements Serializable{

	private static final long serialVersionUID = -668431984943160940L;
	
	String clientId;
	String jobNo;
	String resultData;
	String modDt;
	String modUserId;
	String jobStat;
	
	public String getClientId() { return clientId; }
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) { this.jobNo = jobNo;}
	public String getResultData() {
		return resultData;
	}
	public void setResultData(String resultData) {
		this.resultData = resultData;
	}
	public String getModDt() { return modDt; }
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModUserId() {
		return modUserId;
	}
	public void setModUserId(String modUserId) {
		this.modUserId = modUserId;
	}
	public String getJobStat() {
		return jobStat;
	}
	public void setJobStat(String jobStat) {
		this.jobStat = jobStat;
	}
}