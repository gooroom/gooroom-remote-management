package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serial;
import java.io.Serializable;


public class LogSecurity2VO extends LogBaseVO implements Serializable{

	@Serial
	private static final long serialVersionUID = 4819226489896542652L;

	private String logDt;
	private String logLevel;
	private String logCode;
	private String evalLevel;
	
	public String getEvalLevel() {
		return evalLevel;
	}
	public void setEvalLevel(String evalLevel) {
		this.evalLevel = evalLevel;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLogDt() {
		return logDt;
	}
	public void setLogDt(String logDt) {
		this.logDt = logDt;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getLogCode() {
		return logCode;
	}
	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}
}
