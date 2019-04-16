package com.sjqp.driverexame.controller;

import com.alibaba.fastjson.JSONObject;
import com.sjqp.driverexame.entity.RecordScore;
import com.sjqp.driverexame.entity.UserInfo;
import com.sjqp.driverexame.inc.Const;
import com.sjqp.driverexame.service.AccountService;
import com.sjqp.driverexame.service.RecordScoreService;
import com.sjqp.driverexame.util.ApiResult;
import com.sjqp.driverexame.util.StringUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author qinpeng on 2019/04/15
 * 记录用户答题日志
 */
@Controller
@RequestMapping(value = "/record")
public class RecordScoreController {

    private Logger logger = LoggerFactory.getLogger(RecordScoreController.class);

    @Autowired
    private RecordScoreService recordScoreService;

    @RequestMapping(value = "/recordScore", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject recordScore(HttpServletRequest request, @RequestBody String data) {
        JSONObject resturnObj = new JSONObject();
        resturnObj.put("description", "");
        resturnObj.put("code", "1");
        if (Objects.nonNull(data)) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            String type = jsonObject.getString("type");
            String score = jsonObject.getString("score");
            String totalCount = jsonObject.getString("totalCount");
            logger.info("type{},score{},totalCount{}", type, score, totalCount);
            if (StringUtil.isEmpty(type) || StringUtil.isEmpty(score) || StringUtil.isEmpty(totalCount)) {
                resturnObj.put("description", "必选参数不能为空");
                resturnObj.put("code", "1");
                return resturnObj;
            } else {
                Object attribute = request.getSession().getAttribute(Const.LOGINED_KEY);
                if (Objects.nonNull(attribute) && attribute instanceof UserInfo) {
                    UserInfo userInfo = (UserInfo) attribute;
                    String username = userInfo.getUsername();
                    RecordScore recordScore = new RecordScore();
                    recordScore.setScore(score);
                    recordScore.setUsername(username);
                    recordScore.setTotalCount(totalCount);
                    recordScore.setType(type);
                    recordScore.setCreateTime(new Date());
                    boolean flag = recordScoreService.recordScore(recordScore);
                    if (flag) {
                        resturnObj.put("description", "成功");
                        resturnObj.put("code", "0");
                    }
                } else {
                    resturnObj.put("description", "未登录");
                    resturnObj.put("code", "1");
                }
            }
        }
        return resturnObj;
    }

    @RequestMapping(value = "/getRecordScore", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ApiResult<List<RecordScore>> getRecordScore(@RequestParam(value = "page", defaultValue = "1") Integer currentPageNo,
                                                       @RequestParam(value = "limit", defaultValue = "1") Integer pageSize, String username) {
        try {
            if (StringUtils.isNotEmpty(username)) {
                RecordScore recordScore = new RecordScore();
                recordScore.setUsername(username);

                return recordScoreService.getRecord(currentPageNo, pageSize, recordScore);
            }

        } catch (Exception e) {
            logger.error("getRecordScore e{}", e);
            return new ApiResult<>(ApiResult.FAIL_RESULT, "系统异常");
        }
        return null;
    }

}
