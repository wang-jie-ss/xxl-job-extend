package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.core.thread.JobCompleteHelper;
import com.xxl.job.admin.core.thread.JobRegistryHelper;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.dao.XxlJobGroupDao;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.biz.entity.XxlJobGroup;
import com.xxl.job.core.biz.entity.XxlJobInfo;
import com.xxl.job.core.biz.model.HandleCallbackParam;
import com.xxl.job.core.biz.model.RegistryParam;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author xuxueli 2017-07-27 21:54:20
 */
@Service("AdminBizImpl")
public class AdminBizImpl implements AdminBiz {

    @Resource
    private XxlJobService xxlJobService;

    @Resource
    private XxlJobGroupDao xxlJobGroupDao;

    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return JobCompleteHelper.getInstance().callback(callbackParamList);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return JobRegistryHelper.getInstance().registry(registryParam);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return JobRegistryHelper.getInstance().registryRemove(registryParam);
    }

    @Override
    public ReturnT<String> addJob(XxlJobInfo xxlJobInfo) {
        return xxlJobService.add(xxlJobInfo);
    }

    @Override
    public ReturnT<String> removeJobById(Integer id) {
        return xxlJobService.remove(id);
    }

    @Override
    public ReturnT<String> updateJobById(XxlJobInfo xxlJobInfo) {
        return xxlJobService.update(xxlJobInfo);
    }

    @Override
    public ReturnT<String> stopJobById(Integer id) {
        return xxlJobService.stop(id);
    }

    @Override
    public ReturnT<String> startJobById(Integer id) {
        return xxlJobService.start(id);
    }

    @Override
    public ReturnT getJobById(Integer id) {
        return xxlJobService.getJobById(id);
    }

    @Override
    public ReturnT<String> addXxlJobGroup(XxlJobGroup xxlJobGroup) {
        // valid
        if (xxlJobGroup.getAppname()==null || xxlJobGroup.getAppname().trim().length()==0) {
            return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"AppName") );
        }
        if (xxlJobGroup.getAppname().length()<4 || xxlJobGroup.getAppname().length()>64) {
            return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appname_length") );
        }
        if (xxlJobGroup.getAppname().contains(">") || xxlJobGroup.getAppname().contains("<")) {
            return new ReturnT<String>(500, "AppName"+I18nUtil.getString("system_unvalid") );
        }
        if (xxlJobGroup.getTitle()==null || xxlJobGroup.getTitle().trim().length()==0) {
            return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")) );
        }
        if (xxlJobGroup.getTitle().contains(">") || xxlJobGroup.getTitle().contains("<")) {
            return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_title")+I18nUtil.getString("system_unvalid") );
        }
        if (xxlJobGroup.getAddressType()!=0) {
            if (xxlJobGroup.getAddressList()==null || xxlJobGroup.getAddressList().trim().length()==0) {
                return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit") );
            }
            if (xxlJobGroup.getAddressList().contains(">") || xxlJobGroup.getAddressList().contains("<")) {
                return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList")+I18nUtil.getString("system_unvalid") );
            }

            String[] addresss = xxlJobGroup.getAddressList().split(",");
            for (String item: addresss) {
                if (item==null || item.trim().length()==0) {
                    return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid") );
                }
            }
        }

        // process
        xxlJobGroup.setUpdateTime(new Date());

        int ret = xxlJobGroupDao.save(xxlJobGroup);
        return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
    }

    @Override
    public ReturnT<XxlJobGroup> getXxlJobGroup(String appName) {
        XxlJobGroup xxlJobGroup= xxlJobGroupDao.getXxlJobGroup(appName);
        return new ReturnT<>(xxlJobGroup);
    }

}
