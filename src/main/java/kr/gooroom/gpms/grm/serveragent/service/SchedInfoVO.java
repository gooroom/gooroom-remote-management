package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;

public class SchedInfoVO implements Serializable{

	private static final long serialVersionUID = -7931394345073613118L;
	
	String clientId;
	String innerIp;
	String outerIp;
	String mac;
	String banjang;
	String regDt;
	String modDt;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getInnerIp() {
		return innerIp;
	}
	public void setInnerIp(String innerIp) {
		this.innerIp = innerIp;
	}
	public String getOuterIp() {
		return outerIp;
	}
	public void setOuterIp(String outerIp) {
		this.outerIp = outerIp;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getBanjang() {
		return banjang;
	}
	public void setBanjang(String banjang) {
		this.banjang = banjang;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	
	
}
