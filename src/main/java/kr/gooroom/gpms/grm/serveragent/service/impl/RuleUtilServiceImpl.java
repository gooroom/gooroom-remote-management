package kr.gooroom.gpms.grm.serveragent.service.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import kr.gooroom.gpms.common.utils.Constant;
import kr.gooroom.gpms.grm.serveragent.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.gooroom.gpms.common.service.ResultVO;
import kr.gooroom.gpms.common.service.StatusVO;
import kr.gooroom.gpms.common.utils.GPMSConstants;

/**
 * @Class Name : CtrlMstServiceImpl.java
 * @Description :
 * @Modification Information
 *
 * @author
 * @since 2017-06-05
 * @version 1.0
 * @see
 * 
 *      Copyright (C) All right reserved.
 */

@Service("ruleUtilService")
public class RuleUtilServiceImpl implements RuleUtilService {

    private static final Logger logger = LoggerFactory.getLogger(RuleUtilServiceImpl.class);

    @Resource(name = "ruleUtilDAO")
    private RuleUtilDAO ruleUtilDAO;

	@Resource(name = "clientJobService")
	private ClientJobService clientJobService;

    private ResultVO readCtrlItem(String objId) throws Exception {

	ResultVO resultVO = new ResultVO();
	try {
	    List<CtrlPropVO> propRe = ruleUtilDAO.selectCtrlPropList(objId);
	    CtrlItemVO re = ruleUtilDAO.selectCtrlItem(objId);
	    if (re != null) {
		CtrlPropVO[] props = new CtrlPropVO[propRe.size()];
		props = propRe.toArray(props);
		re.setPropArray(props);
		CtrlItemVO[] row = new CtrlItemVO[1];
		row[0] = re;
		resultVO.setData(row);
		resultVO.setStatus(new StatusVO(GPMSConstants.MSG_SUCCESS, "GRSM0000", "상세 정보 조회"));
	    } else {
		Object[] o = new Object[0];
		resultVO.setData(o);
		resultVO.setStatus(new StatusVO(GPMSConstants.MSG_FAIL, "GRSM4065", "정보가 조회되지 않았습니다."));
	    }
	} catch (Exception ex) {
	    resultVO.setStatus(new StatusVO(GPMSConstants.MSG_FAIL, "ERR999", "서버에 오류가 발생하였습니다."));
	    logger.error("RuleUtilServiceImpl.readCtrlItem Exception occurred. ", ex);
	}

	return resultVO;
    }

    private String getCtrlPropValue(CtrlPropVO[] props, String propName) {
	if (props != null && props.length > 0 && propName != null) {
	    for (CtrlPropVO prop : props) {
		if (propName.equals(prop.getPropNm())) {
		    return prop.getPropValue();
		}
	    }
	}
	return "";
    }

    /**
     * 권한 조회 - JSON OBJECT
     * 
     * @param String objId
     * @return HashMap<String, Object>
     * @throws Exception
     */
    private HashMap<String, Object> readClientCtrlRuleInfo(String objId) throws Exception {

	ResultVO resultVO = null;
	HashMap<String, Object> hm = new HashMap<String, Object>();

	try {

	    resultVO = readCtrlItem(objId);
	    hm.put("masterResult", resultVO);

	    CtrlItemVO[] data = (CtrlItemVO[]) resultVO.getData();
	    if (data != null && data.length > 0) {
		CtrlPropVO[] props = data[0].getPropArray();
		if (props != null && props.length > 0) {
		    for (CtrlPropVO vo : props) {

			if ("firewallBundleId".equals(vo.getPropNm())) {

			    ResultVO firewallResultVO = readCtrlItem(vo.getPropValue());
			    hm.put("firewallResult", firewallResultVO);

			    CtrlItemVO[] firewallData = (CtrlItemVO[]) firewallResultVO.getData();
			    if (firewallData != null && firewallData.length > 0) {
				CtrlPropVO[] firewallProps = firewallData[0].getPropArray();

				// ArrayList<Object> addresses = new
				// ArrayList<Object>();
				HashMap<String, Object> addresses = new HashMap<String, Object>();
				ArrayList<Object> accesses = new ArrayList<Object>();

				if (firewallProps != null && firewallProps.length > 0) {
				    for (CtrlPropVO firewallVo : firewallProps) {
					if ("address".equals(firewallVo.getPropNm())) {

					    ResultVO addressResultVO = readCtrlItem(firewallVo.getPropValue());
					    if (addressResultVO != null && addressResultVO.getData() != null
						    && addressResultVO.getData().length > 0) {

						addresses.put(((CtrlItemVO) addressResultVO.getData()[0]).getObjId(),
							addressResultVO.getData()[0]);
						// addresses.add(addressResultVO.getData()[0]);
					    }
					} else if ("access".equals(firewallVo.getPropNm())) {

					    ResultVO accessResultVO = readCtrlItem(firewallVo.getPropValue());
					    if (accessResultVO != null && accessResultVO.getData() != null
						    && accessResultVO.getData().length > 0) {
						accesses.add(accessResultVO.getData()[0]);

						// get address in access
						CtrlPropVO[] accessProps = ((CtrlItemVO) accessResultVO.getData()[0])
							.getPropArray();
						if (accessProps != null && accessProps.length > 0) {
						    for (CtrlPropVO accessProp : accessProps) {
							if ("addr_list".equals(accessProp.getPropNm())) {

							    ResultVO addressResultVO = readCtrlItem(
								    accessProp.getPropValue());
							    if (addressResultVO.getData() != null
								    && addressResultVO.getData().length > 0) {

								addresses.put(
									((CtrlItemVO) addressResultVO.getData()[0])
										.getObjId(),
									addressResultVO.getData()[0]);
								// addresses.add(addressResultVO.getData()[0]);
							    }
							}
						    }
						}
					    }
					}
				    }
				}

				if (addresses.size() > 0) {
				    Object[] o = new Object[addresses.size()];
				    addresses.values().toArray(o);
				    hm.put("address", o);
				}

				if (accesses.size() > 0) {
				    Object[] o = new Object[accesses.size()];
				    accesses.toArray(o);
				    hm.put("access", o);
				}
			    }

			} else if ("secuBundleId".equals(vo.getPropNm())) {

			    ResultVO setupResultVO = readCtrlItem(vo.getPropValue());
			    hm.put("securityResult", setupResultVO);

			}
		    }
		}
	    }

	} catch (Exception ex) {
	    resultVO = null;
	    logger.error("RuleUtilServiceImpl.readClientCtrlRuleInfo Exception occurred. ", ex);
	}

	return hm;
    }

