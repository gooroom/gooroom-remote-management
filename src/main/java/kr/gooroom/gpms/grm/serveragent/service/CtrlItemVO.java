package kr.gooroom.gpms.grm.serveragent.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
public class CtrlItemVO implements Serializable {

    private String objId;

    private String objNm;
    private String mngObjTp;
    private String mngObjTpAbbr;

    private String comment;

    private Date modDate;
    private String modUserId;

    private CtrlPropVO[] propArray;
    private ArrayList<CtrlPropVO> propList;

    public CtrlItemVO() {

    }

    
    public CtrlPropVO[] getPropArray() {
        return propArray;
    }


    public void setPropArray(CtrlPropVO[] propArray) {
        this.propArray = propArray;
    }


    public ArrayList<CtrlPropVO> getPropList() {
        return propList;
    }


    public void setPropList(ArrayList<CtrlPropVO> propList) {
        this.propList = propList;
    }


    public String getObjId() {
	return objId;
    }

    public void setObjId(String objId) {
	this.objId = objId;
    }

    public String getObjNm() {
	return objNm;
    }

    public void setObjNm(String objNm) {
	this.objNm = objNm;
    }

    public String getMngObjTp() {
	return mngObjTp;
    }

    public void setMngObjTp(String mngObjTp) {
	this.mngObjTp = mngObjTp;
    }

    public String getMngObjTpAbbr() {
	return mngObjTpAbbr;
    }

    public void setMngObjTpAbbr(String mngObjTpAbbr) {
	this.mngObjTpAbbr = mngObjTpAbbr;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
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

}
