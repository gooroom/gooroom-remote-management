package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;


public class LogSecurity2VO implements Serializable{

	private static final long serialVersionUID = 4819226489896542652L;
	
	private String logSeq;
	private String clientId;
	private String userId;
	private String logItem;
	private String logTp;
	private String logValue;
	private String extraInfo;
	private String regDt;
	private String logDt;
	private String logLevel;
	private String logCode;
	private String evalLevel;
	
	public String getEvalLevel() {
		return evalLevel;
	}
	public void setEvalLevel(String evalLevel) {
		this.evalLevel = evalLevel;
	}
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
	public String getLogTp() {
		return logTp;
	}
	public void setLogTp(String logTp) {
		this.logTp = logTp;
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
	public String getLogDt() {
		return logDt;
	}
	public void setLogDt(String logDt) {
		this.logDt = logDt;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getLogCode() {
		return logCode;
	}
	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}
}