    /**
     * 매체제어 정책 조회
     * 
     * @param String init, String objId, String userId
     * @return HashMap<String, Object>
     * @throws Exception
     */
    private HashMap<String, Object> readMediaRuleInfo(boolean init, String objId, String userId) throws Exception {

	HashMap<String, Object> hm = new HashMap<String, Object>();
	ResultVO resultVO = null;

	try {
	    resultVO = readCtrlItem(objId);

	    CtrlItemVO[] data = (CtrlItemVO[]) resultVO.getData();
	    if (data != null && data.length > 0) {

			ArrayList<String> mac_addresses = new ArrayList<String>();
			HashMap<String, Object> bluetooth = new HashMap<String, Object>();
			ArrayList<String> usb_serialno = new ArrayList<String>();
			HashMap<String, Object> serialno = new HashMap<String, Object>();

			CtrlPropVO[] props = data[0].getPropArray();
			if (props != null && props.length > 0) {
				for (CtrlPropVO vo : props) {
					switch (vo.getPropNm()) {
						case GPMSConstants.MEDIA_ITEM_BLUETOOTH_STATE:
							bluetooth.put("state", vo.getPropValue());
							break;
						case GPMSConstants.MEDIA_ITEM_MAC_ADDRESS:
							mac_addresses.add(vo.getPropValue());
							break;
						case GPMSConstants.MEDIA_ITEM_USB_MEMORY:
							serialno.put("state", vo.getPropValue());
							break;
						case GPMSConstants.MEDIA_ITEM_USB_SERIALNO:
							usb_serialno.add(vo.getPropValue());
							break;
						default:
							hm.put(vo.getPropNm(), vo.getPropValue());
							break;
					}
				}
			}

			//등록신청/ 등록거절/ 대기중/ 권한회수 항목 리스트(usb_status_board)
			String[] usbReqList = {};
			if (userId != "") {
				//리모트 계정
				UserReqVO urVo = new UserReqVO();
				urVo.setUserId(userId);
				urVo.setStatus(Constant.STS_EXPIRE);
				urVo.setActionType(Constant.ACTION_REGISTERING);

				List<UserReqVO> re = clientJobService.selectUserUsbMediaList(urVo);
				if (re != null && re.size() > 0) {
					usbReqList = new String[re.size()];
					for (int i = 0; i < re.size(); i++) {
						String state = re.get(i).getAdminCheck();
						String actionType = re.get(i).getActionType();
						String date = re.get(i).getModDt();
						String status = re.get(i).getStatus();
						if (state.equals(Constant.ACTION_WAITING) && actionType.equals(Constant.ACTION_REGISTERING)) {
							state = Constant.ACTION_REGISTERING;
						} else if (state.equals(Constant.ACTION_WAITING) && actionType.equals(Constant.ACTION_UNREGISTERING)) {
							state = Constant.ACTION_UNREGISTERING;
						} else if (status.equals(Constant.STS_USABLE)) {
							usb_serialno.add(re.get(i).getUsbSerialNo());
						}
						if (date == null) {
							date = re.get(i).getRegDt();
						}
						usbReqList[i] = re.get(i).getUsbSerialNo()+","+ date+","+ state+","+ re.get(i).getUsbName()+
								","+ re.get(i).getUsbProduct()+","+ re.get(i).getUsbSize()+","+ re.get(i).getUsbVendor()+","+ re.get(i).getUsbModel()+","+ re.get(i).getReqSeq();
					}
				}
			}

			if (usb_serialno != null || usbReqList != null) {
				String[] serials = new String[usb_serialno.size()];
				serials = usb_serialno.toArray(serials);
				if (serials.length != 0){
					serialno.put(GPMSConstants.MEDIA_ITEM_USB_SERIALNO, serials);
				}
				if (init == true && usbReqList.length != 0) {
					serialno.put(GPMSConstants.MEDIA_ITEM_USB_STATUS_BOARD, usbReqList);
				}
			}

			if (mac_addresses != null && mac_addresses.size() > 0) {
				String[] addrs = new String[mac_addresses.size()];
				addrs = mac_addresses.toArray(addrs);
				bluetooth.put(GPMSConstants.MEDIA_ITEM_MAC_ADDRESS, addrs);
			}

			hm.put("bluetooth", bluetooth);
			hm.put("usb_memory", serialno);

	    }
	} catch (Exception e) {
	    hm.clear();
	}

	return hm;
    }
    
