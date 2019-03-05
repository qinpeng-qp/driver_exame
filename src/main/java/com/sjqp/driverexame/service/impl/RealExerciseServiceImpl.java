package com.sjqp.driverexame.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjqp.driverexame.entity.RealExercise;
import com.sjqp.driverexame.entity.SimulatedExercise;
import com.sjqp.driverexame.mapper.RealExerciseMapper;
import com.sjqp.driverexame.mapper.SimulatedExerciseMapper;
import com.sjqp.driverexame.service.RealExerciseService;
import com.sjqp.driverexame.service.SimulatedExerciseService;
import com.sjqp.driverexame.util.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author qinpeng
 * @date 2019/1/8
 */
@Service
public class RealExerciseServiceImpl implements RealExerciseService {

    @Autowired
    private RealExerciseMapper realExerciseMapper;

    private Logger logger = LoggerFactory.getLogger(RealExerciseServiceImpl.class);

    @Override
    public  ApiResult<List<RealExercise>> getRealExercise(Integer currentPageNo, Integer pageSize) {

        try {
            if(Objects.nonNull(currentPageNo) && Objects.nonNull(pageSize)) {
                /**插件分页 */
                PageHelper.startPage(currentPageNo, pageSize);
                List<RealExercise> realExerciseList = realExerciseMapper.selectAll();
                PageInfo<RealExercise> pageInfo = new PageInfo<>(realExerciseList);
                ApiResult<List<RealExercise>> apiResult = new ApiResult<>(ApiResult.SUCCESS_RESULT);
                apiResult.setData(pageInfo.getList());
                apiResult.setCurrentPageNo(pageInfo.getPageNum());
                apiResult.setTotalCount((int) pageInfo.getTotal());
                apiResult.setPageSize(pageInfo.getPageSize());
                return apiResult;
            }
            return new ApiResult(ApiResult.FAIL_RESULT);
        } catch (Exception e) {
            logger.error("RealExerciseServiceImpl e:{}",e);
        }
        return new ApiResult(ApiResult.FAIL_RESULT,"系统异常");
    }
}
