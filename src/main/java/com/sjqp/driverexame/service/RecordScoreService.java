package com.sjqp.driverexame.service;

import com.sjqp.driverexame.entity.RecordScore;
import com.sjqp.driverexame.util.ApiResult;

import java.util.List;

/**
 * @author qinpeng
 */
public interface RecordScoreService {

    /**
     * @param recordScore
     * @return
     */
     boolean recordScore(RecordScore recordScore);

    /**
     * @param recordScore
     * @return
     */
     ApiResult<List<RecordScore>> getRecord(Integer currentPageNo, Integer pageSize,RecordScore recordScore);


     ApiResult<List<RecordScore>> deleteRecord(List<RecordScore> recordScoreList);

}