    /**
     * Policy Kit 정책 조회
     * 
     * @param String objId
     * @return HashMap<String, Object>
     * @throws Exception
     */
    private HashMap<String, Object> readPolicyKitRuleInfo(String objId) throws Exception {

	HashMap<String, Object> hm = new HashMap<String, Object>();
	ResultVO resultVO = null;

	try {
	    resultVO = readCtrlItem(objId);

	    CtrlItemVO[] data = (CtrlItemVO[]) resultVO.getData();
	    if (data != null && data.length > 0) {

		CtrlPropVO[] props = data[0].getPropArray();
		if (props != null && props.length > 0) {
		    for (CtrlPropVO vo : props) {
			hm.put(vo.getPropNm(), vo.getPropValue());
		    }
		}
	    }

	} catch (Exception e) {
	    hm.clear();
	}

	return hm;
    }
    

    /**
     * OLD (참고용) 브라우저 정책 조회
     * 
     * @param String objId
     * @return HashMap<String, Object>
     * @throws Exception
     */
    private HashMap<String, Object> oldReadBrowserRuleInfo(String objId) throws Exception {

	HashMap<String, Object> hm = new HashMap<String, Object>();
	ResultVO resultVO = null;

	try {
	    resultVO = readCtrlItem(objId);

	    CtrlItemVO[] data = (CtrlItemVO[]) resultVO.getData();
	    if (data != null && data.length > 0) {

		ArrayList<String> trustUrls = new ArrayList<String>();
		ArrayList<String> untrustUrls = new ArrayList<String>();

		CtrlPropVO[] props = data[0].getPropArray();
		if (props != null && props.length > 0) {
		    for (CtrlPropVO vo : props) {

			switch (vo.getPropNm()) {

			case "trust":
			    trustUrls.add(vo.getPropValue());
			    break;

			case "trustgroup":
			    ResultVO trustGroupResultVO = readCtrlItem(vo.getPropValue());
			    CtrlPropVO[] trustProps = ((CtrlItemVO) trustGroupResultVO.getData()[0]).getPropArray();
			    for (CtrlPropVO p : trustProps) {
				trustUrls.add(p.getPropValue());
			    }
			    break;

			case "untrust":
			    untrustUrls.add(vo.getPropValue());
			    break;

			case "untrustgroup":
			    ResultVO untrustGroupResultVO = readCtrlItem(vo.getPropValue());
			    CtrlPropVO[] untrustProps = ((CtrlItemVO) untrustGroupResultVO.getData()[0]).getPropArray();
			    for (CtrlPropVO p : untrustProps) {
				untrustUrls.add(p.getPropValue());
			    }
			    break;

			default:
			    hm.put(vo.getPropNm(), vo.getPropValue());
			    break;

			}
		    }
		}

		if (trustUrls != null && trustUrls.size() > 0) {
		    String[] urls = new String[trustUrls.size()];
		    urls = trustUrls.toArray(urls);
		    hm.put("TRUST_URLS", urls);
		}

		if (untrustUrls != null && untrustUrls.size() > 0) {
		    String[] urls = new String[untrustUrls.size()];
		    urls = untrustUrls.toArray(urls);
		    hm.put("UNTRUST_URLS", urls);
		}
	    }

	} catch (Exception e) {

	    hm.clear();
	}

	return hm;
    }

