package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;


public class AuthVO implements Serializable{

	private static final long serialVersionUID = 3538239955449723982L;
	
	private String trmutId;
	private String trmutNm;
	private String keyInfo;
	private String trmutStat;
	
	
	public String getTrmutId() {
		return trmutId;
	}
	public void setTrmutId(String trmutId) {
		this.trmutId = trmutId;
	}
	public String getTrmutNm() {
		return trmutNm;
	}
	public void setTrmutNm(String trmutNm) {
		this.trmutNm = trmutNm;
	}
	public String getKeyInfo() {
		return keyInfo;
	}
	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}
	public String getTrmutStat() {
		return trmutStat;
	}
	public void setTrmutStat(String trmutStat) {
		this.trmutStat = trmutStat;
	}
		
}
