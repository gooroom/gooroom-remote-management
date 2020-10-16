package kr.gooroom.gpms.common.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.gooroom.gpms.common.service.StatusVO;
import kr.gooroom.gpms.grm.serveragent.nodes.JobNode;
import kr.gooroom.gpms.grm.serveragent.service.ClientJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.gooroom.gpms.grm.serveragent.service.JobVO;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.HashMap;

import java.sql.SQLException;


@Component
public class JobMaker {

    private static final Logger logger = LoggerFactory.getLogger(JobMaker.class);

    @Resource(name = "clientJobService")
    private ClientJobService clientJobService;

    /**
     * create job
     *
     * @param clientJobService   ClientJobService.
     * @param jobName   string job name.
     * @param map       configuration item hierarchy data.
     * @param clientId string client id
     * @return void
     * @throws Exception
     */
    public static void createJobForClientSetupWithClients(ClientJobService clientJobService, String jobName, HashMap<String, String> map, String clientId)
            throws Exception {

        try {
            // create job
            JobNode[] jobs = new JobNode[1];
            if (map == null) {
                map = new HashMap<String, String>();
            }
            jobs[0] = JobNode.generateJobWithMap("config", jobName, map);

            String jsonStr = "";
            StringWriter outputWriter = new StringWriter();
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setVisibility(PropertyAccessor.ALL.FIELD, JsonAutoDetect.Visibility.ANY);
                mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

                mapper.writeValue(outputWriter, jobs);
                jsonStr = outputWriter.toString();
            } catch (Exception jsonex) {
                logger.error("CustomJobMaker.createJobForClientSetupWithClients (make json) Exception occurred. ",
                        jsonex);
            } finally {
                try {
                    if (outputWriter != null) {
                        outputWriter.close();
                    }
                } catch (Exception finalex) {
                    finalex.printStackTrace();
                }
            }
            JobVO jobVO = new JobVO();
            jobVO.setJobData(jsonStr);
            jobVO.setJobName(jobName);
            jobVO.setRegUserId(Constant.H_SYSTEM);

            // assign target clients
            jobVO.setClientId(clientId);

            createJob(clientJobService, jobVO);
        } catch (Exception ex) {
            logger.error("error in createJobForClientSetupWithClients : {}, {}, {}", "ERROR",
                    MessageSourceHelper.getMessage("ERROR"), ex.toString());
        }

    }

    public static StatusVO createJob(ClientJobService clientJobService, JobVO jobVO) throws Exception {
        StatusVO statusVO = new StatusVO();

        try {
            long reCnt1 = clientJobService.createJobMaster(jobVO);
            if (reCnt1 > 0) {
                long reCnt2 = 0;
                String client = jobVO.getClientId();
                    jobVO.setClientId(client);
                    reCnt2 = clientJobService.createJobTarget(jobVO);
            }
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            logger.error("error in createJob : {}, {}, {}", "ERROR",
                    MessageSourceHelper.getMessage("ERROR"), ex.toString());
        }
        return statusVO;
    }
}
