package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serial;
import java.io.Serializable;

public class NetworkAdvancedPropVO implements Serializable {

	@Serial
	private static final long serialVersionUID = -1594914635296788007L;
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
