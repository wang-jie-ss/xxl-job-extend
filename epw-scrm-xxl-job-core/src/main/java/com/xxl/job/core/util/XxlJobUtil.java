package com.xxl.job.core.util;

import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.biz.client.AdminBizClient;
import com.xxl.job.core.biz.entity.XxlJobGroup;
import com.xxl.job.core.biz.entity.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.util.ObjectUtils;

@Slf4j
public class XxlJobUtil {
    private AdminBiz adminBizClient;
    private String addressUrl;
    private String author;
    private String accessToken;
    private int timeout;
    private String appName;
    private static final String URL_SEPARATION = "/";

    public XxlJobUtil(String addressUrl, String accessToken, int timeout,String appName) {
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;
        if (!this.addressUrl.endsWith(URL_SEPARATION)) {
            this.addressUrl = this.addressUrl + URL_SEPARATION;
        }
        this.timeout = timeout;
        this.appName=appName;
        this.adminBizClient = new AdminBizClient(addressUrl, accessToken);
    }
    public XxlJobUtil(String addressUrl, String accessToken, int timeout,String author,String appName) {
        this.appName=appName;
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;
        if (!this.addressUrl.endsWith(URL_SEPARATION)) {
            this.addressUrl = this.addressUrl + URL_SEPARATION;
        }
        this.author=author;
        this.timeout = timeout;
        this.adminBizClient = new AdminBizClient(addressUrl, accessToken);
    }


    /**
     * 新增任务
     *
     * @param jobDesc 任务描述
     * @param cron    cron表达式
     */
    public ReturnT<String> addJob(String jobDesc, String cron, String param, String jobHandler, String appName, String author) {
        if(StringUtils.isEmpty(appName)){
            appName=this.appName;
        }
        ReturnT<XxlJobGroup> xxlJobGroup = adminBizClient.getXxlJobGroup(appName);
        ReturnT<String> stringReturnT = new ReturnT<>();
        if (ObjectUtils.isEmpty(xxlJobGroup)) {
            stringReturnT.setCode(500);
            stringReturnT.setContent("找不到指定的执行器");
            return stringReturnT;
        }
        XxlJobInfo jobInfo = buildJobInfo(null, jobDesc, cron, param, jobHandler, xxlJobGroup.getContent(), author);
        return adminBizClient.addJob(jobInfo);
    }

    public ReturnT<String> addJob(String jobDesc, String cron, String param, String jobHandler, String appName) {
       return addJob(jobDesc,cron,param,jobHandler,appName,null);
    }
    public ReturnT<String> addJob(String jobDesc, String cron, String param, String jobHandler) {
        return addJob(jobDesc,cron,param,jobHandler,null,null);
    }

    /**
     * 更新任务
     *
     * @param jobId   任务id
     * @param jobDesc 任务描述
     * @param cron    cron表达式
     */
    public ReturnT<String> updateJob(Integer jobId, String jobDesc, String cron, String param, String jobHandler, String appName, String author) {
        ReturnT<XxlJobGroup> xxlJobGroup = adminBizClient.getXxlJobGroup(appName);
        XxlJobInfo jobInfo = buildJobInfo(jobId, jobDesc, cron, param, jobHandler, xxlJobGroup.getContent(), author);
        return adminBizClient.updateJobById(jobInfo);
    }

    /**
     * 删除任务
     */
    public ReturnT<String> removeJobById(Integer jobId) {
        return adminBizClient.removeJobById(jobId);
    }

    /**
     * 开始任务
     */
    public ReturnT<String> startJobById(Integer jobId) {
        return adminBizClient.startJobById(jobId);
    }

    /**
     * 停止任务
     *
     * @param jobId 资源id
     */
    public ReturnT<String> stopJobById(Integer jobId) {
        return adminBizClient.stopJobById(jobId);
    }

    /**
     * 获取指定任务
     */
    public ReturnT<XxlJobInfo> getJobById(Integer jobId) {
        return adminBizClient.getJobById(jobId);
    }


    /**
     * 初始化数据
     *
     * @param id
     * @param jobDesc
     * @param cron
     * @param param
     * @param jobHandler
     * @return
     */
    private XxlJobInfo buildJobInfo(Integer id, String jobDesc, String cron, String param, String jobHandler, XxlJobGroup xxlJobGroup, String author) {
        XxlJobInfo jobInfo = new XxlJobInfo();
        if (id != null) {
            jobInfo.setId(id);
        }
        jobInfo.setAppName(xxlJobGroup.getAppname());
        jobInfo.setJobDesc(jobDesc);
        jobInfo.setJobGroup(xxlJobGroup.getId());
        jobInfo.setAuthor(this.author);
        if(!StringUtils.isEmpty(author)){
            jobInfo.setAuthor(author);
        }
        jobInfo.setScheduleType("CRON");
        jobInfo.setScheduleConf(cron);
        jobInfo.setGlueRemark("GLUE代码初始化");
        jobInfo.setExecutorRouteStrategy("ROUND");
        jobInfo.setMisfireStrategy("DO_NOTHING");
        jobInfo.setGlueType("BEAN");
        jobInfo.setExecutorHandler(jobHandler);
        jobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        jobInfo.setExecutorParam(param);
        return jobInfo;
    }

}