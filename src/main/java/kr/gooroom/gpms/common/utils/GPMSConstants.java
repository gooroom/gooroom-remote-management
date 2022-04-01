package kr.gooroom.gpms.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GPMSConstants {
    
    private static final Properties prop = new Properties();
    private static final String GOOROOM_PROPERTIES = "/properties/gooroomapi.properties";

    static {
	InputStream is = GPMSConstants.class.getClassLoader().getResourceAsStream(GOOROOM_PROPERTIES);
	try {
	    prop.load(is);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public static final String SITE_NAME = prop.getProperty("gooroom.site.name");

	public static final String MSG_SUCCESS = "success";
	public static final String MSG_FAIL = "fail";
	public static final String MSG_DEFAULT = "DEFAULT";
	
	// 
	public static final String STS_SERVICE_RUN = "STAT010";
	public static final String STS_SERVICE_STOP = "STAT020";

	public static final String STS_NORMAL_USER = "STAT010";
	public static final String STS_DELETE_USER = "STAT020";
	public static final String STS_TEMP_PASSWORD = "STAT030";
	
	
	// ----정책 항목정의
	// 단말제어정책 -> 방화벽정책:주소그룹
	public static final String CTRL_ITEM_ADDRESS_GROUP = "ADDRESS_GROUP";
	public static final String CTRL_ITEM_ADDRESS_GROUP_ABBR = "ADGR";
	// 단말제어정책 -> 방화벽정책:접근정책
	public static final String CTRL_ITEM_ACCESS_GROUP = "ACCESS_GROUP";
	public static final String CTRL_ITEM_ACCESS_GROUP_ABBR = "ACGR";
	
	// 단말제어정책 -> 보안정책
	public static final String CTRL_ITEM_SECURITY_RULE = "SECURITY_RULE";
	public static final String CTRL_ITEM_SECURITY_RULE_ABBR = "SCRU";
	
	// 단말제어정책
	public static final String CTRL_ITEM_CLIENTSEC_RULE = "CLIENTSEC_RULE";
	public static final String CTRL_ITEM_CLIENTSEC_RULE_ABBR = "CSRU";
	
	// Policy Kit 정책
	public static final String CTRL_ITEM_POLICYKIT_RULE_ABBR = "POKI";
		
	// 매체제어정책
	public static final String CTRL_ITEM_MEDIACTRL_RULE = "MEDIACTRL_RULE";
	public static final String CTRL_ITEM_MEDIACTRL_RULE_ABBR = "MCRU";
	// 매체제어 항목정의
	public static final String MEDIA_ITEM_USB_MEMORY = "usb_memory";
	public static final String MEDIA_ITEM_USB_SERIALNO = "usb_serialno";
	public static final String MEDIA_ITEM_USB_STATUS_BOARD = "usb_status_board";
	public static final String MEDIA_ITEM_CD_DVD = "cd_dvd";
	public static final String MEDIA_ITEM_PRINTER = "printer";
	//public static final String MEDIA_ITEM_SCREEN_CAPTURE = "screen_capture";
	public static final String MEDIA_ITEM_CLIPBOARD = "clipboard";
	public static final String MEDIA_ITEM_CAMERA = "camera";
	//public static final String MEDIA_ITEM_MICROPHONE = "microphone";
	public static final String MEDIA_ITEM_SOUND = "sound";
	public static final String MEDIA_ITEM_KEYBOARD = "keyboard";
	public static final String MEDIA_ITEM_MOUSE = "mouse";
	public static final String MEDIA_ITEM_WIRELESS = "wireless";
	public static final String MEDIA_ITEM_MAC_ADDRESS = "mac_address";
	public static final String MEDIA_ITEM_BLUETOOTH_STATE = "bluetooth_state";

	public static final String NETWORK_GLOVAL_STATE = "global_network";
	public static final String NETWORK_ITEM_FIREWALL = "firewall_network";

	// 브라우져정책
	public static final String CTRL_ITEM_BROWSERCTRL_RULE = "BROWSERCTRL_RULE";
	public static final String CTRL_ITEM_BROWSERCTRL_RULE_ABBR = "BCRU";
	// 브라우져그룹
	public static final String CTRL_ITEM_URL_GROUP = "URL_GROUP";
	public static final String CTRL_ITEM_URL_GROUP_ABBR = "URGR";
	// 신뢰사이트 설정
	public static final String CTRL_ITEM_TRUST_SETUP = "TRUSTSITE_SETUP";
	public static final String CTRL_ITEM_TRUST_SETUP_ABBR = "TRSU";
	// 비신뢰사이트 설정
	public static final String CTRL_ITEM_UNTRUST_SETUP = "UNTRUSTSITE_SETUP";
	public static final String CTRL_ITEM_UNTRUST_SETUP_ABBR = "UTSU";

	// 단말 기능 설정
	public static final String CTRL_CLIENT_SETUP_CONF = "CLIENT_CONF";
	public static final String CTRL_CLIENT_SETUP_CONF_ABBR = "CLCF";
	// 단말 기능 설정 항목
	public static final String CTRL_ITEM_AGENTPOLLINGTIME = "AGENTPOLLINGTIME";
	public static final String CTRL_ITEM_NTPSELECTADDRESS = "NTPSELECTADDRESS";
	public static final String CTRL_ITEM_NTPADDRESSES = "NTPADDRESSES";

}
