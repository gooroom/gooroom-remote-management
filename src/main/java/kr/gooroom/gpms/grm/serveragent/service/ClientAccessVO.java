package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;


public class ClientAccessVO implements Serializable{

	private static final long serialVersionUID = -6628326740856116338L;
	
	private String clientId;
	private String userId;
	private Double maxRspTime;
	private Integer timeoutCnt;
	private Integer pollingTime;
	
	public Integer getPollingTime() {
		return pollingTime;
	}
	public void setPollingTime(Integer pollingTime) {
		this.pollingTime = pollingTime;
	}
	public Double getMaxRspTime() {
		return maxRspTime;
	}
	public void setMaxRspTime(Double maxRspTime) {
		this.maxRspTime = maxRspTime;
	}
	public Integer getTimeoutCnt() {
		return timeoutCnt;
	}
	public void setTimeoutCnt(Integer timeoutCnt) {
		this.timeoutCnt = timeoutCnt;
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
	
	
}
