package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;


public class LogUpdateVO implements Serializable{

	private static final long serialVersionUID = 6987478547926323231L;
	
	private String logSeq = "";
	private String clientId = "";
	private String userId = "";
	private String logItem = "";
	private String basisValue = "";
	private String logValue = "";
	private String extraInfo = "";
	private String regDt = "";
	
	public String getLogSeq() {
		return logSeq;
	}
	public void setLogSeq(String logSeq) {
		this.logSeq = logSeq;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLogItem() {
		return logItem;
	}
	public void setLogItem(String logItem) {
		this.logItem = logItem;
	}
	public String getBasisValue() {
		return basisValue;
	}
	public void setBasisValue(String basisValue) {
		this.basisValue = basisValue;
	}
	public String getLogValue() {
		return logValue;
	}
	public void setLogValue(String logValue) {
		this.logValue = logValue;
	}
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
