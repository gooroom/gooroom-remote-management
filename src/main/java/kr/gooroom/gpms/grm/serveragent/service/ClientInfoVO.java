package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;


public class ClientInfoVO implements Serializable{

	private static final long serialVersionUID = -4336921637490272150L;
	
	private String clientId;
	private String mac1;
	private String mac2;
	private String ip;
	private String os;
	private String kernel;
	private String score;
	private int fileNumOfScore;
	private String localIp;
	private long strgUse;
	private long strgSize;
	
	public String getLocalIp() {
		return localIp;
	}
	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}
	public long getStrgUse() {
		return strgUse;
	}
	public void setStrgUse(long strgUse) {
		this.strgUse = strgUse;
	}
	public long getStrgSize() {
		return strgSize;
	}
	public void setStrgSize(long strgSize) {
		this.strgSize = strgSize;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getMac1() {
		return mac1;
	}
	public void setMac1(String mac1) {
		this.mac1 = mac1;
	}
	public String getMac2() {
		return mac2;
	}
	public void setMac2(String mac2) {
		this.mac2 = mac2;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getKernel() {
		return kernel;
	}
	public void setKernel(String kernel) {
		this.kernel = kernel;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public int getFileNumOfScore() {
		return fileNumOfScore;
	}
	public void setFileNumOfScore(int fileNumOfScore) {
		this.fileNumOfScore = fileNumOfScore;
	}

	
}
