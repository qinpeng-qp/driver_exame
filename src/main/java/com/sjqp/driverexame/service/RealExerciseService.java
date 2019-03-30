package com.sjqp.driverexame.service;

import com.sjqp.driverexame.entity.RealExercise;
import com.sjqp.driverexame.util.ApiResult;

import java.util.List;

/**
 * @author qinpeng
 */
public interface RealExerciseService {

    ApiResult<List<RealExercise>> getRealExercise(Integer currentPageNo, Integer pageSize);

    ApiResult  saveRealExercise(List<RealExercise> realExerciseList);

    /**
     * 删除
     * @param realExerciseList
     * @return
     */
    ApiResult  deleteRealExercise(List<RealExercise> realExerciseList);


}
