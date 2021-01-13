package kr.gooroom.gpms.common.utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.core.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.gooroom.gpms.grm.serveragent.service.ClientInfoVO;
import kr.gooroom.gpms.grm.serveragent.service.ClientJobService;
import kr.gooroom.gpms.grm.serveragent.service.ClientLoginVO;
import kr.gooroom.gpms.grm.serveragent.service.ClientSecurityStateVO;
import kr.gooroom.gpms.grm.serveragent.service.LogBrowserVO;
import kr.gooroom.gpms.grm.serveragent.service.LogGeneralVO;
import kr.gooroom.gpms.grm.serveragent.service.LogSecurity2VO;
import kr.gooroom.gpms.grm.serveragent.service.LogSecurityVO;
import kr.gooroom.gpms.grm.serveragent.service.NotiVO;
import kr.gooroom.gpms.grm.serveragent.service.PackageServerVO;
import kr.gooroom.gpms.grm.serveragent.service.PackageVO;
import kr.gooroom.gpms.grm.serveragent.service.PollingTimeVO;
import kr.gooroom.gpms.grm.serveragent.service.ProfileVO;
import kr.gooroom.gpms.grm.serveragent.service.RuleUtilService;
import kr.gooroom.gpms.grm.serveragent.service.SchedInfoVO;

public class Tasker {
    private static final Logger logger = LoggerFactory.getLogger(Tasker.class);
	static final Base64.Decoder DECODER = Base64.getMimeDecoder();
	static final Base64.Encoder ENCODER = Base64.getMimeEncoder();
	
	static final String TASK_SUCCESS = "";
	
    /*
     * 정책파일 서명을 위한 서버의 개인키(RSA)
     */
    PrivateKey _privateKey = null;
    
