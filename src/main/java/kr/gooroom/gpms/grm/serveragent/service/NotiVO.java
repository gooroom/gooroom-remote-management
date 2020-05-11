package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;


public class NotiVO implements Serializable{

	private static final long serialVersionUID = 8809987938774277337L;
	
	String url;
	String title;
	String viewType;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
	
}
