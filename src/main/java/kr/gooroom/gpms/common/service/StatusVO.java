package kr.gooroom.gpms.common.service;

import java.io.Serial;
import java.io.Serializable;

public class StatusVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 4615291217515694128L;
	private String result;
	private String resultCode;
	private String message;

	public StatusVO() {
		this.result = "";
		this.resultCode = "";
		this.message = "";
	}

	public StatusVO(String result, String resultCode, String message) {
		this.result = result;
		this.resultCode = resultCode;
		this.message = message;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
