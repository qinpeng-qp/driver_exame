package com.sjqp.driverexame.service;

import com.sjqp.driverexame.util.ApiResult;

/**
 * @author qinpeng
 * 数据统计
 */
public interface DataStatisticsService {

    /**
     * 获得所有用户数
     * @return
     */
    Integer getUserCount();

    /**
     * 获得所有题目数
     * @return
     */
    Integer getQuestionCount();


}
