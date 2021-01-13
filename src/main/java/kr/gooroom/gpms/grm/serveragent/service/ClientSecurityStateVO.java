package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;

public class ClientSecurityStateVO implements Serializable{

	private static final long serialVersionUID = -9155087949263920691L;
	
	private String clientId;
	private String safeScore;
	private String osStatus;
	private String exeStatus;
	private String bootStatus;
	private String mediaStatus;
	private String osRun;
	private String exeRun;
	private String bootRun;
	private String mediaRun;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSafeScore() {
		return safeScore;
	}
	public void setSafeScore(String safeScore) {
		this.safeScore = safeScore;
	}
	public String getOsStatus() {
		return osStatus;
	}
	public void setOsStatus(String osStatus) {
		this.osStatus = osStatus;
	}
	public String getExeStatus() {
		return exeStatus;
	}
	public void setExeStatus(String exeStatus) {
		this.exeStatus = exeStatus;
	}
	public String getBootStatus() {
		return bootStatus;
	}
	public void setBootStatus(String bootStatus) {
		this.bootStatus = bootStatus;
	}
	public String getMediaStatus() {
		return mediaStatus;
	}
	public void setMediaStatus(String mediaStatus) {
		this.mediaStatus = mediaStatus;
	}
	public String getOsRun() {
		return osRun;
	}
	public void setOsRun(String osRun) {
		this.osRun = osRun;
	}
	public String getExeRun() {
		return exeRun;
	}
	public void setExeRun(String exeRun) {
		this.exeRun = exeRun;
	}
	public String getBootRun() {
		return bootRun;
	}
	public void setBootRun(String bootRun) {
		this.bootRun = bootRun;
	}
	public String getMediaRun() {
		return mediaRun;
	}
	public void setMediaRun(String mediaRun) {
		this.mediaRun = mediaRun;
	}
	
}