    /**
     * 브라우저 정책 조회
     * 
     * @param String objId
     * @return HashMap<String, Object>
     * @throws Exception
     */
    private HashMap<String, Object> readBrowserRuleInfo(String objId) throws Exception {

	HashMap<String, Object> gooroomHm = new HashMap<String, Object>();
	HashMap<String, Object> policyHm = new HashMap<String, Object>();
	HashMap<String, Object> hm = new HashMap<String, Object>();

	ResultVO resultVO = null;

	try {
	    resultVO = readCtrlItem(objId);

	    CtrlItemVO[] data = (CtrlItemVO[]) resultVO.getData();
	    if (data != null && data.length > 0) {

		ArrayList<String> trustUrls = new ArrayList<String>();

		CtrlPropVO[] props = data[0].getPropArray();
		if (props != null && props.length > 0) {
		    for (CtrlPropVO vo : props) {

			switch (vo.getPropNm()) {

			case "trust":
			    trustUrls.add(vo.getPropValue());
			    break;

			case "trustgroup":
			    ResultVO trustGroupResultVO = readCtrlItem(vo.getPropValue());
			    CtrlPropVO[] trustProps = ((CtrlItemVO) trustGroupResultVO.getData()[0]).getPropArray();
			    for (CtrlPropVO p : trustProps) {
				trustUrls.add(p.getPropValue());
			    }
			    break;
			case "websocket":
			    hm.put(vo.getPropNm(), "allow".equalsIgnoreCase(vo.getPropValue()));
			    break;
			case "webworker":
			    hm.put(vo.getPropNm(), "allow".equalsIgnoreCase(vo.getPropValue()));
			    break;

			default:
			    break;

			}
		    }
		}

		if (trustUrls != null && trustUrls.size() > 0) {
		    String[] urls = new String[trustUrls.size()];
		    urls = trustUrls.toArray(urls);
		    hm.put("whitelist", urls);
		}

		policyHm.put("policy", hm);
		gooroomHm.put("gooroom", policyHm);
	    }

	} catch (Exception e) {
	    hm.clear();
	    policyHm.clear();
	    gooroomHm.clear();
	}

	return gooroomHm;
    }

    /**
     * 브라우저 사이트 설정 조회
     * 
     * @param String objId
     * @return HashMap<String, Object>
     * @throws Exception
     */
    private String readBrowserRuleSiteSetup(String objId, String gubun) throws Exception {

	String propName = "";
	String resultString = "";

	if ("TRUST".equalsIgnoreCase(gubun)) {
	    propName = "trustSetup";
	} else if ("UNTRUST".equalsIgnoreCase(gubun)) {
	    propName = "untrustSetup";
	}

	ResultVO resultVO = null;

	try {
	    resultVO = readCtrlItem(objId);

	    CtrlItemVO[] data = (CtrlItemVO[]) resultVO.getData();
	    if (data != null && data.length > 0) {

		CtrlPropVO[] props = data[0].getPropArray();
		if (props != null && props.length > 0) {
		    for (CtrlPropVO vo : props) {
			if (propName.equals(vo.getPropNm())) {
			    resultString = vo.getPropValue();
//			    ResultVO siteSetupResultVO = readCtrlItem(vo.getPropValue());
//			    CtrlPropVO[] setupProps = ((CtrlItemVO) siteSetupResultVO.getData()[0]).getPropArray();
//			    for (CtrlPropVO p : setupProps) {
//				if ("CONTENT".equals(p.getPropNm())) {
//				    resultString = p.getPropValue();
//				    break;
//				}
//			    }
			    break;
			}
		    }
		}
	    }

	} catch (Exception e) {
	}

	return resultString;
    }

    /**
     * 브라우저 정책 JSON 조회 - JSON STRING
     * 
     * @param String userId
     * @param String clientId
     * @return String
     * @throws Exception
     */
    @Override
    public String getBrowserRuleJson(String userId, String clientId) throws Exception {

	HashMap<String, Object> hm = new HashMap<String, Object>();

	try {
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("userId", userId);
	    map.put("clientId", clientId);
	    map.put("confTp", "BROWSERRULE");
	    map.put("defaultConfId", "BCRUDEFAULT");

	    String objId = ruleUtilDAO.selectItemIdByMap(map);

	    if (objId != null && objId.length() > 0) {
		hm = readBrowserRuleInfo(objId);
	    } else {
		// 디폴트 값을 조회
		hm = readBrowserRuleInfo(GPMSConstants.CTRL_ITEM_BROWSERCTRL_RULE_ABBR + GPMSConstants.MSG_DEFAULT);
	    }

	} catch (Exception ex) {
	    hm.clear();
	    logger.error("RuleUtilServiceImpl.getBrowserRuleJson Exception occurred. ", ex);
	}

	if (hm != null && hm.size() > 0) {
	    StringWriter outputWriter = new StringWriter();
	    try {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputWriter, hm);
		return outputWriter.toString();

	    } catch (Exception ex) {
		logger.error("RuleUtilServiceImpl.getBrowserRuleJson (writeValueAsString) Exception occurred. ", ex);
	    } finally {
		try {
		    if (outputWriter != null) {
			outputWriter.close();
		    }
		} catch (Exception ex) {

		}
	    }
	}

