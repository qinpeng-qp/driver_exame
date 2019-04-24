package com.sjqp.driverexame.service.impl;

import com.sjqp.driverexame.mapper.DataMapper;
import com.sjqp.driverexame.mapper.UserInfoMapper;
import com.sjqp.driverexame.service.DataStatisticsService;
import com.sjqp.driverexame.util.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qinpeng
 */
@Service
public class DataStatisticsServiceImpl implements DataStatisticsService {

    private Logger logger = LoggerFactory.getLogger(DataStatisticsServiceImpl.class);

    @Autowired
    private DataMapper dataMapper;


    @Override
    public Integer getUserCount() {
        Integer count = 0;
        try {
            count = dataMapper.getUserCount();

        } catch (Exception e) {
            logger.error("DataStatisticsServiceImpl getUserCount e{}", e);
        }
        return count;
    }

    @Override
    public Integer getQuestionCount() {
        Integer count = 0;
        try {
            count = dataMapper.getQuestionCount();

        } catch (Exception e) {
            logger.error("DataStatisticsServiceImpl getQuestionCount e{}", e);
        }
        return count;
    }
}
