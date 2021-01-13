package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class NetworkPropVO implements Serializable {

    private String ipaddress;
    private String state;
    private String direction;
    private String src_ports;
    private String dst_ports;
    private String protocol;

    public String getIpaddress() {
	return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
	this.ipaddress = ipaddress;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getDirection() {
	return direction;
    }

    public void setDirection(String direction) {
	this.direction = direction;
    }

    public String getSrc_ports() {
	return src_ports;
    }

    public void setSrc_ports(String src_ports) {
	this.src_ports = src_ports;
    }

    public String getDst_ports() {
	return dst_ports;
    }

    public void setDst_ports(String dst_ports) {
	this.dst_ports = dst_ports;
    }

    public String getProtocol() {
	return protocol;
    }

    public void setProtocol(String protocol) {
	this.protocol = protocol;
    }

}