	return "";
    }

    /**
     * 브라우저정책 - 신뢰사이트설정 JSON JSON 조회
     * 
     * @param String userId
     * @return String
     * @throws Exception
     */
    @Override
    public String getBrowserRuleSiteSetupJson(String userId, String clientId, String gubun) throws Exception {

	String jsonStr = "";

	try {
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("userId", userId);
	    map.put("clientId", clientId);
	    map.put("confTp", "BROWSERRULE");
	    map.put("defaultConfId", "BCRUDEFAULT");

	    String objId = ruleUtilDAO.selectItemIdByMap(map);

	    if (objId != null && objId.length() > 0) {
		jsonStr = readBrowserRuleSiteSetup(objId, gubun);
	    } else {
		// 디폴트 값을 조회
		jsonStr = readBrowserRuleSiteSetup(
			GPMSConstants.CTRL_ITEM_BROWSERCTRL_RULE_ABBR + GPMSConstants.MSG_DEFAULT, gubun);
	    }

	} catch (Exception ex) {
	    logger.error("RuleUtilServiceImpl.getBrowserRuleSiteSetupJson Exception occurred. ", ex);
	}

	return jsonStr;
    }

    /**
     * 브라우저정책 - 추가 설정 조회
     * 
     * @param String userId
     * @param String clientId
     * @param String gubun
     * @return String
     * @throws Exception
     */
    @Override
    public String getBrowserExtRuleJson(String userId, String clientId, String gubun) throws Exception {

	HashMap<String, Object> hm = new HashMap<String, Object>();

	try {
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("userId", userId);
	    map.put("clientId", clientId);
	    map.put("confTp", "BROWSERRULE");
	    map.put("defaultConfId", "BCRUDEFAULT");

	    String objId = ruleUtilDAO.selectItemIdByMap(map);

	    if (objId != null && objId.length() > 0) {
		hm = readBrowserExtRule(objId, gubun);
	    } else {
		// 디폴트 값을 조회
		hm = readBrowserExtRule(GPMSConstants.CTRL_ITEM_BROWSERCTRL_RULE_ABBR + GPMSConstants.MSG_DEFAULT,
			gubun);
	    }

	} catch (Exception ex) {
	    logger.error("RuleUtilServiceImpl.getBrowserExtRuleJson Exception occurred. ", ex);
	}

	if (hm != null && hm.size() > 0) {
	    StringWriter outputWriter = new StringWriter();
	    try {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputWriter, hm);
		return outputWriter.toString();

	    } catch (Exception ex) {
		logger.error("RuleUtilServiceImpl.getBrowserExtRuleJson (writeValueAsString) Exception occurred. ", ex);
	    } finally {
		try {
		    if (outputWriter != null) {
			outputWriter.close();
		    }
		} catch (Exception ex) {

		}
	    }
	}

	return "";
    }

    /**
     * 로그 컨피그 검색 (저널디 설정 제외)
     * 
     * @param String clientId
     * @return String
     * @throws Exception
     */
    @Override
    public String getLogConfigJson(String clientId) throws Exception {

	HashMap<String, Object> hm = new HashMap<String, Object>();

	try {
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("clientId", clientId);
	    map.put("confTp", "CLIENTCONF");
	    map.put("defaultConfId", "CLCFDEFAULT");

	    String objId = ruleUtilDAO.selectItemIdWithMapInClientRule(map);

	    if (objId != null && objId.length() > 0) {
		hm = readLogConfig(objId);
	    }

	} catch (Exception ex) {
	    logger.error("RuleUtilServiceImpl.getLogConfigJson Exception occurred. ", ex);
	}

	if (hm != null && hm.size() > 0) {
	    StringWriter outputWriter = new StringWriter();
	    try {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputWriter, hm);
		return outputWriter.toString();

	    } catch (Exception ex) {
		logger.error("RuleUtilServiceImpl.getLogConfigJson (writeValueAsString) Exception occurred. ", ex);
	    } finally {
		try {
		    if (outputWriter != null) {
			outputWriter.close();
		    }
		} catch (Exception ex) {

		}
	    }
	}

	return "";
    }

    /**
     * 로그 컨피그중 저널디 설정 검색
     * 
     * @param String clientId
     * @return String
     * @throws Exception
     */
    @Override
    public String getJournaldConfigJson(String clientId) throws Exception {

	HashMap<String, Object> hm = new HashMap<String, Object>();

	try {
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("clientId", clientId);
	    map.put("confTp", "CLIENTCONF");
	    map.put("defaultConfId", "CLCFDEFAULT");

	    String objId = ruleUtilDAO.selectItemIdWithMapInClientRule(map);

	    if (objId != null && objId.length() > 0) {
		hm = readJournaldConfig(objId);
	    }

	} catch (Exception ex) {
	    logger.error("RuleUtilServiceImpl.getJournaldConfigJson Exception occurred. ", ex);
	}

	if (hm != null && hm.size() > 0) {
	    StringWriter outputWriter = new StringWriter();
	    try {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputWriter, hm);
		return outputWriter.toString();

	    } catch (Exception ex) {
		logger.error("RuleUtilServiceImpl.getJournaldConfigJson (writeValueAsString) Exception occurred. ", ex);
	    } finally {
		try {
		    if (outputWriter != null) {
			outputWriter.close();
		    }
		} catch (Exception ex) {

		}
	    }
	}

	return "";
    }

    /**
     * 브라우저 추가 설정 조회
     * 
     * @param String objId
     * @return HashMap<String, Object>
     * @throws Exception
     */
    private HashMap<String, Object> readBrowserExtRule(String objId, String gubun) throws Exception {

	HashMap<String, Object> hm = new HashMap<String, Object>();
	String printRule = "printRule__" + gubun.toLowerCase();
	String downloadRule = "downloadRule__" + gubun.toLowerCase();
	String devToolRule = "devToolRule__" + gubun.toLowerCase();
	String viewSourceRule = "viewSourceRule__" + gubun.toLowerCase();

	ResultVO resultVO = null;
	try {
	    resultVO = readCtrlItem(objId);

	    CtrlItemVO[] data = (CtrlItemVO[]) resultVO.getData();
	    if (data != null && data.length > 0) {

		CtrlPropVO[] props = data[0].getPropArray();
		if (props != null && props.length > 0) {

		    for (CtrlPropVO vo : props) {

			if (vo.getPropNm() != null) {
			    if (vo.getPropNm().equalsIgnoreCase(printRule)) {
				hm.put("PrintingEnabled", Boolean.parseBoolean(vo.getPropValue()));
			    } else if (vo.getPropNm().equalsIgnoreCase(downloadRule)) {
				hm.put("DownloadRestrictions", Integer.parseInt(vo.getPropValue()));
			    } else if (vo.getPropNm().equalsIgnoreCase(devToolRule)) {
				hm.put("DeveloperToolsAvailability", Integer.parseInt(vo.getPropValue()));
			    } else if (vo.getPropNm().equalsIgnoreCase(viewSourceRule)) {
				hm.put("PageSourceViewEnabled", Boolean.parseBoolean(vo.getPropValue()));
			    }
			}
		    }
		}
	    }
	} catch (Exception e) {
	}

	return hm;
    }

    /**
     * 로그 컨피그 검색 (저널디 설정 제외)
     * 
     * @param String objId
     * @return HashMap<String, Object>
     * @throws Exception
     */
    private HashMap<String, Object> readLogConfig(String objId) throws Exception {

	HashMap<String, Object> hm = new HashMap<String, Object>();

	String isDeleteLog = "";
	String logMaxSize = "";
	String logMaxCount = "";
	String logRemainDate = "";
	String systemKeepFree = "";

	String transmit_boot = "";
	String transmit_exe = "";
	String transmit_os = "";
	String transmit_media = "";
	String transmit_agent = "";

	String notify_boot = "";
	String notify_exe = "";
	String notify_os = "";
	String notify_media = "";
	String notify_agent = "";

	String show_boot = "";
	String show_exe = "";
	String show_os = "";
	String show_media = "";
	String show_agent = "";

	ResultVO resultVO = null;
	try {
	    resultVO = readCtrlItem(objId);
	    CtrlItemVO[] data = (CtrlItemVO[]) resultVO.getData();
	    if (data != null && data.length > 0) {
		CtrlPropVO[] props = data[0].getPropArray();
		if (props != null && props.length > 0) {
		    for (CtrlPropVO vo : props) {
			if (vo.getPropNm() != null) {
			    if (vo.getPropNm().equalsIgnoreCase("transmit_boot")) {
				transmit_boot = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("transmit_exe")) {
				transmit_exe = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("transmit_os")) {
				transmit_os = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("transmit_media")) {
				transmit_media = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("transmit_agent")) {
				transmit_agent = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("notify_boot")) {
				notify_boot = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("notify_exe")) {
				notify_exe = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("notify_os")) {
				notify_os = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("notify_media")) {
				notify_media = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("notify_agent")) {
				notify_agent = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("show_boot")) {
				show_boot = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("show_exe")) {
				show_exe = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("show_os")) {
				show_os = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("show_media")) {
				show_media = vo.getPropValue();
			    } else if (vo.getPropNm().equalsIgnoreCase("show_agent")) {
				show_agent = vo.getPropValue();
			    }
			}
		    }
		}
	    }
	    
	    // create hashmap
	    HashMap<String, Object> hm_os = new HashMap<String, Object>();
	    hm_os.put("transmit_level", transmit_os);
	    hm_os.put("notify_level", notify_os);
	    hm_os.put("show_level", show_os);
	    hm_os.put("service_name", "gop-daemon");
	    hm_os.put("syslog_identifier", "gop-daemon");
	    
	    HashMap<String, Object> hm_exe = new HashMap<String, Object>();
	    hm_exe.put("transmit_level", transmit_exe);
	    hm_exe.put("notify_level", notify_exe);
	    hm_exe.put("show_level", show_exe);
	    hm_exe.put("service_name", "gep-daemon");
	    hm_exe.put("syslog_identifier", "gep-daemon");
	    
	    HashMap<String, Object> hm_boot = new HashMap<String, Object>();
	    hm_boot.put("transmit_level", transmit_boot);
	    hm_boot.put("notify_level", notify_boot);
	    hm_boot.put("show_level", show_boot);
	    hm_boot.put("service_name", "gbp-daemon");
	    hm_boot.put("syslog_identifier", "gbp-daemon");
	    
	    HashMap<String, Object> hm_media = new HashMap<String, Object>();
	    hm_media.put("transmit_level", transmit_media);
	    hm_media.put("notify_level", notify_media);
	    hm_media.put("show_level", show_media);
	    hm_media.put("service_name", "grac-device-daemon");
	    hm_media.put("syslog_identifier", "GRAC,gooroom-browser,GRAC-EXT");
	    
	    HashMap<String, Object> hm_agent = new HashMap<String, Object>();
	    hm_agent.put("transmit_level", transmit_agent);
	    hm_agent.put("notify_level", notify_agent);
	    hm_agent.put("show_level", show_agent);
	    hm_agent.put("service_name", "gooroom-agent");
	    hm_agent.put("syslog_identifier", "gooroom-agent");
	    
	    hm.put("os", hm_os);
	    hm.put("exe", hm_exe);
	    hm.put("boot", hm_boot);
	    hm.put("media", hm_media);
	    hm.put("agent", hm_agent);
	    
	} catch (Exception e) {
	}

	return hm;
    }

    /**
     * 저널디 컨피그 검색
     * 
     * @param String objId
     * @return HashMap<String, Object>
     * @throws Exception
     */
    private HashMap<String, Object> readJournaldConfig(String objId) throws Exception {

	HashMap<String, Object> hm = new HashMap<String, Object>();

	ResultVO resultVO = null;
	try {
	    resultVO = readCtrlItem(objId);
	    CtrlItemVO[] data = (CtrlItemVO[]) resultVO.getData();
	    if (data != null && data.length > 0) {
		CtrlPropVO[] props = data[0].getPropArray();
		if (props != null && props.length > 0) {
		    for (CtrlPropVO vo : props) {
			if (vo.getPropNm() != null) {
			    if (vo.getPropNm().equalsIgnoreCase("isDeleteLog")) {
				hm.put("isDeleteLog", vo.getPropValue());
			    } else if (vo.getPropNm().equalsIgnoreCase("logMaxSize")) {
				hm.put("logMaxSize", vo.getPropValue());
			    } else if (vo.getPropNm().equalsIgnoreCase("logMaxCount")) {
				hm.put("logMaxCount", vo.getPropValue());
			    } else if (vo.getPropNm().equalsIgnoreCase("logRemainDate")) {
				hm.put("logRemainDate", vo.getPropValue());
			    } else if (vo.getPropNm().equalsIgnoreCase("systemKeepFree")) {
				hm.put("systemKeepFree", vo.getPropValue());
			    }
			}
		    }
		}
	    }
	    
	} catch (Exception e) {
	}

	return hm;
    }

	/**
	 * 네트워크(단말기보안정책) 부분과 매체제어정채을 합한 JSON 조회 - JSON STRING
	 *
	 * @param String userId
	 * @param String clientId
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String getNetworkAndMediaRuleJson(boolean init, String userId, String clientId) throws Exception {

		HashMap<String, Object> resultHashMap = new HashMap<String, Object>();
		HashMap<String, Object> hm = new HashMap<String, Object>();
		ResultVO resultVO = null;

		try {
			HashMap<String, Object> networkHashMap = new HashMap<String, Object>();

			// set
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			map.put("clientId", clientId);
			map.put("confTp", "SECURITYRULE");
			map.put("defaultConfId", "GSRUDEFAULT");

			String objId = ruleUtilDAO.selectItemIdByMap(map);
			if (objId != null && objId.length() > 0) {
				resultVO = readCtrlItem(objId);
			}

			if (resultVO != null && resultVO.getData() != null) {
				CtrlItemVO[] data = (CtrlItemVO[]) resultVO.getData();
				if (data != null && data.length > 0) {

					// set version
					String siteVersion = ruleUtilDAO.selectSiteVersion();
					networkHashMap.put("version", siteVersion);

					CtrlPropVO[] props = data[0].getPropArray();

					for (CtrlPropVO vo : props) {
						switch (vo.getPropNm()) {
							case GPMSConstants.NETWORK_GLOVAL_STATE:
								networkHashMap.put("state", vo.getPropValue());
								break;
							case GPMSConstants.NETWORK_ITEM_FIREWALL:
								String[] val = vo.getPropValue().split("\\|");
								if(val[1].equals("basic")) {
									NetworkPropVO networkVO = new NetworkPropVO();
									if (val != null) {
										networkVO.setSeq(val[0]);
										networkVO.setDirection(val[2]);
										networkVO.setProtocol(val[3]);
										networkVO.setIpaddress(val[4]);
										networkVO.setSrc_ports(val[5]);
										networkVO.setDst_ports(val[6]);
										networkVO.setState(val[7]);
									}

									ArrayList<NetworkPropVO> revNetworks = (ArrayList<NetworkPropVO>) networkHashMap.get("rules");
									if (revNetworks != null) {
										revNetworks.add(networkVO);
										networkHashMap.put("rules", revNetworks);
									} else {
										revNetworks = new ArrayList<NetworkPropVO>();
										revNetworks.add(networkVO);
										networkHashMap.put("rules", revNetworks);
									}
								} else {
									if (val != null) {
										NetworkAdvancedPropVO networkAdvancedVO = new NetworkAdvancedPropVO();
										networkAdvancedVO.setSeq(val[0]);
										networkAdvancedVO.setCmd(val[2]);

										ArrayList<NetworkAdvancedPropVO> revNetworks = (ArrayList<NetworkAdvancedPropVO>) networkHashMap.get("rules_raw");
										if (revNetworks != null) {
											revNetworks.add(networkAdvancedVO);
											networkHashMap.put("rules_raw", revNetworks);
										} else {
											revNetworks = new ArrayList<NetworkAdvancedPropVO>();
											revNetworks.add(networkAdvancedVO);
											networkHashMap.put("rules_raw", revNetworks);
										}
									}
								}
								break;
							default:
								break;
						}
					}
				}
			}

			if (networkHashMap != null && networkHashMap.size() > 0) {
				resultHashMap.clear();
				resultHashMap.put("network", networkHashMap);

				// 매체제어
				HashMap<String, Object> mediaHm = new HashMap<String, Object>();
				HashMap<String, String> mediaMap = new HashMap<String, String>();
				mediaMap.put("userId", userId);
				mediaMap.put("clientId", clientId);
				mediaMap.put("confTp", "MEDIARULE");
				mediaMap.put("defaultConfId", "MCRUDEFAULT");

				String mediaRe = ruleUtilDAO.selectItemIdByMap(mediaMap);
				if (mediaRe != null && mediaRe.length() > 0) {
					mediaHm = readMediaRuleInfo(init, mediaRe, userId);
				} else {
					// 디폴트 값을 조회
					mediaHm = readMediaRuleInfo(init,
							GPMSConstants.CTRL_ITEM_MEDIACTRL_RULE_ABBR + GPMSConstants.MSG_DEFAULT, userId);
				}

				if (mediaHm != null && mediaHm.size() > 0) {
					resultHashMap.putAll(mediaHm);
				}

			} else {
				resultHashMap.clear();
			}

		} catch (Exception ex) {
			resultHashMap.clear();
			logger.error("RuleUtilServiceImpl.getNetworkRuleJson Exception occurred. ", ex);
		}

		if (resultHashMap != null && resultHashMap.size() > 0) {
			StringWriter outputWriter = new StringWriter();
			try {
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(outputWriter, resultHashMap);
				return outputWriter.toString();
			} catch (Exception ex) {
				logger.error("RuleUtilServiceImpl.getNetworkAndMediaRuleJson (writeValueAsString) Exception occurred. ", ex);
			} finally {
				try {
					if (outputWriter != null) {
						outputWriter.close();
					}
				} catch (Exception ex) {

				}
			}
		}
		return "";
	}

    /**
     * Policy Kit 정책을 위한 JSON 조회 - JSON STRING
     * 
     * @param String userId
     * @param String clientId
     * @return String
     * @throws Exception
     */
    @Override
    public String getPolicyKitRuleJson(String userId, String clientId) throws Exception {

	HashMap<String, Object> resultHashMap = new HashMap<String, Object>();
	
	try {
		HashMap<String, Object> policyKitHm = new HashMap<String, Object>();
		HashMap<String, String> policyKitMap = new HashMap<String, String>();
		policyKitMap.put("userId", userId);
		policyKitMap.put("clientId", clientId);
		policyKitMap.put("confTp", "POLICYKITRULE");
		policyKitMap.put("defaultConfId", "POKIDEFAULT");

		String policyKitRe = ruleUtilDAO.selectItemIdByMap(policyKitMap);
		if (policyKitRe != null && policyKitRe.length() > 0) {
		    policyKitHm = readPolicyKitRuleInfo(policyKitRe);
		} else {
		    // 디폴트 값을 조회
		    policyKitHm = readPolicyKitRuleInfo(
			    GPMSConstants.CTRL_ITEM_POLICYKIT_RULE_ABBR + GPMSConstants.MSG_DEFAULT);
		}

		if (policyKitHm != null && policyKitHm.size() > 0) {
		    resultHashMap.putAll(policyKitHm);
		}


	} catch (Exception ex) {
	    
	    resultHashMap.clear();
	    logger.error("RuleUtilServiceImpl.getPolicyKitRuleJson Exception occurred. ", ex);
	}

	if (resultHashMap != null && resultHashMap.size() > 0) {
	    StringWriter outputWriter = new StringWriter();
	    try {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputWriter, resultHashMap);
		return outputWriter.toString();
		
	    } catch (Exception ex) {
		logger.error("RuleUtilServiceImpl.getPolicyKitRuleJson (writeValueAsString) Exception occurred. ",
			ex);
	    } finally {
		try {
		    if (outputWriter != null) {
			outputWriter.close();
		    }
		} catch (Exception ex) {

		}
	    }
	}

	return "";
    }
}
