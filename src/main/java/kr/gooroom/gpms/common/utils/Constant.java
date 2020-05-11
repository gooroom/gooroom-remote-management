package kr.gooroom.gpms.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constant {

	public static Properties prop = new Properties();
	private static final String GOOROOM_PROPERTIES = "/properties/gooroomapi.properties";

	static {
		InputStream is = Constant.class.getClassLoader().getResourceAsStream(GOOROOM_PROPERTIES);
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String MSG_SUCCESS = "SUCCESS";
	public static final String MSG_FAIL = "FAIL";
	
	public static final String TOKEN_SALT = prop.getProperty("gooroom.token.salt");
	public static final String TOKEN_ISSUER = prop.getProperty("gooroom.token.issuer");
	public static final String TOKEN_INTERVAL = prop.getProperty("gooroom.token.interval");
	public static final String TOKEN_SUCCESS = "";
	
	public static final String CERTIFICATE_KEY = prop.getProperty("gooroom.grmserver.privatekey.path");
	public static final String CERTIFICATE_CERT = prop.getProperty("gooroom.certificate.cert");
	
	public static final String SITE_NAME = prop.getProperty("gooroom.site.name");
	
	public static final String RSP_OK = "200";
	public static final String RSP_NOT_AUTH = "401";
	public static final String RSP_INTERNAL_ERR = "444";
	
	public static final String AGENT_STATUS = "status";
	public static final String AGENT_DATA = "data";
	
	//public static final String AGENT_STATUS_RESULT = "result";
	//public static final String AGENT_STATUS_RESULTCODE = "resultCode";
	//public static final String AGENT_STATUS_MESSAGE = "message";
	
	public static final String AGENT_DATA_CLIENTID = "clientId";
	public static final String AGENT_DATA_JOBNO = "jobNo";
	public static final String AGENT_DATA_JOBDATA = "job";
	
	public static final String JOBS_CLIENT_ID = "client_id";
	public static final String JOBS_USER_ID = "user_id";
	public static final String JOBS_TYPE = "type";
	public static final String JOBS_MAX_RSP_TIME = "max_rsp_time";
	public static final String JOBS_TIMEOUT_CNT = "timeout_cnt";
	public static final String JOBS_POLLING_TIME = "polling_time";
	
	public static final String TR_NO_AGENT_DATA = "noagentdata";
	public static final String TR_OK = "ok";
	
	public static final String JOB_STAT_SUCCESS = "C";
	public static final String JOB_STAT_FAIL = "E";
	
	//agent의 agent_define.py에 정의되어 있는 템플릿 엘리먼트명 정의와 일치해야한다.
	public static final String J_OB = "job";
	public static final String J_MOD = "module";
	public static final String J_MODN = "module_name";
	public static final String J_TASK = "task";
	public static final String J_TASKN = "task_name";
	public static final String J_IN = "in";
	public static final String J_OUT = "out";
	public static final String J_PKG_LIST = "pkg_list";
	public static final String J_TERMINAL_INFO = "terminal_info";
	public static final String J_REQUEST = "request";
	public static final String J_RESPONSE = "response";
	public static final String J_OS_STATUS = "os_status";
	public static final String J_OS_LOG = "os_log";
	public static final String J_BOOT_STATUS = "boot_status";
	public static final String J_BOOT_LOG = "boot_log";
	public static final String J_EXE_STATUS = "exe_status";
	public static final String J_EXE_LOG ="exe_log";
	public static final String J_MEDIA_STATUS = "media_status";
	public static final String J_MEDIA_LOG = "media_log";
	public static final String J_SAFE_SCORE = "safe_score";
	public static final String J_USER_ID = "user_id";
	public static final String J_OS_RUN = "os_run";
	public static final String J_BOOT_RUN = "boot_run";
	public static final String J_EXE_RUN = "exe_run";
	public static final String J_MEDIA_RUN = "media_run";
	public static final String J_AGENT_LOG = "agent_log";
	public static final String J_LOGS = "logs";
	
	public static final String H_AUTH = "auth";
	public static final String H_CID = "client_id";
	public static final String H_CERT = "gooroom-client-cert";
	public static final String H_TOKEN = "gooroom-client-token";
	public static final String H_REALIP = "gooroom-real-ip";
	public static final String H_VERSION = "version";
	
	public static final String TASK_INSTALLED_PACKAGE_LIST = "installed_package_list";
	public static final String TASK_GRM_HEARTBEAT = "grm_heartbit";
	public static final String TASK_SUMMARY_LOG = "summary_log";
	public static final String TASK_GET_UPDATE_SERVER_CONFIG = "get_update_server_config";
	public static final String TASK_APPEND_CONTENTS_ETC_HOSTS = "append_contents_etc_hosts";
	public static final String TASK_GET_SERVER_CERTIFICATE = "get_server_certificate";
	public static final String TASK_UPDATE_PACKAGE_VERSION_TO_SERVER = "update_package_version_to_server";
	public static final String TASK_INSERT_ALL_PACKAGES_TO_SERVER = "insert_all_packages_to_server";
	public static final String TASK_CLEAR_SECURITY_ALARM = "clear_security_alarm";
	public static final String TASK_SET_AUTHORITY_CONFIG = "set_authority_config";
	public static final String TASK_GET_SERVERJOB_DISPATCH_TIME = "get_serverjob_dispatch_time";
	public static final String TASK_CLIENT_INFO = "client_info";
	public static final String TASK_GET_NTP_LIST_CONFIG = "get_ntp_list_config";
	public static final String TASK_GET_SCREEN_TIME = "get_screen_time";
	public static final String TASK_GOOROOM_LOG = "gooroom_log";
	public static final String TASK_GET_HYPERVISOR_OPERATION = "get_hypervisor_operation";
	public static final String TASK_GET_MEDIA_CONFIG = "get_media_config";
	public static final String TASK_GET_BROWSER_CONFIG = "get_browser_config";
	public static final String TASK_GET_UPDATE_OPERATION_WITH_LOGINID = "get_update_operation_with_loginid";
	public static final String TASK_GET_UPDATE_OPERATION = "get_update_operation";
	public static final String TASK_TELL_UPDATE_OPERATION = "tell_update_operation";
	public static final String TASK_GET_PASSWORD_CYCLE = "get_password_cycle";
	public static final String TASK_SET_APT_CONF = "set_apt_conf";
	public static final String TASK_SET_GPG_KEY = "set_gpg_key";
	public static final String TASK_PROFILING = "profiling";
	public static final String TASK_PROFILING_PACKAGES = "profiling_packages";
	public static final String TASK_GET_SERVER_TIME = "get_server_time";
	public static final String TASK_CLIENT_SYNC = "client_sync";
	public static final String TASK_CLIENT_USER_SYNC = "client_user_sync";
	public static final String TASK_SECURITY_LOG = "security_log";
	public static final String TASK_GET_APP_LIST = "get_app_list";
	public static final String TASK_SET_APP_LIST = "set_app_list";
	public static final String TASK_SET_HOMEFOLDER_OPERATION = "set_homefolder_operation";
	public static final String TASK_DPMS_OFF_TIME = "dpms_off_time";
	public static final String TASK_GET_LOG_CONFIG = "get_log_config";
	public static final String TASK_SCHED_INFO = "sched_info";
	public static final String TASK_GET_NOTI = "get_noti";
	public static final String TASK_SET_NOTI = "set_noti";
	public static final String TASK_BROWSER_URL = "browser_url";
	public static final String TASK_GET_CONTROLCENTER_ITEMS = "get_controlcenter_items";
	public static final String TASK_GET_POLICYKIT_CONFIG = "get_policykit_config";
	public static final String TASK_UPDATE_POLLING_TIME = "update_polling_time";
	public static final String TASK_GET_ACCOUNT_CONFIG = "get_account_config";
	public static final String TASK_SET_AUTHORITY_CONFIG_LOCAL = "set_authority_config_local";
	public static final String TASK_GET_POLKIT_ADMIN_CONFIG = "get_polkit_admin_config";
	
	public static final String SOURCESLIST_PATH = prop.getProperty("gooroom.sourceslist.path");
	public static final String PREFERENCES_PATH = prop.getProperty("gooroom.preferences.path");
	public static final String GRAC_PATH = prop.getProperty("gooroom.grac.path");
	public static final String HOSTS_PATH = prop.getProperty("gooroom.hosts.path");
	public static final String BROWSER_RULE_DEFAULT_PATH = prop.getProperty("gooroom.browser.rule.default.path");
	public static final String BROWSER_RULE_TRUST_PATH = prop.getProperty("gooroom.browser.rule.trust.path");
	public static final String BROWSER_RULE_UNTRUST_PATH = prop.getProperty("gooroom.browser.rule.untrust.path");
	public static final String BROWSER_RULE_TRUST_EXT_PATH = prop.getProperty("gooroom.browser.rule.trust.ext.path");
	public static final String BROWSER_RULE_UNTRUST_EXT_PATH = prop.getProperty("gooroom.browser.rule.untrust.ext.path");
	public static final String LOG_CONFIG_PATH = prop.getProperty("gooroom.log.config.path");
	public static final String POLKIT_PATH = prop.getProperty("gooroom.polkit.path");
	
	public static final String IP_HIT_SPAN = prop.getProperty("gooroom.ip.hit.span");
	
	public static final String SCHED_INFO_TIMEOUT = prop.getProperty("gooroom.sched.info.timeout");
}
