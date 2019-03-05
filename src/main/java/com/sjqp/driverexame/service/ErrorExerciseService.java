package com.sjqp.driverexame.service;

import com.sjqp.driverexame.entity.ErrorExercise;
import com.sjqp.driverexame.util.ApiResult;

import java.util.List;

public interface ErrorExerciseService {

    ApiResult<List<ErrorExercise>> getErrorExercise(Integer currentPageNo, Integer pageSize);

}
