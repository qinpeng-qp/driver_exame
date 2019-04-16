package com.sjqp.driverexame.service;

import com.github.pagehelper.PageInfo;
import com.sjqp.driverexame.entity.ErrorExercise;
import com.sjqp.driverexame.entity.SimulatedExercise;
import com.sjqp.driverexame.util.ApiResult;

import java.util.List;

public interface SimulatedExerciseService {

    ApiResult<List<SimulatedExercise>> getSimulatedExercise(Integer currentPageNo, Integer pageSize);

    ApiResult  saveSimulatedExercise(List<SimulatedExercise> simulatedExercises);

    /**
     * 删除
     * @param simulatedExercises
     * @return
     */
    ApiResult  deleteSimulatedExercise(List<SimulatedExercise> simulatedExercises);

    /**
     * 更新
     * @param errorExercise
     * @return
     */
    ApiResult  updateSimulatedExercise(SimulatedExercise simulatedExercise);
}
