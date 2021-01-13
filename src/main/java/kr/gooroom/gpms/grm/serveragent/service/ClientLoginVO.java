package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;

public class ClientLoginVO implements Serializable{

	private static final long serialVersionUID = 2088729669403090906L;
	private String clientId;
	private String loginId;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
}
