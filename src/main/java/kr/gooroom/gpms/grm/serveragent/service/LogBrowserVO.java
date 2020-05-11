package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;


public class LogBrowserVO implements Serializable{

	private static final long serialVersionUID = -9016270243313529052L;
	
	String clientId;
	String userId;
	String logDt;
	String logTp;
	String url;
	
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
	public String getLogDt() {
		return logDt;
	}
	public void setLogDt(String logDt) {
		this.logDt = logDt;
	}
	public String getLogTp() {
		return logTp;
	}
	public void setLogTp(String logTp) {
		this.logTp = logTp;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
