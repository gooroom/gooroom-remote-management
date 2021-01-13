package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class CtrlPropVO implements Serializable {

    private String objId;

    private String propId;
    private String propNm;
    private String propValue;

    private String link;

    private Date modDate;
    private String modUserId;

    public CtrlPropVO() {

    }

    public CtrlPropVO(String objId, String propId, String propNm, String propValue, String link, String modUserId) {
	this.setObjId(objId);
	this.setPropId(propId);
	this.setPropNm(propNm);
	this.setPropValue(propValue);
	this.setLink(link);
	this.setModUserId(modUserId);
    }

    public String getObjId() {
	return objId;
    }

    public void setObjId(String objId) {
	this.objId = objId;
    }

    public String getPropId() {
	return propId;
    }

    public void setPropId(String propId) {
	this.propId = propId;
    }

    public String getPropNm() {
	return propNm;
    }

    public void setPropNm(String propNm) {
	this.propNm = propNm;
    }

    public String getPropValue() {
	return propValue;
    }

    public void setPropValue(String propValue) {
	this.propValue = propValue;
    }

    public Date getModDate() {
	return modDate;
    }

    public void setModDate(Date modDate) {
	this.modDate = modDate;
    }

    public String getModUserId() {
	return modUserId;
    }

    public void setModUserId(String modUserId) {
	this.modUserId = modUserId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    
}
