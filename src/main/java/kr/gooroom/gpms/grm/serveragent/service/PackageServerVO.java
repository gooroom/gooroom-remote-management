package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;


public class PackageServerVO implements Serializable{

	private static final long serialVersionUID = 1064904938599827894L;
	
	private String mainOsRepo;
	private String baseOsRepo;
	private String repoPreference;
	
	public String getMainOsRepo() {
		return mainOsRepo;
	}
	public void setMainOsRepo(String mainOsRepo) {
		this.mainOsRepo = mainOsRepo;
	}
	public String getBaseOsRepo() {
		return baseOsRepo;
	}
	public void setBaseOsRepo(String baseOsRepo) {
		this.baseOsRepo = baseOsRepo;
	}
	public String getRepoPreference() {
		return repoPreference;
	}
	public void setRepoPreference(String repoPreference) {
		this.repoPreference = repoPreference;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
