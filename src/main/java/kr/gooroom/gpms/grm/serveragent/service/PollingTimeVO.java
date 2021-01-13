package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;


public class PollingTimeVO implements Serializable{

	private static final long serialVersionUID = -9095168071704047965L;
	
	String clientId;
	String pollingTime;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPollingTime() {
		return pollingTime;
	}
	public void setPollingTime(String pollingTime) {
		this.pollingTime = pollingTime;
	}
}
