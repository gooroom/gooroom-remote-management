package kr.gooroom.gpms.grm.serveragent.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.gooroom.gpms.common.utils.Token;
import kr.gooroom.gpms.common.service.AgentStatusVO;
import kr.gooroom.gpms.common.utils.Constant;
import kr.gooroom.gpms.common.utils.JobScheduler;
import kr.gooroom.gpms.common.utils.Tasker;
import kr.gooroom.gpms.grm.serveragent.service.AuthService;
import kr.gooroom.gpms.grm.serveragent.service.AuthVO;
import kr.gooroom.gpms.grm.serveragent.service.ClientAccessVO;
import kr.gooroom.gpms.grm.serveragent.service.ServerJobService;
import kr.gooroom.gpms.grm.serveragent.service.JobVO;
import kr.gooroom.gpms.grm.serveragent.service.RuleUtilService;
import kr.gooroom.gpms.grm.serveragent.service.ClientJobService;
import kr.gooroom.gpms.grm.serveragent.service.JobTargetVO;


@Controller
@RequestMapping(value ="/v1")
public class ClientJobController {
    private static final Logger logger = LoggerFactory.getLogger(ClientJobController.class);

    @Resource(name = "serverJobService")
    private ServerJobService serverJobService;

    @Resource(name = "authService")
    private AuthService authService;
    
    @Resource(name = "clientJobService")
    private ClientJobService clientJobService;
    
    @Resource(name = "ruleUtilService")
    private RuleUtilService ruleUtilService;
    
    private Token tokenFactory = new Token(
    		Constant.TOKEN_SALT,
    		Constant.TOKEN_ISSUER,
    		Constant.TOKEN_INTERVAL);

    /*
     * IP차단상태, DB접근시간을 저장
     */
    private static final int IP_HIT_SPAN = Integer.parseInt(Constant.IP_HIT_SPAN);
	private static final int IP_STATUS_OK = 1;
	private static final int IP_STATUS_BLOCK = 2;
    class IpCacheItem {
    	public Date hitDate;
    	public int status;
    	
    	public IpCacheItem() {
    		hitDate = new Date();
    		status = IP_STATUS_OK;
    	}
    }
    /*
     * IP 차단 기능을 위해 단말의 incomming IP를 키로
     * 차단상태와 DB접근시간을 저장
     * hitCache() 메서드에서 사용
     *  
     */
    HashMap<String, IpCacheItem> ipCache = new HashMap<String, IpCacheItem>();
    
    
    /*
     *client job의 task들을 처리하는 객체
     */
    private Tasker tasker = new Tasker();
    
    /**
     * 토큰을 복호화해서 토큰의 클라이언트 아이디와 데이터의 클라이언트 아이디가 일치하는 지 조사한다.
     * @param token
     * @param clientIp
     * @param clientId
     * @param model
     * @return 성공:TOKEN_SUCCESS, 실패:pageJsonView
     */
    private String inspectToken(String token, String clientIp, String clientId, ModelMap model) {
    	
		/*
		 * 토큰을 incoming ip로 복호화해서 단말을 확인하고
		 * 토큰의 클라이언트 아이디와 데이터의 클라이언트 아이디가 일치하는지 확인해서
		 * 토큰 = 클라이언트 = 데이터의 정합성을 확인한다.
		 */
		try {
			String tokenClientId = tokenFactory.parseToken(token, clientIp);
			if (!clientId.equals(tokenClientId)) {
				throw new Exception(String.format(
						"clientId({}) is not equal to token's clientId.({})", 
						clientId, 
						tokenClientId));
			}
			
		} catch (Exception e) {
			//토큰이 만료되었을 때 발생 할 ExpiredJwtException이 
			//주로 발생할 것으로 보입니다. 
			return createResult(
					Constant.MSG_FAIL, 
					Constant.RSP_NOT_AUTH, 
					e.getMessage(), 
					"",
					"",
					null, 
					model);
		}
		return Constant.TOKEN_SUCCESS;
    }
    
