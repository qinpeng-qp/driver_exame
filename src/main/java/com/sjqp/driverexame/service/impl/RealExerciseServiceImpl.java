package com.sjqp.driverexame.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjqp.driverexame.entity.RealExercise;
import com.sjqp.driverexame.mapper.RealExerciseMapper;
import com.sjqp.driverexame.service.RealExerciseService;
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
                apiResult.setTotal((int) pageInfo.getTotal());
                apiResult.setPageSize(pageInfo.getPageSize());
                return apiResult;
            }
            return new ApiResult(ApiResult.FAIL_RESULT);
        } catch (Exception e) {
            logger.error("RealExerciseServiceImpl e:{}",e);
        }
        return new ApiResult(ApiResult.FAIL_RESULT,"系统异常");
    }

    @Override
    public ApiResult saveRealExercise(List<RealExercise> realExerciseList) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            int row = realExerciseMapper.insertList(realExerciseList);
            if (row > 0){
                apiResult.setData(ApiResult.SUCCESS_RESULT);
                apiResult.setMsg("保存成功");
            }
        } catch (Exception e) {
            logger.error("savRealExercise e{}",e);
            return new ApiResult(ApiResult.FAIL_RESULT,"系统异常");
        }
        return apiResult;
    }

    /**
     * 批量删除此处运用到spring的事物
     * @param realExerciseList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult deleteRealExercise(List<RealExercise> realExerciseList) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            if (!CollectionUtils.isEmpty(realExerciseList)){
                for (RealExercise realExercise : realExerciseList){
                    realExerciseMapper.deleteByPrimaryKey(realExercise);
                }
            }
            apiResult.setData(ApiResult.SUCCESS_RESULT);
            apiResult.setMsg("删除成功");
        } catch (Exception e) {
            logger.error("savRealExercise e{}",e);
            throw e;
        }
        return apiResult;
    }


}
