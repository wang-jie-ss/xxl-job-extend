package com.xxl.job.core.biz.model;

import lombok.Data;

/**
 * @author wangjie
 * @version 1.0
 * @description TODO
 * @date 2023/7/12 14:56
 */
@Data
public class PageListParam {

    private Integer start;
    private Integer length;
    private Integer jobGroup;

    /**
     * 状态:全部 -1 停止 0 启动 1
     */
    private Integer triggerStatus;
    private String jobDesc;
    private String executorHandler;
    private String author;
}
