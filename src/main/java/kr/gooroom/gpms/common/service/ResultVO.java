package kr.gooroom.gpms.common.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResultVO implements Serializable {

	private StatusVO status;
	private Object[] data;

	public ResultVO() {
		
		status = null;
		data = null;
	}


	public ResultVO(String result, String resultcode, String errorMessage) {
		
		status = new StatusVO(result, resultcode, errorMessage);

		data = null;
	}

	public void setResultInfo(StatusVO status, Object[] data) {
		this.status = status;
		this.data = data;
	}

	public StatusVO getStatus() {
		return status;
	}

	public void setStatus(StatusVO status) {
		this.status = status;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

}