    /**
     * job 목록을 조회하여 클라이언트에게 전송한다.
     * @param reqBody
     * @param req
     * @param res
     * @param model
     * @return pageJsonView
     * @throws Exception
     */
    @RequestMapping(value = "/jobs", method = RequestMethod.POST)
    public String jobs(
    		@RequestBody HashMap<String,Object> reqBody,
    		HttpServletRequest req, 
    		HttpServletResponse res, 
    		ModelMap model) throws Exception {
    	
    	/*
    	 * clientIp는 web server로 부터 header를 통해 전송받고
    	 * token은 클라이언트의 header에서,
    	 * 나머지는 클라이언트의 body(json)에서 받는다.
    	 */
		String clientIp = req.getHeader(Constant.H_REALIP);
		String token = req.getHeader(Constant.H_TOKEN);
		String clientId = (String)reqBody.get(Constant.JOBS_CLIENT_ID);
		String userId = (String)reqBody.get(Constant.JOBS_USER_ID);
		Double maxRspTime = (Double)reqBody.get(Constant.JOBS_MAX_RSP_TIME);
		Integer timeoutCnt = (Integer)reqBody.get(Constant.JOBS_TIMEOUT_CNT);
		Integer pollingTime = -3137;
		
		int columnCnt = serverJobService.selectPollingTimeColumnCntInClientAccess();
		
		if (reqBody.containsKey("polling_time") && columnCnt > 0) {
			pollingTime = (Integer)reqBody.get(Constant.JOBS_POLLING_TIME);
		}
		
    	/*
    	 * IP가 화이트리스트에 존재하는 지 확인.
    	 */
    	if (hitCache(clientIp, clientId) != IP_STATUS_OK) {
			return createResult(
					Constant.MSG_FAIL, 
					String.format("Your incoming-ip(%s) is blocked", clientIp), 
					"", 
					"", 
					"",
					null, 
					model);
    	}
    	
		/*
		 * 토큰 조사
		 */
		String tokenResult = inspectToken(token, clientIp, clientId, model);
		if (!tokenResult.equals(Constant.TOKEN_SUCCESS)) {
			return tokenResult;
		}
		
		/*
		 * 클라이언트의 사용자가 온라인/로컬/비접속 인지를 확인해서
		 * 클라이언트 접속 정보를 업데이트한다.
		 * 클라이언트의 ON/OFF 상태를 서버와 클라이언트가 동기화하기 위해서
		 * 클라이언트의 이전 접속 시간을 조회해서 클라이언트에게 전달한다.
		 */
		ClientAccessVO cav = new ClientAccessVO();
		cav.setClientId(clientId);
		cav.setUserId(userId);
		cav.setMaxRspTime(maxRspTime);
		cav.setTimeoutCnt(timeoutCnt);
		cav.setPollingTime(pollingTime);
		
		String prevAccessDiffTime = 
				serverJobService.selectNotLoginAccessDiffTime(clientId);
		if (userId.equals("-")) { //not logged in
			if (serverJobService.insertOrUpdateNotLogginedClientConnStatus(cav) <= 0) {
				logger.error(
						"(NOT LOGGINED) fail to update client connection info(clientId={} userId={})", 
						clientId, 
						userId);
			}
		}
		else if (userId.charAt(0) == '+') { //local user
			if (serverJobService.insertOrUpdateLocalClientConnStatus(cav) <= 0) {
				logger.error(
						"(LOCAL) fail to update client connection info(clientId={} userId={})", 
						clientId, 
						userId);
			}
		}
		else { //online user
			if (serverJobService.insertOrUpdateOnlineClientConnStatus(cav) <= 0) {
				logger.error(
						"(ONLINE) fail to update client connection info(clientId={} userId={})", 
						clientId, 
						userId);
			}
		}
		
		/*
		 * 클라이언트의 JOB을 조회 후 JOB상태를 ready에서 doing으로 업데이트.
		 */
		List<JobVO> resultList = 
				(List<JobVO>) serverJobService.selectListForClientReady(clientId);
		if (resultList.size() != 0) {
			for (JobVO vo: resultList) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("clientId", clientId);
				param.put("jobNo", vo.getJobNo());
				serverJobService.updateReadyToDoing(param);
				logger.debug("SERVERJOB client_id={} job_no={}", clientId, vo.getJobNo());
			}
		}
		
