package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;

public class ProfileVO implements Serializable{

	private static final long serialVersionUID = 6668958076197419302L;
	
	String packageId;
	String profileNo;
	String modDt;
	String packagelastVer;
	String installVer;
	String PackageArch;
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getProfileNo() {
		return profileNo;
	}
	public void setProfileNo(String profileNo) {
		this.profileNo = profileNo;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getPackagelastVer() {
		return packagelastVer;
	}
	public void setPackagelastVer(String packagelastVer) {
		this.packagelastVer = packagelastVer;
	}
	public String getInstallVer() {
		return installVer;
	}
	public void setInstallVer(String installVer) {
		this.installVer = installVer;
	}
	public String getPackageArch() {
		return PackageArch;
	}
	public void setPackageArch(String packageArch) {
		PackageArch = packageArch;
	}
}
