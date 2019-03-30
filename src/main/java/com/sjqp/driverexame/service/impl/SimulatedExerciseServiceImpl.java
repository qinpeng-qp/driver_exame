package com.sjqp.driverexame.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjqp.driverexame.entity.SimulatedExercise;
import com.sjqp.driverexame.mapper.SimulatedExerciseMapper;
import com.sjqp.driverexame.service.SimulatedExerciseService;
import com.sjqp.driverexame.util.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author qinpeng
 * @date 2019/1/8
 */
@Service
public class SimulatedExerciseServiceImpl implements SimulatedExerciseService {

    @Autowired
    private SimulatedExerciseMapper simulatedExerciseMapper;

    private Logger logger = LoggerFactory.getLogger(SimulatedExerciseServiceImpl.class);

    @Override
    public ApiResult<List<SimulatedExercise>> getSimulatedExercise(Integer currentPageNo, Integer pageSize) {

        try {
            if(Objects.nonNull(currentPageNo) && Objects.nonNull(pageSize)) {
                /**插件分页 */
                PageHelper.startPage(currentPageNo, pageSize);
                List<SimulatedExercise> simulatedExerciseList = simulatedExerciseMapper.selectAll();
                PageInfo<SimulatedExercise> pageInfo = new PageInfo<>(simulatedExerciseList);
                ApiResult<List<SimulatedExercise>> apiResult = new ApiResult<List<SimulatedExercise>>(ApiResult.SUCCESS_RESULT);
                apiResult.setData(pageInfo.getList());
                apiResult.setCurrentPageNo(pageInfo.getPageNum());
                apiResult.setTotal((int) pageInfo.getTotal());
                apiResult.setPageSize(pageInfo.getPageSize());
                return apiResult;
            }
            return new ApiResult(ApiResult.FAIL_RESULT);
        } catch (Exception e) {
            logger.error("SimulatedExerciseServiceImpl e:{}",e);
        }
        return new ApiResult(ApiResult.FAIL_RESULT,"系统异常");
    }

    @Override
    public ApiResult saveSimulatedExercise(List<SimulatedExercise> simulatedExerciseList) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            int row = simulatedExerciseMapper.insertList(simulatedExerciseList);
            if (row > 0){
                apiResult.setData(ApiResult.SUCCESS_RESULT);
                apiResult.setMsg("保存成功");
            }
        } catch (Exception e) {
            logger.error("saveSimulatedExercise e{}",e);
            return new ApiResult(ApiResult.FAIL_RESULT,"系统异常");
        }
        return apiResult;
    }

    /**
     * 批量删除此处运用到spring的事物
     * @param simulatedExerciseList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult deleteSimulatedExercise(List<SimulatedExercise> simulatedExerciseList) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            if (!CollectionUtils.isEmpty(simulatedExerciseList)){
                for (SimulatedExercise realExercise : simulatedExerciseList){
                    simulatedExerciseMapper.deleteByPrimaryKey(realExercise);
                }
            }
            apiResult.setData(ApiResult.SUCCESS_RESULT);
            apiResult.setMsg("删除成功");
        } catch (Exception e) {
            logger.error("deleteSimulatedExercise e{}",e);
            throw e;
        }
        return apiResult;
    }
}
