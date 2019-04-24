package com.sjqp.driverexame.controller;

import com.alibaba.fastjson.JSONObject;
import com.sjqp.driverexame.service.DataStatisticsService;
import com.sjqp.driverexame.util.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据统计
 *
 * @author qinpeng
 */
@Controller
@RequestMapping("/data")
public class DataStatisticsController {

    private Logger logger = LoggerFactory.getLogger(DataStatisticsController.class);

    @Autowired
    private DataStatisticsService dataStatisticsService;

    /**
     * 获得题目数和用户总数
     *
     * @return
     */
    @RequestMapping("/getDataCount")
    @ResponseBody
    public JSONObject getDataCount() {
        JSONObject jsonObject = new JSONObject();

        try {
            Integer userCount = dataStatisticsService.getUserCount();
            Integer questionCount = dataStatisticsService.getQuestionCount();
            jsonObject.put("userCount", userCount);
            jsonObject.put("questionCount", questionCount);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


}
