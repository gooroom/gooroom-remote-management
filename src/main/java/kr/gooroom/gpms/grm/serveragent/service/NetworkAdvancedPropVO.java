package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NetworkAdvancedPropVO implements Serializable {

	private String seq;
    private String cmd;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

    public String getCmd() {
	return cmd;
    }

    public void setCmd(String cmd) {
	this.cmd = cmd;
    }

}
