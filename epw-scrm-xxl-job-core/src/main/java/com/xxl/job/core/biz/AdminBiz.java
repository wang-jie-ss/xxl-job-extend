package com.xxl.job.core.biz;

import com.xxl.job.core.biz.entity.XxlJobGroup;
import com.xxl.job.core.biz.entity.XxlJobInfo;
import com.xxl.job.core.biz.model.HandleCallbackParam;
import com.xxl.job.core.biz.model.RegistryParam;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:52:49
 */
@Service
public interface AdminBiz {


    // ---------------------- callback ----------------------

    /**
     * callback
     *
     * @param callbackParamList
     * @return
     */
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList);


    // ---------------------- registry ----------------------

    /**
     * registry
     *
     * @param registryParam
     * @return
     */
    public ReturnT<String> registry(RegistryParam registryParam);

    /**
     * registry remove
     *
     * @param registryParam
     * @return
     */
    public ReturnT<String> registryRemove(RegistryParam registryParam);

    ReturnT<String> addJob(XxlJobInfo xxlJobInfo);

    ReturnT<String> removeJobById(Integer id);

    ReturnT<String> updateJobById(XxlJobInfo xxlJobInfo);

    ReturnT<String> stopJobById(Integer id);

    ReturnT<String> startJobById(Integer id);

    ReturnT<XxlJobInfo> getJobById(Integer id);

    ReturnT<String> addXxlJobGroup(XxlJobGroup xxlJobGroup);
    ReturnT<XxlJobGroup> getXxlJobGroup(String appName);
    // ---------------------- biz (custome) ----------------------
    // group、job ... manage

}
