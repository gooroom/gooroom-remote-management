package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;


public class PackageVO implements Serializable{

	private static final long serialVersionUID = -6153303439691583467L;
	
	private String clientId;
	private String packageId;
	private String packageArch;
	private String installVer;
	private String packageLastVer;
	private String repoLabel;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getPackageArch() {
		return packageArch;
	}
	public void setPackageArch(String packageArch) {
		this.packageArch = packageArch;
	}
	public String getInstallVer() {
		return installVer;
	}
	public void setInstallVer(String installVer) {
		this.installVer = installVer;
	}
	public String getPackageLastVer() {
		return packageLastVer;
	}
	public void setPackageLastVer(String packageLastVer) {
		this.packageLastVer = packageLastVer;
	}
	public String getRepoLabel() {
		return repoLabel;
	}
	public void setRepoLabel(String repoLabel) {
		this.repoLabel = repoLabel;
	}
	
}
