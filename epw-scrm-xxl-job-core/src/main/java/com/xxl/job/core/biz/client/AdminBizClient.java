package com.xxl.job.core.biz.client;

import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.biz.entity.XxlJobGroup;
import com.xxl.job.core.biz.entity.XxlJobInfo;
import com.xxl.job.core.biz.model.HandleCallbackParam;
import com.xxl.job.core.biz.model.RegistryParam;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.util.XxlJobRemotingUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * admin api test
 *
 * @author xuxueli 2017-07-28 22:14:52
 */
@Service("AdminBizClient")
public class AdminBizClient implements AdminBiz {

    public AdminBizClient() {
    }
    public AdminBizClient(String addressUrl, String accessToken) {
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;

        // valid
        if (!this.addressUrl.endsWith("/")) {
            this.addressUrl = this.addressUrl + "/";
        }
    }

    private String addressUrl ;
    private String accessToken;
    private int timeout = 3;


    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return XxlJobRemotingUtil.postBody(addressUrl+"api/callback", accessToken, timeout, callbackParamList, String.class);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/registry", accessToken, timeout, registryParam, String.class);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/registryRemove", accessToken, timeout, registryParam, String.class);
    }

    @Override
    public ReturnT<String> addJob(XxlJobInfo xxlJobInfo) {
        return XxlJobRemotingUtil.postBody(addressUrl + "api/addJob", accessToken, timeout, xxlJobInfo, String.class);
    }

    @Override
    public ReturnT<String> removeJobById(Integer id) {
        return XxlJobRemotingUtil.postBody(addressUrl+"api/removeJobById",accessToken,timeout,id,String.class);
    }

    @Override
    public ReturnT<String> updateJobById(XxlJobInfo xxlJobInfo) {
        return XxlJobRemotingUtil.postBody(addressUrl+"api/updateJobById",accessToken,timeout,xxlJobInfo,String.class);
    }

    @Override
    public ReturnT<String> stopJobById(Integer id) {
        return XxlJobRemotingUtil.postBody(addressUrl+"api/stopJobById",accessToken,timeout,id,String.class);
    }

    @Override
    public ReturnT<String> startJobById(Integer id) {
        return XxlJobRemotingUtil.postBody(addressUrl+"api/startJobById",accessToken,timeout,id,String.class);
    }

    @Override
    public ReturnT<XxlJobInfo> getJobById(Integer id) {
        return XxlJobRemotingUtil.postBody(addressUrl+"api/getJobById",accessToken,timeout,id,XxlJobInfo.class);
    }

    @Override
    public ReturnT<String> addXxlJobGroup(XxlJobGroup xxlJobGroup) {
        return XxlJobRemotingUtil.postBody(addressUrl+"api/addXxlJobGroup",accessToken,timeout,xxlJobGroup,String.class);
    }

    @Override
    public ReturnT<XxlJobGroup> getXxlJobGroup(String appName) {
        return XxlJobRemotingUtil.postBody(addressUrl+"api/getXxlJobGroup",accessToken,timeout,appName,XxlJobGroup.class);
    }


}