		return createResult(
				Constant.MSG_SUCCESS, 
				Constant.RSP_OK, 
				"", 
				prevAccessDiffTime,
				"", //vi.visaStatus,
				resultList, model);
    }
    
    /**
     * 클라이언트가 전송한 JOB 결과를 저장한다.
     * 클라이언트는 복수개의 JOB을 수신하더라도 그 결과는 JOB 별로 송신한다.
     * @param reqBody
     * @param req
     * @param res
     * @param model
     * @return pageJsonView
     * @throws Exception
     */   
    @RequestMapping(value = "/job/server", method = RequestMethod.POST)
    public String jobResult(
    		@RequestBody HashMap<String,Object> reqBody, 
    		HttpServletRequest req, 
    		HttpServletResponse res, 
    		ModelMap model) throws Exception {
    	
    	Date beforeTime = new Date();
    	
    	/*
    	 * clientIp는 web server로 부터 header를 통해 전송받고
    	 * token은 클라이언트의 header에서,
    	 * 나머지는 클라이언트의 body(json)에서 받는다.
    	 */
		String clientIp = req.getHeader(Constant.H_REALIP);
		String token = req.getHeader(Constant.H_TOKEN);
		List<?> agentBodyList = (List<?>)reqBody.get(Constant.AGENT_DATA);
		HashMap<?,?> agentBody = (HashMap<?,?>)agentBodyList.get(0);
		String clientId = (String)agentBody.get(Constant.AGENT_DATA_CLIENTID);
		
		/*
		 * 토큰 조사
		 */
		String tokenResult = inspectToken(token, clientIp, clientId, model);
		if (!tokenResult.equals(Constant.TOKEN_SUCCESS)) {
			return tokenResult;
		}
		
		/*
		 * 클라이언트가 전송한 JOB 처리 결과를 job_trgt 테이블에 저장한다.
		 * 이전에는 job_result 데이블에 저장했지만 job_result가 job_trgt에 통합되었다. 
		 */
		JobTargetVO jobTargetVo = new JobTargetVO();
		jobTargetVo.setClientId(clientId);
		jobTargetVo.setJobNo((Integer)agentBody.get(Constant.AGENT_DATA_JOBNO));
		jobTargetVo.setResultData((String)agentBody.get(Constant.AGENT_DATA_JOBDATA));
		logger.info(
				"SERVERJOB clientId={} jobNo={}", 
				clientId, jobTargetVo.getJobNo());
		HashMap<?,?> agentStaus = (HashMap<?,?>)reqBody.get(Constant.AGENT_STATUS);
		String resultCode = (String)agentStaus.get("resultCode");
		//JOB결과가 성공인지 실패인지 확인
		if (resultCode.equals("200")) {
			jobTargetVo.setJobStat(Constant.JOB_STAT_SUCCESS);
		}
		else {
			jobTargetVo.setJobStat(Constant.JOB_STAT_FAIL);
		}
		serverJobService.insertOrUpdateJobResult(jobTargetVo);

		Date afterTime = new Date();
		logger.debug("server time={} client_id={}", afterTime.getTime() - beforeTime.getTime(), clientId);
		return createResult(
				Constant.MSG_SUCCESS, 
				Constant.RSP_OK, 
				"", 
				"", 
				"",
				null, 
				model);
    }
    
    /**
     * 클라이언트가 전송한 JOB을 처리하고 그 결과를 클라이언트에게 전송한다.
     * @param reqBody
     * @param req
     * @param res
     * @param model
     * @return pageJsonView
     * @throws Exception
     */   
    @RequestMapping(value = "/job/client", method = RequestMethod.POST)
    public String clientJob(
    		@RequestBody HashMap<String,Object> reqBody, 
    		HttpServletRequest req, 
    		HttpServletResponse res, 
    		ModelMap model) throws Exception {
    	
    	/*
    	 * clientIp는 web server로 부터 header를 통해 전송받고
    	 * token은 클라이언트의 header에서,
    	 * 나머지는 클라이언트의 body(json)에서 받는다.
    	 */
		String clientIp = req.getHeader(Constant.H_REALIP);
		String token = req.getHeader(Constant.H_TOKEN);
		List<HashMap<String,Object>> reqAgentDataList = 
				(List<HashMap<String,Object>>)reqBody.get(Constant.AGENT_DATA);
		HashMap<String,Object> agentData = (HashMap<String,Object>)reqAgentDataList.get(0);
		String clientId = (String)agentData.get(Constant.AGENT_DATA_CLIENTID);

    	/*
    	 * IP가 화이트리스트에 존재하는 지 확인.
    	 */
    	if (hitCache(clientIp, clientId) != IP_STATUS_OK) {
			return createResult(
					Constant.MSG_FAIL, 
					String.format("Your incoming-ip(%s) is blocked", clientIp), 
					"", 
					"", 
					"",
					null, 
					model);
    	}
    	
		/*
		 * 토큰 조사
		 */
		String tokenResult = inspectToken(token, clientIp, clientId, model);
		if (!tokenResult.equals(Constant.TOKEN_SUCCESS)) {
			return tokenResult;
		}
		
		int jobNo = (Integer)agentData.get(Constant.AGENT_DATA_JOBNO);
		String jobString = (String)agentData.get(Constant.AGENT_DATA_JOBDATA);
		
		/*
		 * 클라이언트가 전송한 job_data는 dump된 json 스트링이다.
		 * json 스트링을 hashmap으로 변환하는 과정이 필요하다.
		 */
		ObjectMapper mapper = new ObjectMapper();
		List<?> jobList = mapper.readValue(jobString, List.class);
		HashMap<String,Object> job = 
				(HashMap<String,Object>)jobList.get(0);
		HashMap<String,Object> module = 
				(HashMap<String,Object>)job.get(Constant.J_MOD);
		String moduleName = (String)module.get(Constant.J_MODN);
		HashMap<String,Object> task = 
				(HashMap<String,Object>)module.get(Constant.J_TASK);
		String taskName = (String)task.get(Constant.J_TASKN);
		logger.info(
				"CLIENTJOB clientId={} moduleName={} taskName={}", 
				clientId, moduleName, taskName);
		
		/*
		 * 클라이언트는 서버에게 하나의 task만 포함된 JOB을 전송한다.
		 * 복수개의 task는 지원하지 않는다.
		 * 클라이언트가 전송한 task를 처리하는 객체는 tasker이다.
		 */
		String taskerResult = tasker.doTask(
				clientJobService, 
				ruleUtilService, 
				clientId, 
				clientIp, 
				task);
		//클라이언트가 전송한 요청데이터는 불필요하기에 삭제하고 결과를 전송 
		if (task.containsKey(Constant.J_REQUEST)) {
			task.remove(Constant.J_REQUEST);
		}
		if (task.containsKey(Constant.J_IN)) {
			task.remove(Constant.J_IN);
		}
		
		/*
		 * JOB 결과를 클라이언트에게 전송한다.
		 */
		//JOB처리는 성공했고 결과데이터가 있을 경우 
		if (taskerResult.equals(Constant.TR_OK)) {
			List<JobVO> agentDataList = new ArrayList<JobVO>();
			JobVO rspJob = new JobVO();
			rspJob.setClientId(clientId);
			rspJob.setJobNo(-1);
			rspJob.setJob(new ObjectMapper().writeValueAsString(job));
			agentDataList.add(rspJob);
			
			return createResult(
					Constant.MSG_SUCCESS, 
					Constant.RSP_OK, 
					"", 
					"", 
					"",
					agentDataList, 
					model);
		}
		 //JOB처리는 성공했고 결과데이터가 없을 경우
		else if (taskerResult.equals(Constant.TR_NO_AGENT_DATA)) {
			return createResult(
					Constant.MSG_SUCCESS, 
					Constant.RSP_OK, 
					"", 
					"", 
					"",
					null, 
					model);
		}
		//JOB처리에 실패했을 경우
		else {
			return createResult(
					Constant.MSG_FAIL, 
					Constant.RSP_INTERNAL_ERR, 
					taskerResult, 
					"", 
					"",
					null, 
					model);

		}
    }
    
    /**
     * 단말을 인증하고 토큰을 발행한다.
     * @param reqBody
     * @param req
     * @param res
     * @param model
     * @return pageJsonView
     * @throws Exception
     */   
	@RequestMapping(value = "/auth/token", method = RequestMethod.POST)
	public String auth(
			@RequestBody HashMap<String,Object> reqBody
			,HttpServletRequest req
			,HttpServletResponse res
			,ModelMap model) throws Exception {

    	/*
    	 * clientCert와 clientIp는 web server로 부터 header를 통해 전송받고
    	 * 나머지는 클라이언트의 body(json)에서 받는다.
    	 */
		String clientCert = req.getHeader(Constant.H_CERT);
		String clientIp = req.getHeader(Constant.H_REALIP);
		HashMap<?,?> auth = (HashMap<?,?>)reqBody.get(Constant.H_AUTH);
		String clientId = (String)auth.get(Constant.H_CID);
		clientCert = clientCert.replaceAll("[ \n\t\r]", "");
		
		//logger.info("auth ip={}", clientIp);
		
		/*
		 * 클라이언트의 아이디와 인증서가 DB에 저장되어 있는 정보와 일치하는 지 확인을 한다.
		 */
		List<AuthVO> trmutList = (List<AuthVO>) authService.selectTrmut(clientId);
		
		//client_mstr에 클라이언트아이디에 해당하는 정보가 유일해야하고(등록이 정상)
		//클라이언트의 상태가 정상(STAT010)이어야하며
		//인증서가 일치해야 된다.
		if (trmutList.size() == 1
				&& trmutList.get(0).getTrmutStat().equals("STAT010") 
				&& trmutList.get(0).getKeyInfo().replaceAll("[ \n\t\r]", "").equals(clientCert)) {
			
			//인증이 성공했으면 토큰을 생성
			String token = tokenFactory.genToken(clientIp, clientId);
			logger.info("AUTH clientId={} clientIp={} token={}", clientId, clientIp, token);
			
			//토큰을 클라이언트에게 전송
			res.setHeader("Content-Type", "application/json; charset=UTF-8");
			res.setHeader(Constant.H_TOKEN, token);
			
			return createResult(
					Constant.MSG_SUCCESS, 
					Constant.RSP_OK,
					"", 
					"", 
					"",
					null, 
					model);
		}
		else {
			//인증이 실패하면
			String trmUtStat = null;
			String errReason = null;
			
			int size = trmutList.size();
			//클라이언트 등록 정상
			if (size == 1) {
				trmUtStat = trmutList.get(0).getTrmutStat();
				
				//클라이언트 상태가 비정상
				if (!trmUtStat.equals("STAT010")) {
					errReason = String.format(
							"AUTH ERROR clientId=%s clientIp=%s clientStat=%s",
									clientId,  
									clientIp, 
									trmUtStat);
				}
				//인증서가 일치하지 않음
				else {
					errReason = String.format(
							"AUTH ERROR clientId=%s clientIp=%s certificate unmatch",
							clientId,
							clientIp);
				}
			}
			//클라이언트 등록 비정상
			else {
				errReason = String.format(
						"AUTH ERROR clientId=%s clientIp=%s client row number in client_mstr=%s",
						clientId,
						clientIp,
						Integer.toString(size));
			}
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			logger.error(errReason);
			
			return createResult(
					Constant.MSG_FAIL, 
					Constant.RSP_NOT_AUTH, 
					errReason, 
					"", 
					"",
					null, 
					model);
		}
	}

    /**
     * GRM의 상태를 반환한다.(임시)
     * @param req
     * @param res
     * @param model
     * @return pageJsonView
     * @throws Exception
     */   
	@RequestMapping(value = "/server", method = RequestMethod.GET)
	public String server(
			HttpServletRequest req
			,HttpServletResponse res
			,ModelMap model) throws Exception {
		String fromIp = req.getHeader(Constant.H_REALIP);
		logger.info("SERVER STATUS REQUEST from {}", fromIp);
		return "pageJsonView";
	}
	
	/**
	 * GRM의 버전정보를 반환한다.
	 * @param reqBody
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/version", method = RequestMethod.POST)
	public String version(
    		@RequestBody HashMap<String,Object> reqBody,
			HttpServletRequest req
			,HttpServletResponse res
			,ModelMap model) throws Exception {

		String clientId = (String)reqBody.get(Constant.AGENT_DATA_CLIENTID);
		String serverVersion = "9.9"; //clientJobService.selectServerVersion(Constant.SITE_NAME);

		//버전정보를 클라이언트에게 전송
		res.setHeader("Content-Type", "application/json; charset=UTF-8");
		res.setHeader(Constant.H_VERSION, serverVersion);
		
		logger.info("VERSION clientId={} version={}", clientId, serverVersion);
		
		return createResult(
				Constant.MSG_SUCCESS,
				Constant.RSP_OK,
				"", 
				"", 
				"",
				null, 
				model);
	}
	
	/**
	 * 클라이언트에게 전송할 최종 응답을 생성하고 뷰로 리턴
	 * @param rspStatus
	 * @param rspCode
	 * @param message
	 * @param prevPollingTime
	 * @param resultList
	 * @param model
	 * @return
	 */
    private String createResult(
    		String rspStatus, 
    		String rspCode, 
    		String message, 
    		String prevPollingTime, 
    		String visaStatus,
    		List<JobVO> resultList, 
    		ModelMap model)
    {
    	/*
    	 * 상태 설정
    	 */
    	AgentStatusVO agentStatusVo = new AgentStatusVO();
    	agentStatusVo.setResultInfo(rspStatus, rspCode, message);
    	agentStatusVo.setPrevAccessDiffTime(prevPollingTime);
    	agentStatusVo.setVisaStatus(visaStatus);
		model.addAttribute(Constant.AGENT_STATUS, agentStatusVo);
		/*
		 * 데이터 설정
		 */
		if (resultList != null) {
			model.addAttribute(Constant.AGENT_DATA, resultList);
		}
		return "pageJsonView";
    }
    
    /**
     * 차단된 IP인지 확인
     * @param ip
     * @return
     */
    private int hitCache(String ip, String clientId) {
    	IpCacheItem item = null;
    	boolean isNewIp = false;
    	
    	/*
    	 * IP가 key이고 차단상태와 DB를 확인한 시간이 기록된 IpCacheItem이 value인
    	 * ipCache 맵을 생성해서 IP차단 기능을 구현
    	 */
    	if (ipCache.containsKey(ip)) {
    		item = ipCache.get(ip);
    		isNewIp = false;
    	}
    	else {
    		item = new IpCacheItem();
    		ipCache.put(ip, item);
    		item.hitDate = new Date();
    		item.status = IP_STATUS_BLOCK; //디폴트 상태를 차단으로 한다.
    		isNewIp = true;
    	}
    	
    	Date nowDate = new Date();
    	long nowHitDiff = (nowDate.getTime() - item.hitDate.getTime()) / 1000;
    	
    	/*
    	 * 처음 요청이 들어온 IP이거나 DB를 확인한 시간이 HIT_SPAN이 지난 요청을 처리
    	 */
    	if (isNewIp || nowHitDiff > IP_HIT_SPAN) {
			item.hitDate = new Date();
			
			try {
				List<String> whiteList = clientJobService.selectIpWhiteList(clientId);
				for (String wl : whiteList) {
					try {
						/*
						 * DB 쿼리 결과 형식이
						 * allow|disallow
						 * whitelist ip0
						 * whitelist ip1
						 * ......
						 * 첫번 째 row가 전체 IP의 허용|비허용을 표현한다.
						 * 따라서 allow이면 이 후 row들은 무시한다.
						 */
						if (wl.equalsIgnoreCase("allow")) {
							item.status = IP_STATUS_OK;
							logger.info("SARABAL {} ALL IP CHECK", clientId);
							break;
						}
						else if (wl.equalsIgnoreCase("disallow")) {
							item.status = IP_STATUS_BLOCK;
							logger.info("SARABAL {} ALL IP NOT CHECK", clientId);
						}
						/*
						 * 172.0.0.1-172.0.0.255와 같이 ranged ip를 처리
						 */
						else if (wl.contains("-")) {
							String [] wls = wl.split("-");
							
							String[] fromIpSplited = wls[0].split("\\.");
							String[] toIpSplited = wls[1].split("\\.");
							String[] ipSplited = ip.split("\\.");
							
							long fromIpL =
									Integer.parseInt(fromIpSplited[0]) * 1000000000L +
									Integer.parseInt(fromIpSplited[1]) * 1000000L +
									Integer.parseInt(fromIpSplited[2]) * 1000L +
									Integer.parseInt(fromIpSplited[3]);
							long toIpL = Integer.parseInt(toIpSplited[0]) * 1000000000L +
									Integer.parseInt(toIpSplited[1]) * 1000000L +
									Integer.parseInt(toIpSplited[2]) * 1000L +
									Integer.parseInt(toIpSplited[3]);
							long ipL = Integer.parseInt(ipSplited[0]) * 1000000000L +
									Integer.parseInt(ipSplited[1]) * 1000000L +
									Integer.parseInt(ipSplited[2]) * 1000L +
									Integer.parseInt(ipSplited[3]);
							
							if (ipL >= fromIpL && ipL <= toIpL) {
								item.status = IP_STATUS_OK;
								logger.info("SARABAL {} {} <= IP({}) <= {} ALLOWED", clientId, fromIpL, ipL, toIpL);
								break;
							}
							else {
								item.status = IP_STATUS_BLOCK;
							}
						}
						/*
						 * 단일 ip를 처리
						 */
						else {
							if (ip.equals(wl)) {
								item.status = IP_STATUS_OK;
								logger.info("SARABAL {} IP({}) ALLOWED", clientId, ip);
								break;
							}
							else {
								item.status = IP_STATUS_BLOCK;
							}
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return item.status;
    }
}