    public Tasker() {
    	/*
    	 * 정책파일 서명을 위한 서버의 개인키를 로드
    	 */
    	try {
			Path path = Paths.get(Constant.CERTIFICATE_KEY);
			String sServerKey = new String(Files.readAllBytes(path));
			byte[] bServerKey = 
					DECODER.decode(
							sServerKey.trim()
							.replaceAll("-----\\w+ PRIVATE KEY-----", "")
							.replaceAll("-----\\w+ RSA PRIVATE KEY-----", ""));
			
		    PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(bServerKey);
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		    _privateKey = keyFactory.generatePrivate(privateSpec);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * 클라이언트에서 전송한 JOB의 task를 처리
     * @param clientJobService
     * @param ruleUtilService
     * @param clientId
     * @param clientIp
     * @param task
     * @return
     */
	public String doTask(
			ClientJobService clientJobService,
			RuleUtilService ruleUtilService,
			String clientId, 
			String clientIp, 
			HashMap<String,Object> task) {
		
		String taskName = (String)task.get(Constant.J_TASKN);
		HashMap<String, Object> jOut = new HashMap<String, Object>();
		
		/* 
		 * CLIENT_SYNC
		 * 클라이언트의 접속상태가 오프라인에서 온라인으로 변경되었을 때
		 */
		if (taskName.equals(Constant.TASK_CLIENT_SYNC)) {
			return clientSync(clientJobService, ruleUtilService, clientId, clientIp, task);
		}
		/*
		 * CLIENT_USER_SYNC
		 * 클라이언트의 접속상태가 오프라인에서 온라인으로 변경되었을 때
		 * 사용자가 로그인을 했을 경우
		 */
		else if (taskName.equals(Constant.TASK_CLIENT_USER_SYNC)) {
			return clientUserSync(clientJobService, ruleUtilService, clientId, clientIp, task);
		}
		/*
		 * GRM_HEARTBEAT
		 * 에이전트GUI의 연결상태를 확인(agentgui->agent->server)
		 */
		else if (taskName.equals(Constant.TASK_GRM_HEARTBEAT)) {
			return Constant.TR_NO_AGENT_DATA;
		}
		/*
		 * GET_SERVER_TIME
		 * NTP를 대체하기위해서 클라이언트는 서버의 시간과 동기화
		 */
		else if (taskName.equals(Constant.TASK_GET_SERVER_TIME)) {
			try {
				HashMap<String, Object> jRes = new HashMap<String, Object>();
				jRes.put("time", serverTime());
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_PROFILING, e.toString());
			}
		}
		/*
		 * PROFILING_PACKAGES
		 * 프로파일링 참조 클라이언트의 패키지정보를 저장
		 */
		else if (taskName.equals(Constant.TASK_PROFILING_PACKAGES)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				List<String> packageList = (List<String>)moduleRequest.get("pkg_list");
				String profileNo = (String)moduleRequest.get("profile_no");
				
				//String id = (String)moduleRequest.get("id");
				List<ProfileVO> profileVoList = new ArrayList<ProfileVO>();
				
				for (String pkg : packageList) {				
					String[] pkgSplited = pkg.split(",");
					String name = pkgSplited[0];
					//String arch = pkgSplited[1];
					//String label = pkgSplited[2];
					//String version = pkgSplited[3];
					
					ProfileVO pv = new ProfileVO();
					pv.setProfileNo(profileNo);
					pv.setPackageId(name);
					profileVoList.add(pv);
				}
				
				if (profileVoList.size() > 0) {
					clientJobService.insertProfilingPackage(profileVoList);
				}
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_PROFILING_PACKAGES, e.toString());
			}
		}
		/*
		 * PROFILING
		 * 프로파일링 패키지리스트를 클라이언트에게 전송
		 */
		else if (taskName.equals(Constant.TASK_PROFILING)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String profileNo = (String)moduleRequest.get("profile_no");
				List<String> pkgList = clientJobService.selectProfilingPackageList(profileNo);
				if (pkgList != null && pkgList.size() > 0) {
					jOut.put("pkg_list", String.join(",", pkgList));
				}
				else {
					jOut.put("pkg_list", "");
					logger.info("profiling profileNo={}", profileNo);
				}
				task.put(Constant.J_RESPONSE, jOut);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_PROFILING, e.toString());
			}
		}
		/*
		 * SET_APT_CONF | SET_GPG_KEY
		 * apt.conf.d 저장과 gpgkey 생성을 위해 업데이트서버 정보 전송
		 */
		else if (taskName.equals(Constant.TASK_SET_APT_CONF) 
				|| taskName.equals(Constant.TASK_SET_GPG_KEY)) {
			
			HashMap<String, Object> jRes = new HashMap<String, Object>();
			String resultMsg = mainUpdateServerUrls(clientJobService, clientId, jRes);
			if (resultMsg != TASK_SUCCESS) {
				return resultMsg;
			}
			task.put(Constant.J_RESPONSE, jRes);
		}
		/*
		 * GET_PASSWORD_CYCLE
		 * 비밀번호 변경 주기
		 */
		else if (taskName.equals(Constant.TASK_GET_PASSWORD_CYCLE)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				ClientLoginVO clVo = new ClientLoginVO();
				clVo.setClientId(clientId);
				clVo.setLoginId(loginId);
				String password_time = clientJobService.selectPasswordCycle(clVo);
				jOut.put("password_time", password_time);
				task.put(Constant.J_RESPONSE, jOut);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_PASSWORD_CYCLE, e.toString());
			}
		}
		/*
		 * GET_SCREEN_TIME
		 * 화면잠금시간
		 */
		else if (taskName.equals(Constant.TASK_GET_SCREEN_TIME)
				|| taskName.equals(Constant.TASK_DPMS_OFF_TIME)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				ClientLoginVO clVo = new ClientLoginVO();
				clVo.setClientId(clientId);
				clVo.setLoginId(loginId);
				String screen_time = clientJobService.selectScreenTime(clVo);
				jOut.put("screen_time", screen_time);
				task.put(Constant.J_RESPONSE, jOut);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_SCREEN_TIME, e.toString());
			}
		}
		/*
		 * 패키지 추가/삭제 기능 조회
		 */
		else if (taskName.equals(Constant.TASK_GET_UPDATE_OPERATION) 
				|| taskName.equals(Constant.TASK_GET_UPDATE_OPERATION_WITH_LOGINID)
				|| taskName.equals(Constant.TASK_TELL_UPDATE_OPERATION)) {
			
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				ClientLoginVO clVo = new ClientLoginVO();
				clVo.setClientId(clientId);
				clVo.setLoginId(loginId);
				String operation = clientJobService.selectUpdateOperation(clVo);
				if (operation.equals("true")) {
					jOut.put("operation", "enable");
				}
				else {
					jOut.put("operation", "disable");
				}
				task.put(Constant.J_RESPONSE, jOut);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s|%s|%s=%s", 
						Constant.TASK_GET_UPDATE_OPERATION, 
						Constant.TASK_GET_UPDATE_OPERATION_WITH_LOGINID, 
						Constant.TASK_TELL_UPDATE_OPERATION,
						e.toString());
			}
		}
		/*
		 * GET_BROWSER_CONFIG
		 * 브라우저 정책 조회
		 */
		else if (taskName.equals(Constant.TASK_GET_BROWSER_CONFIG)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				ArrayList<String> fileNameList = new ArrayList<String>();		
				ArrayList<String> fileContentsList = new ArrayList<String>();
				ArrayList<String> signatureList = new ArrayList<String>();
				
				String resultMsg = browserConfig(
						ruleUtilService, 
						loginId, 
						clientId,
						fileNameList, 
						fileContentsList, 
						signatureList);
				
				if (!resultMsg.equals(TASK_SUCCESS)) {
					return resultMsg;
				}
				jOut.put("file_name_list", fileNameList);
				jOut.put("file_contents_list", fileContentsList);
				jOut.put("signature_list", signatureList);
				task.put(Constant.J_RESPONSE, jOut);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_BROWSER_CONFIG, e.toString());
			}
		}
		/*
		 * GET_MEDIA_CONFIG
		 * 매체제어 정책 조회
		 */
		else if (taskName.equals(Constant.TASK_GET_MEDIA_CONFIG)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				String gracConfig = ruleUtilService.getNetworkAndMediaRuleJson(loginId, clientId);	
				String signature = signing(gracConfig);
				String fileName = Constant.GRAC_PATH;
				
				jOut.put("file_name", fileName);
				jOut.put("file_contents", gracConfig);
				jOut.put("signature", signature);
				task.put(Constant.J_RESPONSE, jOut);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_MEDIA_CONFIG, e.toString());
			}
		}
		/*
		 * GET_SERVER_CERTIFICATE
		 * 서버가 서명한 정책을 검증하기 위해서 서버의 인증서를 전송
		 */
		else if (taskName.equals(Constant.TASK_GET_SERVER_CERTIFICATE)) {
			try {
				byte[] bCert = Files.readAllBytes(Paths.get(Constant.CERTIFICATE_CERT));
				String sCert = new String(bCert, StandardCharsets.UTF_8);
				jOut.put("file_contents", sCert);
				task.put(Constant.J_RESPONSE, jOut);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_SERVER_CERTIFICATE, e.toString());
			}
		}
		/*
		 * SCHED_INFO
		 * 스케줄러의 분산처리를 위한 정보를 저장
		 */
		else if (taskName.equals(Constant.TASK_SCHED_INFO)) {
			try {
				/*
				HashMap<?,?> log = (HashMap<?,?>)task.get(Constant.J_OUT);
				//inner ip
				String innerIp = (String)log.get("local_ip");
				//mac
				String mac = (String)log.get("mac");
				//arp list
				List<SchedInfoVO> sivo = new ArrayList<SchedInfoVO>();
				String arpListStr = (String)log.get("arp_list");
				String[] arpList = arpListStr.split(",");
				for (String al : arpList) {
					String[] ipmac = al.split("-");
					String arpIp = ipmac[0];
					String arpMac = ipmac[1];
					
					SchedInfoVO vo = new SchedInfoVO();
					vo.setInnerIp(arpIp);
					vo.setOuterIp(clientIp);
					vo.setMac(arpMac);
					vo.setBanjang(clientId);
					sivo.add(vo);
				}
				
				SchedInfoVO si = clientJobService.selectSchedInfo(clientId);
				
				if (si == null) {
					//반장이 없는 경우
					logger.info("SARABAL 반장 없이 없는 경우 si={}", si);
					SchedInfoVO vo = new SchedInfoVO();
					vo.setClientId(clientId);
					vo.setInnerIp(innerIp);
					vo.setOuterIp(clientIp);
					vo.setBanjang(clientId);
					vo.setMac(mac);
					clientJobService .insertSchedInfo(vo);
					clientJobService.updateSchedInfo(sivo);
				}
				else {
					String banjang = si.getBanjang();
					if (banjang.equals(clientId)) {
						//반장이 있고 반장이 자신인 경우
						logger.info("SARABAL 반장이 있고 반장이 자신인 경우");
						clientJobService.updateSchedInfo(sivo);
					}
					else {
						SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date nowDt = new Date();
						Date modDt = transFormat.parse(si.getModDt());
						long diffTime = nowDt.getTime() - modDt.getTime();
						
						//반장이 있고 반장이 자신이 아니고 반장임기가 끝난 경우
						if (diffTime > Long.parseLong(Constant.SCHED_INFO_TIMEOUT)) {
							logger.info("SARABAL 반장이 있고 반장이 자신이 아니고 반장임기가 끝난 경우");
							clientJobService.updateSchedInfo(sivo);
						}
						//반장이 있고 반장이 자신이 아니고 반장임기가 끝나지 않은 경우
						else {
							logger.info("SARABAL 반장이 있고 반장이 자신이 아니고 반장임기가 끝나지 않은 경우 (임기가 끝날 때까지 기다린다)");
						}
					}
				}
				*/
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_SCHED_INFO, e.toString());
			}
		}
		/*
		 * CLIENT_INFO
		 * 클라이언트 정보를 저장
		 */
		else if (taskName.equals(Constant.TASK_CLIENT_INFO)) {
			try {
				HashMap<?,?> log = (HashMap<?,?>)task.get(Constant.J_OUT);
				
				//TERMINAL INFO
				String terminalInfo = (String)log.get(Constant.J_TERMINAL_INFO);
				String[] info = terminalInfo.split(",");
				ClientInfoVO clientInfo = new ClientInfoVO();
				clientInfo.setClientId(clientId);
				
				if (info.length > 0) {
					clientInfo.setMac1(info[0]);
				}
				if (info.length > 1) {
					clientInfo.setOs(info[1]);
				}
				if (info.length > 2) {
					clientInfo.setKernel(info[2]);
				}
				if (info.length > 3) {
					clientInfo.setLocalIp(info[3]);
				}
				if (info.length > 4) {
					clientInfo.setStrgSize(Long.parseLong(info[4]));
				}
				if (info.length > 5) {
					clientInfo.setStrgUse(Long.parseLong(info[5]));
				}
				clientInfo.setIp(clientIp);

				String safeScore = (String)log.get(Constant.J_SAFE_SCORE);
				String[] fileNumAndScore = safeScore.split(",");
				clientInfo.setFileNumOfScore(Integer.parseInt(fileNumAndScore[0]));
				clientInfo.setScore(fileNumAndScore[1]);
				clientJobService.insertOrUpdateClientExt(clientInfo);
				//clientJobService.insertClientExtHist(clientId);
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_CLIENT_INFO, e.toString());
			}
		}
		/*
		 * GOOROOM_LOG
		 * 클라이언트의 구름패키지 로그를 저장
		 */
		else if (taskName.equals(Constant.TASK_GOOROOM_LOG)) {
			try {
				HashMap<?,?> req = (HashMap<?,?>)task.get(Constant.J_REQUEST);
				String userId = (String)req.get("user_id");
				HashMap<String, List<String>> logs = (HashMap<String, List<String>>)req.get("logs");
				
				List<LogGeneralVO> vos = new ArrayList<LogGeneralVO>();
				
				for (Map.Entry<String, List<String>> entry : logs.entrySet()) {
					String logItem = entry.getKey();
					List<String> logValueList = (List<String>)entry.getValue();
					
					for (String lv : logValueList) {
						String[] splited = lv.split(",,,");
						String priority = splited[1];
						LogGeneralVO vo = new LogGeneralVO();
						vo.setClientId(clientId);
						vo.setUserId(userId);
						vo.setLogItem(logItem);
						vo.setLogTp(priority);
						vo.setLogValue(lv);
						vos.add(vo);
					}
				}
				clientJobService.insertLogGeneral(vos);
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GOOROOM_LOG, e.toString());
			}
		}
		/*
		 * SECURITY_LOG
		 * 클라이언트의 (NEW)보안로그를 저장
		 */
		else if (taskName.equals(Constant.TASK_SECURITY_LOG)) {
			try {
				HashMap<String,?> logInfoRoot = (HashMap<String,?>)task.get(Constant.J_REQUEST);
				HashMap<String,?> logInfo = (HashMap<String,?>)logInfoRoot.get(Constant.J_LOGS);
				
				//CLIENT SECURITY STATE
				String osStatus = (String)logInfo.get(Constant.J_OS_STATUS);
				String exeStatus = (String)logInfo.get(Constant.J_EXE_STATUS);
				String bootStatus = (String)logInfo.get(Constant.J_BOOT_STATUS);
				String mediaStatus = (String)logInfo.get(Constant.J_MEDIA_STATUS);
				String osRun = (String)logInfo.get(Constant.J_OS_RUN);
				String exeRun = (String)logInfo.get(Constant.J_EXE_RUN);
				String bootRun = (String)logInfo.get(Constant.J_BOOT_RUN);
				String mediaRun = (String)logInfo.get(Constant.J_MEDIA_RUN);

				ClientSecurityStateVO clientSecurityStateVo = new ClientSecurityStateVO();
				clientSecurityStateVo.setOsStatus(osStatus);
				clientSecurityStateVo.setExeStatus(exeStatus);
				clientSecurityStateVo.setBootStatus(bootStatus);
				clientSecurityStateVo.setMediaStatus(mediaStatus);
				clientSecurityStateVo.setClientId(clientId);
				clientSecurityStateVo.setOsRun(osRun);
				clientSecurityStateVo.setExeRun(exeRun);
				clientSecurityStateVo.setBootRun(bootRun);
				clientSecurityStateVo.setMediaRun(mediaRun);
				
				clientJobService.insertOrUpdateClientSecurityState(clientSecurityStateVo);
				clientJobService.insertClientSecurityStateHist(clientId);
				
				//SECURITY LOG
				String userId = (String)logInfoRoot.get(Constant.J_USER_ID);

				String[] logItems = {
						Constant.J_OS_LOG, 
						Constant.J_EXE_LOG, 
						Constant.J_BOOT_LOG, 
						Constant.J_MEDIA_LOG, 
						Constant.J_AGENT_LOG};
				
				List<LogSecurity2VO> logSecurity2VoList = new ArrayList<LogSecurity2VO>();
				
				for (String logItem : logItems) {
					ArrayList<HashMap<String,Object>> logs = 
							(ArrayList<HashMap<String,Object>>)logInfo.get(logItem);
					if (logs == null) continue;
					
					for (HashMap<String, Object> log : logs) {
						String logValue = (String)log.get("log");
						String logTp = (String)log.get("type");
						String time = (String)log.get("time");
						String level = (String)log.get("level");
						String grmcode = (String)log.get("grmcode");
						String evalLevel = (String)log.get("eval_level");
						
						LogSecurity2VO vo = new LogSecurity2VO();
						vo.setClientId(clientId);
						vo.setLogCode(grmcode);
						vo.setLogDt(time);
						vo.setLogItem(logItem);
						vo.setLogLevel(level);
						vo.setLogTp(logTp);
						vo.setLogValue(logValue);
						vo.setUserId(userId);
						vo.setEvalLevel(evalLevel);
						logSecurity2VoList.add(vo);
					}
				}

				if (logSecurity2VoList.size() > 0) {
					clientJobService.insertLogSecurity2(logSecurity2VoList);
				}
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_SECURITY_LOG, e.toString());
			}
		}
		/*
		 * SUMMARY_LOG
		 * 클라이언트의 보안로그를 저장
		 */
		else if (taskName.equals(Constant.TASK_SUMMARY_LOG)) {
			try {
				HashMap<?,?> log = (HashMap<?,?>)task.get(Constant.J_REQUEST);
				
				//CLIENT SECURITY STATE
				String osStatus = (String)log.get(Constant.J_OS_STATUS);
				String osLog = (String)log.get(Constant.J_OS_LOG);
				String exeStatus = (String)log.get(Constant.J_EXE_STATUS);
				String exeLog = (String)log.get(Constant.J_EXE_LOG);
				String bootStatus = (String)log.get(Constant.J_BOOT_STATUS);
				String bootLog = (String)log.get(Constant.J_BOOT_LOG);
				String mediaStatus = (String)log.get(Constant.J_MEDIA_STATUS);
				String mediaLog = (String)log.get(Constant.J_MEDIA_LOG);
				String userId = (String)log.get(Constant.J_USER_ID);
				String osRun = (String)log.get(Constant.J_OS_RUN);
				String exeRun = (String)log.get(Constant.J_EXE_RUN);
				String bootRun = (String)log.get(Constant.J_BOOT_RUN);
				String mediaRun = (String)log.get(Constant.J_MEDIA_RUN);

				ClientSecurityStateVO clientSecurityStateVo = new ClientSecurityStateVO();
				clientSecurityStateVo.setOsStatus(osStatus);
				clientSecurityStateVo.setExeStatus(exeStatus);
				clientSecurityStateVo.setBootStatus(bootStatus);
				clientSecurityStateVo.setMediaStatus(mediaStatus);
				clientSecurityStateVo.setClientId(clientId);
				clientSecurityStateVo.setOsRun(osRun);
				clientSecurityStateVo.setExeRun(exeRun);
				clientSecurityStateVo.setBootRun(bootRun);
				clientSecurityStateVo.setMediaRun(mediaRun);
				
				clientJobService.insertOrUpdateClientSecurityState(clientSecurityStateVo);
				clientJobService.insertClientSecurityStateHist(clientId);
				
				//SECURITY LOG
				List<LogSecurityVO> logSecurityVoList = new ArrayList<LogSecurityVO>();
				if (osLog != null && osLog.length() > 0) {
					LogSecurityVO vo = new LogSecurityVO();
					vo.setClientId(clientId);
					vo.setLogItem("osProtector");
					vo.setLogTp(osStatus);
					vo.setLogValue(osLog);
					vo.setUserId(userId);
					logSecurityVoList.add(vo);
				}
				if (bootLog != null && bootLog.length() > 0) {
					LogSecurityVO vo = new LogSecurityVO();
					vo.setClientId(clientId);
					vo.setLogItem("bootProtector");
					vo.setLogTp(bootStatus);
					vo.setLogValue(bootLog);
					vo.setUserId(userId);
					logSecurityVoList.add(vo);
				}
				if (exeLog != null && exeLog.length() > 0) {
					LogSecurityVO vo = new LogSecurityVO();
					vo.setClientId(clientId);
					vo.setLogItem("exeProtector");
					vo.setLogTp(exeStatus);
					vo.setLogValue(exeLog);
					vo.setUserId(userId);
					logSecurityVoList.add(vo);
				}
				if (mediaLog != null && mediaLog.length() > 0) {
					LogSecurityVO vo = new LogSecurityVO();
					vo.setClientId(clientId);
					vo.setLogItem("mediaProtector");
					vo.setLogTp(mediaStatus);
					vo.setLogValue(mediaLog);
					vo.setUserId(userId);
					logSecurityVoList.add(vo);
				}
				if (logSecurityVoList.size() > 0) {
					clientJobService.insertLogSecurity(logSecurityVoList);
				}
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_SUMMARY_LOG, e.toString());
			}
		}
		/*
		 * GET_UPDATE_SERVER_CONFIG
		 * 업데이트 서버 정보(sourceslist, preference)를 반환
		 */
		else if (taskName.equals(Constant.TASK_GET_UPDATE_SERVER_CONFIG)) {
			try {
				ArrayList<String> fileNameList = new ArrayList<String>();
				ArrayList<String> fileContentsList = new ArrayList<String>();
				ArrayList<String> signatureList = new ArrayList<String>();
				HashMap<String, Object> jRes = new HashMap<String, Object>();
				
				updateServerInfo(clientJobService, clientId, fileNameList, fileContentsList, signatureList);
				
				jRes.put("file_name_list", fileNameList);
				jRes.put("file_contents_list", fileContentsList);
				jRes.put("signature_list", signatureList);
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_UPDATE_SERVER_CONFIG, e.toString());
			}
		}
		/*
		 * APPEND_CONTENTS_ETC_HOSTS
		 * 호스트 정보를 반환
		 */
		else if (taskName.equals(Constant.TASK_APPEND_CONTENTS_ETC_HOSTS)) {
			try {
				String etcHosts = clientJobService.selectEtcHostsContents(clientId);
				HashMap<String, Object> jIn = new HashMap<String, Object>();
				jIn.put("file_contents", etcHosts);
				jIn.put("signature", signing(etcHosts));
				task.put(Constant.J_RESPONSE, jIn);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_APPEND_CONTENTS_ETC_HOSTS, e.toString());
			}
		}
		/*
		 * SET_AUTHORITY_CONFIG
		 * 보안기술요소 설정 반환
		 */
		else if (taskName.equals(Constant.TASK_SET_AUTHORITY_CONFIG)) {
			try {
				ArrayList<String> fileNameList = new ArrayList<String>();
				ArrayList<String> fileContentsList = new ArrayList<String>();
				ArrayList<String> signatureList = new ArrayList<String>();
				
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				HashMap<String, Object> jRes = new HashMap<String, Object>();
				
				securityConfig(ruleUtilService, loginId, clientId, fileNameList, fileContentsList, signatureList);
				
				/*
				 * 폴킷 정책
				 */
				String polkitConfig = ruleUtilService.getPolicyKitRuleJson(loginId, clientId);
				if (polkitConfig != null && polkitConfig.length() != 0) {
					fileNameList.add(Constant.POLKIT_PATH);
					fileContentsList.add(polkitConfig);
					signatureList.add(signing(polkitConfig));
				}
				
				/*
				 * 폴킷 관리자 설정
				 */
				String polkitAdmin = clientJobService.selectPolkitAdmin(clientId);
				jRes.put("polkit_admin", polkitAdmin);
				
				jRes.put("file_name_list", fileNameList);
				jRes.put("file_contents_list", fileContentsList);
				jRes.put("signature_list", signatureList);
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_SET_AUTHORITY_CONFIG, e.toString());
			}
		}
		/*
		 * UPDATE_PACKAGE_VERSION_TO_SERVER
		 * 클라이언트의 패키지 정보를 저장
		 */
		else if (taskName.equals(Constant.TASK_UPDATE_PACKAGE_VERSION_TO_SERVER)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				List<String> packageList = (List<String>)moduleRequest.get("pkg_list");
				List<PackageVO> packageVoList = new ArrayList<PackageVO>();
				List<PackageVO> packageVoDelList = new ArrayList<PackageVO>();
				
				String requestId = (String)moduleRequest.get("id");
				if (requestId.equals("installed")) {
					clientJobService.deleteClientPackage(clientId);
				}
				
				for (String pkg : packageList) {				
					String[] pkgSplited = pkg.split(",");
					String name = pkgSplited[0];
					String installVersion = pkgSplited[1];
					String candidateVersion = pkgSplited[2];
					String arch = pkgSplited[3];
					//String label = pkgSplited[4];
					
					String flag = null;
					if (pkgSplited.length > 5) {
						flag = pkgSplited[5];
					}
					
					PackageVO pv = new PackageVO();
					pv.setClientId(clientId);
					pv.setPackageId(name);
					pv.setInstallVer(installVersion);
					pv.setPackageLastVer(candidateVersion);
					pv.setPackageArch(arch);
					
					if (flag == null || flag.equals("U")) {
						packageVoList.add(pv);
					}
					else {
						packageVoDelList.add(pv);
					}
				}
				
				if (packageVoList.size() > 0) {
					clientJobService.insertOrUpdatePackage(packageVoList);
				}
				if (packageVoDelList.size() > 0) {
					for (PackageVO vo : packageVoDelList) {
						clientJobService.deletePackage(vo);
					}
				}
				clientJobService.insertOrUpdatePackageState(clientId);
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_UPDATE_PACKAGE_VERSION_TO_SERVER, e.toString());
			}
		}
		/*
		 * INSERT_ALL_PACKAGE_TO_SERVER
		 * 클라이언트의 모든 패키지 정보를 저장(패키지 마스터)
		 */
		else if (taskName.equals(Constant.TASK_INSERT_ALL_PACKAGES_TO_SERVER)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				List<String> packageList = (List<String>)moduleRequest.get("pkg_list");
				String id = (String)moduleRequest.get("id");
				List<PackageVO> packageVoList = new ArrayList<PackageVO>();
				
				for (String pkg : packageList) {				
					String[] pkgSplited = pkg.split(",");
					String name = pkgSplited[0];
					String arch = pkgSplited[1];
					String label = pkgSplited[2];
					String version = pkgSplited[3];
					
					PackageVO pv = new PackageVO();
					pv.setPackageId(name);
					pv.setPackageArch(arch);
					pv.setPackageLastVer(version);
					pv.setRepoLabel(label);
					packageVoList.add(pv);
				}
				
				if (packageVoList.size() > 0) {
					if (id.equals("head")) {
						clientJobService.deletePackageMstr();
					}
					clientJobService.insertPackageMstr(packageVoList);
				}
				
				//전체 패키지를 업데이트하면 클라이언트들의 패키지상태를 갱신
				if (id.equals("tail")) {
					List<String> cids = clientJobService.selectAllClients();
					for (String cid : cids) {
						clientJobService.insertOrUpdatePackageState(cid);
					}
				}
				
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_INSERT_ALL_PACKAGES_TO_SERVER, e.toString());
			}
		}
		/*
		 * GET_SERVERJOB_DISPATCH_TIME
		 * 클라이언트의 폴링주기 반환
		 */
		else if (taskName.equals(Constant.TASK_GET_SERVERJOB_DISPATCH_TIME)) {
			try {				
				HashMap<String, Object> jIn = new HashMap<String, Object>();
				String dispatchTime = clientJobService.selectOneServerjobDispatchTime(Constant.SITE_NAME);
				jIn.put("dispatch_time", dispatchTime);
				task.put(Constant.J_RESPONSE, jIn);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_SERVERJOB_DISPATCH_TIME, e.toString());
			}
		}
		/*
		 * GET_HYPERVISOR_OPERATION
		 * 하이퍼바이저(gop) 사용 유무
		 */
		else if (taskName.equals(Constant.TASK_GET_HYPERVISOR_OPERATION)) {
			try {				
				HashMap<String, Object> jIn = new HashMap<String, Object>();
				String operation = "true";//clientJobService.selectHypervisorOperation(clientId);
				if (operation == null || operation.equals("true")) {
					operation = "enable";
				}
				else {
					operation = "disable";
				}
				jIn.put("operation", operation);
				task.put(Constant.J_RESPONSE, jIn);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_HYPERVISOR_OPERATION, e.toString());
			}
		}
		/*
		 * CLEAR_SECURITY_ALARM
		 * 클라이언트의 침해해제 요청을 처리
		 */
		else if (taskName.equals(Constant.TASK_CLEAR_SECURITY_ALARM)) {
			try {
				ClientSecurityStateVO clientSecurityStateVo = new ClientSecurityStateVO();
				clientSecurityStateVo.setClientId(clientId);
				clientJobService.insertOrUpdateClientSecurityStateClear(clientSecurityStateVo);
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_CLEAR_SECURITY_ALARM, e.toString());
			}
		}
		/*
		 * GET_APP_LIST | SET_APP_LIST
		 * 클라이언트에 설치된 어플리케이션 정보를 제공
		 * -블랙리스트 
		 */
		else if (taskName.equals(Constant.TASK_GET_APP_LIST) 
				| taskName.equals(Constant.TASK_SET_APP_LIST)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				HashMap<String, Object> jRes = new HashMap<String, Object>();

				ClientLoginVO clVo = new ClientLoginVO();
				clVo.setClientId(clientId);
				clVo.setLoginId(loginId);
				List<String> blackList = clientJobService.selectAppBlackList(clVo);
				String blackListString = "";
				for (String desktopId : blackList) {
					blackListString += String.format("%s,,,", desktopId);
				}
				if (blackListString.length() > 0) {
					blackListString = StringUtils.substring(blackListString, 0, -1);
				}
				jRes.put("black_list", blackListString);
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_APP_LIST, e.toString());
			}
		}
		/*
		 * GET_LOG_CONFIG
		 * 로그 설정(저널설정포함)
		 */
		else if (taskName.equals(Constant.TASK_GET_LOG_CONFIG)) {
			try {
				HashMap<String, Object> jRes = new HashMap<String, Object>();
				
				//로그 설정
				String logConfig = ruleUtilService.getLogConfigJson(clientId);
				jRes.put("file_name", Constant.LOG_CONFIG_PATH);
				jRes.put("file_contents", logConfig);
				jRes.put("signature", signing(logConfig));
				
				//저널 로그 설정
				String journalConfig = ruleUtilService.getJournaldConfigJson(clientId);
				jRes.put("journal_conf", journalConfig);
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_LOG_CONFIG, e.toString());
			}
		}
		/*
		 * GET_NOTI
		 * 공지사항 검색
		 */
		else if (taskName.equals(Constant.TASK_GET_NOTI)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				HashMap<String, Object> jRes = new HashMap<String, Object>();

				ClientLoginVO cl = new ClientLoginVO();
				cl.setClientId(clientId);
				cl.setLoginId(loginId);
				
				//default noti domain
				String defaultNotiDomain = clientJobService.selectDefaultNotiDomain();
				
				//notis
				List<NotiVO> notis = clientJobService.selectNoti(cl);
				int disabledTitleViewCnt = 0;
				HashMap<String, Object> notiInfo = new HashMap<String, Object>();
				List<HashMap<String, String>> enabledTitleViewNotis = new ArrayList<HashMap<String, String>>();
				for (NotiVO vo : notis) {
					if (vo.getViewType().equals("1")) {
						HashMap<String, String> noti = new HashMap<String, String>();
						noti.put("url", defaultNotiDomain + "#npid=" + vo.getUrl());
						noti.put("title", vo.getTitle());
						enabledTitleViewNotis.add(noti);
					}
					else {
						disabledTitleViewCnt += 1;
					}
				}
				notiInfo.put("enabled_title_view_notis", enabledTitleViewNotis);
				notiInfo.put("disabled_title_view_cnt", disabledTitleViewCnt);
				notiInfo.put("client_id", clientId);
				notiInfo.put("default_noti_domain", defaultNotiDomain);
				
				jRes.put("noti_info", notiInfo);
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_NOTI, e.toString());
			}
		}
		/*
		 * SET_NOTI
		 * 즉시공지사항 검색
		 */
		else if (taskName.equals(Constant.TASK_SET_NOTI)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				String noticePublishId = (String)moduleRequest.get("notice_publish_id");
				
				HashMap<String, Object> jRes = new HashMap<String, Object>();

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("clientId", clientId);
				params.put("loginId",  loginId);
				params.put("noticePublishId",  noticePublishId);
				
				//default noti domain
				String defaultNotiDomain = clientJobService.selectDefaultNotiDomain();
				
				//instance notis
				List<NotiVO> notis = clientJobService.selectInstanceNoti(params);
				int disabledTitleViewCnt = 0;
				HashMap<String, Object> notiInfo = new HashMap<String, Object>();
				List<HashMap<String, String>> enabledTitleViewNotis = new ArrayList<HashMap<String, String>>();
				for (NotiVO vo : notis) {
					if (vo.getViewType().equals("1")) {
						HashMap<String, String> noti = new HashMap<String, String>();
						noti.put("url", defaultNotiDomain + "#npid=" + vo.getUrl());
						noti.put("title", vo.getTitle());
						enabledTitleViewNotis.add(noti);
					}
					else {
						disabledTitleViewCnt += 1;
					}
				}
				notiInfo.put("enabled_title_view_notis", enabledTitleViewNotis);
				notiInfo.put("disabled_title_view_cnt", disabledTitleViewCnt);
				notiInfo.put("client_id", clientId);
				notiInfo.put("default_noti_domain", defaultNotiDomain);
				
				jRes.put("noti_info", notiInfo);
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_SET_NOTI, e.toString());
			}
		}
		/*
		 * BROWSER_URL
		 * 브라우저 대용량 로그 저장
		 */
		else if (taskName.equals(Constant.TASK_BROWSER_URL)) {
			try {
				HashMap<?,?> request = (HashMap<?,?>)task.get(Constant.J_REQUEST);
				
				List<String> logs = (List<String>)request.get("logs");
				List<LogBrowserVO> vos = new ArrayList<LogBrowserVO>();
				
				for (String log : logs) {
					logger.debug("SARABAL log={}", log);
					String[] splited = log.split(",");
					String logDt = splited[0];
					String userId = splited[1];
					String logTp = splited[2];
					String url = splited[3];
					LogBrowserVO vo = new LogBrowserVO();
					vo.setClientId(clientId);
					vo.setLogDt(logDt);
					vo.setLogTp(logTp);
					vo.setUserId(userId);
					vo.setUrl(url);
					vos.add(vo);
				}
				if (vos.size() > 0) {
					clientJobService.insertLogBrowser(vos);
				}
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_BROWSER_URL, e.toString());
			}
		}
		/*
		 * GET_CONTROLCENTER_ITEMS
		 * 단말의 제어판 표시 항목
		 */
		else if (taskName.equals(Constant.TASK_GET_CONTROLCENTER_ITEMS)) {
			try {
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				HashMap<String, Object> jRes = new HashMap<String, Object>();

				ClientLoginVO clVo = new ClientLoginVO();
				clVo.setClientId(clientId);
				clVo.setLoginId(loginId);
				List<String> controlcenterItems = clientJobService.selectControlcenterItems(clVo);
				String controlcenterItemsString = "";
				for (String item : controlcenterItems) {
					controlcenterItemsString += String.format("%s,", item);
				}
				if (controlcenterItemsString.length() > 0) {
					controlcenterItemsString = StringUtils.substring(controlcenterItemsString, 0, -1);
				}
				jRes.put("controlcenter_items", controlcenterItemsString);
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_CONTROLCENTER_ITEMS, e.toString());
			}
		}
		/*
		 * GET_POLICYKIT_CONFIG
		 * 폴킷 정책
		 */
		else if (taskName.equals(Constant.TASK_GET_POLICYKIT_CONFIG)) {
			try {	
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				String loginId = (String)moduleRequest.get("login_id");
				String polkitConfig = ruleUtilService.getPolicyKitRuleJson(loginId, clientId);	
				String signature = signing(polkitConfig);
				String fileName = Constant.POLKIT_PATH;
				
				jOut.put("file_name", fileName);
				jOut.put("file_contents", polkitConfig);
				jOut.put("signature", signature);
				task.put(Constant.J_RESPONSE, jOut);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_POLICYKIT_CONFIG, e.toString());
			}
		}
		/*
		 * UPDATE_POLLING_TIME
		 * 폴킷 정책
		 */
		else if (taskName.equals(Constant.TASK_UPDATE_POLLING_TIME)) {
			try {	
				HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get("request");
				PollingTimeVO vo = new PollingTimeVO();
				vo.setClientId(clientId);
				vo.setPollingTime((String)moduleRequest.get("polling_time"));
				
				clientJobService.updatePollingTime(vo);
				
				return Constant.TR_NO_AGENT_DATA;
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_UPDATE_POLLING_TIME, e.toString());
			}
		}
		/*
		 * GET_ACCOUNT_CONFIG
		 * ROOT/SUDO 
		 */
		else if (taskName.equals(Constant.TASK_GET_ACCOUNT_CONFIG)) {
			try {	
				HashMap<String, Object> jRes = new HashMap<String, Object>();
				String rootUse = clientJobService.selectRootUse(clientId);
				String sudoUse = clientJobService.selectSudoUse(clientId);
				jRes.put("root_use", rootUse);
				jRes.put("sudo_use", sudoUse);
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_UPDATE_POLLING_TIME, e.toString());
			}
		}
		/*
		 * SET_AUTHORITY_CONFIG_LOCAL
		 * 로컬유저 로그인 시
		 */
		else if (taskName.equals(Constant.TASK_SET_AUTHORITY_CONFIG_LOCAL)) {
			try {	
				HashMap<String, Object> jRes = new HashMap<String, Object>();
				String polkitAdmin = clientJobService.selectPolkitAdmin(clientId);
				jRes.put("polkit_admin", polkitAdmin);
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_SET_AUTHORITY_CONFIG_LOCAL, e.toString());
			}
		}
		/*
		 * GET_POLKIT_ADMIN_CONFIG
		 * 폴킷 관리자 설정
		 */
		else if (taskName.equals(Constant.TASK_GET_POLKIT_ADMIN_CONFIG)) {
			try {	
				HashMap<String, Object> jRes = new HashMap<String, Object>();
				String polkitAdmin = clientJobService.selectPolkitAdmin(clientId);
				jRes.put("polkit_admin", polkitAdmin);
				task.put(Constant.J_RESPONSE, jRes);
			}
			catch (Exception e) {
				e.printStackTrace();
				return String.format("%s=%s", Constant.TASK_GET_POLKIT_ADMIN_CONFIG, e.toString());
			}
		}
		return Constant.TR_OK;
	}

	
	/**
	 * 브라우저 정책 조회
	 * @param ruleUtilService
	 * @param loginId
	 * @param fileNameList
	 * @param fileContentsList
	 * @param signatureList
	 * @return
	 */
	private String browserConfig(
			RuleUtilService ruleUtilService,
			String loginId,
			String clientId,
			ArrayList<String> fileNameList, 
			ArrayList<String> fileContentsList, 
			ArrayList<String> signatureList) {
		
		String resultMsg = TASK_SUCCESS;
		try {
			//BROWSER DEFAULT RULE
			String defaultRule = ruleUtilService.getBrowserRuleJson(loginId, clientId);
			if (defaultRule != null && defaultRule.length() != 0) {
				fileContentsList.add(defaultRule);
				signatureList.add(signing(defaultRule));
				fileNameList.add(Constant.BROWSER_RULE_DEFAULT_PATH);
			}
	
			//BROWSER TRUST RULE
			String trustRule = ruleUtilService.getBrowserRuleSiteSetupJson(loginId, clientId, "TRUST");
			if (trustRule != null && trustRule.length() != 0) {
				fileContentsList.add(trustRule);
				signatureList.add(signing(trustRule));
				fileNameList.add(Constant.BROWSER_RULE_TRUST_PATH);
			}
			
			//BROWSER UNTRUST RULE
			String untrustRule = ruleUtilService.getBrowserRuleSiteSetupJson(loginId, clientId, "UNTRUST");
			if (untrustRule != null && untrustRule.length() != 0) {
				fileContentsList.add(untrustRule);
				signatureList.add(signing(untrustRule));
				fileNameList.add(Constant.BROWSER_RULE_UNTRUST_PATH);
			}
			
			//BROWSER TRUST EXT RULE
			String trustExtRule = ruleUtilService.getBrowserExtRuleJson(loginId, clientId, "TRUST");
			if (trustExtRule != null && trustExtRule.length() != 0) {
				fileContentsList.add(trustExtRule);
				signatureList.add(signing(trustExtRule));
				fileNameList.add(Constant.BROWSER_RULE_TRUST_EXT_PATH);
			}
			
			//BROWSER UNTRUST EXT RULE
			String untrustExtRule = ruleUtilService.getBrowserExtRuleJson(loginId, clientId, "UNTRUST");
			if (untrustExtRule != null && untrustExtRule.length() != 0) {
				fileContentsList.add(untrustExtRule);
				signatureList.add(signing(untrustExtRule));
				fileNameList.add(Constant.BROWSER_RULE_UNTRUST_EXT_PATH);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			resultMsg = String.format("%s=%s", Constant.TASK_GET_BROWSER_CONFIG, e.toString());
			
		}
		return resultMsg;
	}
	/**
	 * 업데이트서버의 메인서버주소를 파라미터에 저장하고
	 * 호환성을 유지하기 위해 에러메시지를 반환한다.
	 * @param result
	 * @return
	 */
	private String mainUpdateServerUrls(
			ClientJobService clientJobService, 
			String clientId, 
			HashMap<String, Object> result) {
		
		String resultMsg = TASK_SUCCESS;
		try {
			PackageServerVO ps = clientJobService.selectUpdateServerFiles(clientId);
			if (ps == null) {
				result.put("update_base_urls", "");
				resultMsg = "UPDATE SERVER CONFIG IS EMPTY:SET-APT-CONF|SET-GPG-KEY";
			}
			else {
				String mainOsRepo = ps.getMainOsRepo();	
				if (mainOsRepo == null) {
					result.put("update_base_urls", "");
					resultMsg = "DB error:package server info empty:set-apt-conf|set-gpg-key";
				}
				else {
					result.put("update_base_urls", mainOsRepo);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			result.put("update_base_urls", "");
			resultMsg = String.format("%s|%s=%s", Constant.TASK_SET_APT_CONF, Constant.TASK_SET_GPG_KEY, e.toString());
		}
		return resultMsg;
	}

	/**
	 * 클라이언트와 동기화할 서버시간을 구한다.
	 * @return
	 */
	private String serverTime() {
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		int y = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DAY_OF_MONTH);
		int h = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int s = cal.get(Calendar.SECOND);
		int mil = cal.get(Calendar.MILLISECOND);
		return String.format("%d,%d,%d,%d,%d,%d,%d", y, mon, d, h, min, s, mil);
	}
	
	/**
	 * 업데이트 서버의 정보를 조회
	 * @param clientJobService
	 * @param clientId
	 * @param fileNameList
	 * @param fileContentsList
	 * @param signatureList
	 * @throws Exception
	 */
	private void updateServerInfo(
			ClientJobService clientJobService, 
			String clientId, 
			ArrayList<String> fileNameList,
			ArrayList<String> fileContentsList,
			ArrayList<String> signatureList) throws Exception {
		
		PackageServerVO ps = clientJobService.selectUpdateServerFiles(clientId);
		if (ps == null) {
			throw new Exception("UPDATE SERVER INFO IS EMPTY IN GPMS");
		}
		
		String sourcelists = "";
		if (ps.getMainOsRepo().length() != 0 || ps.getBaseOsRepo().length() != 0) {
			sourcelists = ps.getMainOsRepo() + "\n" + ps.getBaseOsRepo();
		}
		fileNameList.add(Constant.SOURCESLIST_PATH);
		fileContentsList.add(sourcelists);
		signatureList.add(signing(sourcelists));
		
		String preferences = ps.getRepoPreference();
		fileNameList.add(Constant.PREFERENCES_PATH);
		fileContentsList.add(preferences);
		signatureList.add(signing(preferences));
	}
	
	/**
	 * 클라이언트의 보안기술요소(GOP,GEP,GBP,GRAC,BROWSER 등)의 설정 반환
	 * @param ruleUtilService
	 * @param loginId
	 * @param fileNameList
	 * @param fileContentsList
	 * @param signatureList
	 * @throws Exception
	 */
	private void securityConfig(			
			RuleUtilService ruleUtilService, 
			String loginId,
			String clientId,
			ArrayList<String> fileNameList,
			ArrayList<String> fileContentsList,
			ArrayList<String> signatureList) throws Exception {
		
		//GRAC 
		String gracConfig = ruleUtilService.getNetworkAndMediaRuleJson(loginId, clientId);
		if (gracConfig != null && gracConfig.length() != 0) {
			fileNameList.add(Constant.GRAC_PATH);
			fileContentsList.add(gracConfig);
			signatureList.add(signing(gracConfig));
		}
		
		//BROWSER
		browserConfig(
				ruleUtilService,
				loginId,
				clientId,
				fileNameList, 
				fileContentsList, 
				signatureList);
	}
	/**
	 * 정책서명
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String signing(String data) throws Exception {
		if (_privateKey == null) {
			throw new Exception("THERE IS NO SIGNING KEY");
		}
		
		try {  
		    Signature signature = Signature.getInstance("SHA256withRSA");
		    signature.initSign(_privateKey);
		    signature.update(data.getBytes("UTF-8"));
		    byte[] bSign = signature.sign();
		    String sSign = ENCODER.encodeToString(bSign);
		    
		    return sSign;
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 클라이언트 정책 동기화
	 * @param clientJobService
	 * @param ruleUtilService
	 * @param clientId
	 * @param clientIp
	 * @param task
	 * @return
	 */
	public String clientSync(
			ClientJobService clientJobService,
			RuleUtilService ruleUtilService,
			String clientId, 
			String clientIp, 
			HashMap<String,Object> task) {
		
		try {
			ArrayList<String> fileNameList = new ArrayList<String>();
			ArrayList<String> fileContentsList = new ArrayList<String>();
			ArrayList<String> signatureList = new ArrayList<String>();
			
			HashMap<String, Object> jRes = new HashMap<String, Object>();
			
			/*
			 * 클라이언트에 파일로 저장될 모든 정책은 아래 리스트 형식으로 전송
			 */
			jRes.put("file_name_list", fileNameList);
			jRes.put("file_contents_list", fileContentsList);
			jRes.put("signature_list", signatureList);
			
			/*
			 * 서버 인증서 
			 */
			try {
				byte[] bCert = Files.readAllBytes(Paths.get(Constant.CERTIFICATE_CERT));
				String sCert = new String(bCert, StandardCharsets.UTF_8);
				jRes.put("certificate", sCert);
			}
			catch (Exception e) {
				e.printStackTrace();
				jRes.put("certificate", "");
			}
			/*
			 * 클라이언트 폴링주기
			 */
			try {
				String dispatchTime = clientJobService.selectOneServerjobDispatchTime(Constant.SITE_NAME);
				if (dispatchTime == null) {
					dispatchTime = "";
				}
				jRes.put("dispatch_time", dispatchTime);
			}
			catch (Exception e) {
				e.printStackTrace();
				jRes.put("dispatch_time", "");
			}
			/*
			 * HOSTS 정보
			 */
			try {
				
				String etcHosts = clientJobService.selectEtcHostsContents(clientId);
				fileNameList.add(Constant.HOSTS_PATH);
				fileContentsList.add(etcHosts);
				signatureList.add(signing(etcHosts));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * 하이퍼바이저 동작유무(gop)
			 */
			try {				
				String operation = "true";//clientJobService.selectHypervisorOperation(clientId);
				if (operation == null || operation.equals("true")) {
					operation = "enable";
				}
				else {
					operation = "disable";
				}
				jRes.put("hyper_operation", operation);
			}
			catch (Exception e) {
				e.printStackTrace();
				jRes.put("hyper_operation", "");
			}
			/*
			 * 서버 시간
			 */
			try {
				jRes.put("time", serverTime());
			}
			catch (Exception e) {
				e.printStackTrace();
				jRes.put("time", "");
			}
			/*
			 * 업데이트 서버 정보
			 */
			try {
				updateServerInfo(clientJobService, clientId, fileNameList, fileContentsList, signatureList);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * APT 설정 | GPG KEY 설정
			 */
			mainUpdateServerUrls(clientJobService, clientId, jRes);
			/*
			 * 홈폴더 초기화 여부
			 */
			try {
				String operation = clientJobService.selectHomefolderOperation(clientId);
				if (operation.equals("true")) {
					operation = "enable";
				}
				else {
					operation = "disable";
				}
				jRes.put("operation", operation);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * 로그 설정
			 */
			try {
				String logConfig = ruleUtilService.getLogConfigJson(clientId);
				fileNameList.add(Constant.LOG_CONFIG_PATH);
				fileContentsList.add(logConfig);
				signatureList.add(signing(logConfig));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * JOURNAL 로그 설정
			 */
			try {
				String journalConfig = ruleUtilService.getJournaldConfigJson(clientId);
				jRes.put("journal_conf", journalConfig);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * ROOT/SUDO 활성화/비활성화
			 */
			try {
				String rootUse = clientJobService.selectRootUse(clientId);
				String sudoUse = clientJobService.selectSudoUse(clientId);
				jRes.put("root_use", rootUse);
				jRes.put("sudo_use", sudoUse);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * POLKIT ADMIN CONFIG
			 */
			try {
				String polkitAdmin = clientJobService.selectPolkitAdmin(clientId);
				jRes.put("polkit_admin", polkitAdmin);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		task.put(Constant.J_RESPONSE, jRes);
		}
		catch (Exception e) {
			e.printStackTrace();
			return String.format("%s=%s", Constant.TASK_CLIENT_SYNC, e.toString());
		}
		return Constant.TR_OK;
	}
	
	/**
	 * 클라이언트유저 정책 동기화
	 * @param clientJobService
	 * @param ruleUtilService
	 * @param clientId
	 * @param clientIp
	 * @param task
	 * @return
	 */
	public String clientUserSync(
			ClientJobService clientJobService,
			RuleUtilService ruleUtilService,
			String clientId, 
			String clientIp, 
			HashMap<String,Object> task) {
		
		try {
			HashMap<String, Object> jRes = new HashMap<String, Object>();
			HashMap<?,?> moduleRequest = (HashMap<?,?>)task.get(Constant.J_REQUEST);
			String loginId = (String)moduleRequest.get("login_id");
			
			/*
			 * 보안기술요소 설정
			 */
			try {
				ArrayList<String> fileNameList = new ArrayList<String>();
				ArrayList<String> fileContentsList = new ArrayList<String>();
				ArrayList<String> signatureList = new ArrayList<String>();

				securityConfig(ruleUtilService, loginId, clientId, fileNameList, fileContentsList, signatureList);
				
				/*
				 * 폴킷 정책
				 */
				String polkitConfig = ruleUtilService.getPolicyKitRuleJson(loginId, clientId);
				if (polkitConfig != null && polkitConfig.length() != 0) {
					fileNameList.add(Constant.POLKIT_PATH);
					fileContentsList.add(polkitConfig);
					signatureList.add(signing(polkitConfig));
				}
				
				jRes.put("file_name_list", fileNameList);
				jRes.put("file_contents_list", fileContentsList);
				jRes.put("signature_list", signatureList);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
			//============================================
			//아래 공통 사용
			ClientLoginVO clVo = new ClientLoginVO();
			clVo.setClientId(clientId);
			clVo.setLoginId(loginId);
			//============================================
			
			/*
			 * 패키지 추가/삭제 활성화여부
			 */
			try {
				String operation = clientJobService.selectUpdateOperation(clVo);
				if (operation.equals("true")) {
					jRes.put("operation", "enable");
				}
				else {
					jRes.put("operation", "disable");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				jRes.put("operation", "");
			}
			
			task.put(Constant.J_RESPONSE, jRes);
		}
		catch (Exception e) {
			e.printStackTrace();
			return String.format("%s=%s", Constant.TASK_CLIENT_USER_SYNC, e.toString());
		}
		return Constant.TR_OK;
	}
}
