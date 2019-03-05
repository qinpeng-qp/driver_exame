package com.sjqp.driverexame.service;

import com.github.pagehelper.PageInfo;
import com.sjqp.driverexame.entity.SimulatedExercise;
import com.sjqp.driverexame.util.ApiResult;

import java.util.List;

public interface SimulatedExerciseService {

    ApiResult<List<SimulatedExercise>> getSimulatedExercise(Integer currentPageNo, Integer pageSize);
}
