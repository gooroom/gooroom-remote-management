package kr.gooroom.gpms.common.service;


import java.io.Serializable;

public class AgentStatusVO implements Serializable{

	private static final long serialVersionUID = -4276539527633969279L;
	
	private String result ;
	private String resultCode ;
	private String message ;
	private String prevAccessDiffTime;
	private String visaStatus;
	
	public String getVisaStatus() {
		return visaStatus;
	}

	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}

	public String getPrevAccessDiffTime() {
		return prevAccessDiffTime;
	}

	public void setPrevAccessDiffTime(String prevAccessTime) {
		this.prevAccessDiffTime = prevAccessTime;
	}

	public void setResultInfo(String result, String resultCode, String message) {
		this.result = result;
		this.resultCode = resultCode;
		this.message = message;		
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrMsg() {
		return message;
	}
	public void setErrMsg(String message) {
		this.message = message;
	}

	
}
