package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;

public class JobResultVO implements Serializable{

	private static final long serialVersionUID = -1156268382492615495L;

	private int jobNo = 0;   // job 구분번호
	private String  trmutId ;  // 클라이언트 구분값
	private String  resultData ;  // job 처리 내역 
	public int getJobNo() {
		return jobNo;
	}
	public void setJobNo(int jobNo) {
		this.jobNo = jobNo;
	}
	public String getTrmutId() {
		return trmutId;
	}
	public void setTrmutId(String trmutId) {
		this.trmutId = trmutId;
	}
	public String getResultData() {
		return resultData;
	}
	public void setResultData(String resultData) {
		this.resultData = resultData;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
