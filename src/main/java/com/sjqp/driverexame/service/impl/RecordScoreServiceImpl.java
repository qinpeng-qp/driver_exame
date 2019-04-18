package com.sjqp.driverexame.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjqp.driverexame.entity.RecordScore;
import com.sjqp.driverexame.mapper.RecordScoreMapper;
import com.sjqp.driverexame.service.RecordScoreService;
import com.sjqp.driverexame.util.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author qinpeng
 */
@Service
public class RecordScoreServiceImpl implements RecordScoreService {

    @Autowired
    private RecordScoreMapper recordScoreMapper;

    private Logger logger = LoggerFactory.getLogger(RecordScoreServiceImpl.class);

    @Override
    public boolean recordScore(RecordScore recordScore) {
        boolean flag = false;
        try {
            int row = recordScoreMapper.insert(recordScore);
            if (row > 0){
                flag = true;
            }
        } catch (Exception e) {
           logger.error("recordScore e{}",e);
        }
        return flag;
    }

    @Override
    public ApiResult<List<RecordScore>> getRecord(Integer currentPageNo, Integer pageSize,RecordScore recordScore) {

        try {
            if (Objects.nonNull(currentPageNo) && Objects.nonNull(pageSize) && Objects.nonNull(recordScore)) {
                /**插件分页 */
                PageHelper.startPage(currentPageNo, pageSize);
                Condition condition = new Condition(RecordScore.class);
                Example.Criteria conditionCriteria = condition.createCriteria();
                if (Objects.nonNull(recordScore.getUsername())) {
                    conditionCriteria.andEqualTo("username", recordScore.getUsername());
                }
                condition.setOrderByClause("create_time desc");
                List<RecordScore> recordScoreList = recordScoreMapper.selectByCondition(condition);
                PageInfo<RecordScore> pageInfo = new PageInfo<>(recordScoreList);
                ApiResult<List<RecordScore>> apiResult = new ApiResult<>(ApiResult.SUCCESS_RESULT);
                apiResult.setData(pageInfo.getList());
                apiResult.setPage(pageInfo.getPageNum());
                apiResult.setCount((int) pageInfo.getTotal());
                apiResult.setLimit(pageInfo.getPageSize());
                return apiResult;
            }
            return new ApiResult(ApiResult.FAIL_RESULT);
        } catch (Exception e) {
            logger.error("RecordScoreServiceImpl e:{}", e);
        }
        return new ApiResult(ApiResult.FAIL_RESULT, "系统异常");
    }

    /**
     * 批量删除
     * @param recordScoreList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<List<RecordScore>> deleteRecord(List<RecordScore> recordScoreList) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            if (!CollectionUtils.isEmpty(recordScoreList)) {
                for (RecordScore recordScore : recordScoreList) {
                    recordScoreMapper.deleteByPrimaryKey(recordScore);
                }
            }
            apiResult.setData(ApiResult.SUCCESS_RESULT);
            apiResult.setMsg("删除成功");
        } catch (Exception e) {
            logger.error("savRealExercise e{}", e);
            throw e;
        }
        return apiResult;
    }

}
