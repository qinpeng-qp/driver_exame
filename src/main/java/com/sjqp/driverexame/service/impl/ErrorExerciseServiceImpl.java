package com.sjqp.driverexame.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjqp.driverexame.entity.ErrorExercise;
import com.sjqp.driverexame.mapper.ErrorExerciseMapper;
import com.sjqp.driverexame.service.ErrorExerciseService;
import com.sjqp.driverexame.util.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * @author qinpeng
 * @date 2019/1/8
 */
@Service
public class ErrorExerciseServiceImpl implements ErrorExerciseService {

    @Autowired
    private ErrorExerciseMapper errorExerciseMapper;

    private Logger logger = LoggerFactory.getLogger(ErrorExerciseServiceImpl.class);

    @Override
    public  ApiResult<List<ErrorExercise>> getErrorExercise(Integer currentPageNo, Integer pageSize) {

        try {
            if(Objects.nonNull(currentPageNo) && Objects.nonNull(pageSize)) {
                /**插件分页 */
                PageHelper.startPage(currentPageNo, pageSize);
                Condition condition = new Condition(ErrorExercise.class);
                condition.orderBy("id");
                List<ErrorExercise> errorExercises = errorExerciseMapper.selectByCondition(condition);

                PageInfo<ErrorExercise> pageInfo = new PageInfo<>(errorExercises);
                ApiResult<List<ErrorExercise>> apiResult = new ApiResult<>(ApiResult.SUCCESS_RESULT);
                apiResult.setData(pageInfo.getList());
                apiResult.setPage(pageInfo.getPageNum());
                apiResult.setCount((int) pageInfo.getTotal());
                apiResult.setLimit(pageInfo.getPageSize());
                return apiResult;
            }
            return new ApiResult(ApiResult.FAIL_RESULT);
        } catch (Exception e) {
            logger.error("ErrorExerciseServiceImpl e:{}",e);
        }
        return new ApiResult(ApiResult.FAIL_RESULT,"系统异常");
    }
    @Override
    public ApiResult saveErrorExercise(List<ErrorExercise> errorExerciseList) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            int row = errorExerciseMapper.insertList(errorExerciseList);
            if (row > 0){
                apiResult.setData(ApiResult.SUCCESS_RESULT);
                apiResult.setMsg("保存成功");
            }
        } catch (Exception e) {
            logger.error("saveErrorExercise e{}",e);
            return new ApiResult(ApiResult.FAIL_RESULT,"系统异常");
        }
        return apiResult;
    }

    /**
     * 批量删除此处运用到spring的事物
     * @param errorExerciseList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult deleteErrorExercise(List<ErrorExercise> errorExerciseList) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            if (!CollectionUtils.isEmpty(errorExerciseList)){
                for (ErrorExercise realExercise : errorExerciseList){
                    errorExerciseMapper.deleteByPrimaryKey(realExercise);
                }
            }
            apiResult.setData(ApiResult.SUCCESS_RESULT);
            apiResult.setMsg("删除成功");
        } catch (Exception e) {
            logger.error("deleteErrorExercise e{}",e);
            throw e;
        }
        return apiResult;
    }

    @Override
    public ApiResult updateErrorExercise(ErrorExercise errorExercise) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            int row = errorExerciseMapper.updateByPrimaryKey(errorExercise);
            if (row > 0) {
                apiResult.setData(ApiResult.SUCCESS_RESULT);
                apiResult.setMsg("修改成功");
            }
        } catch (Exception e) {
            logger.error("updateErrorExercise e{}", e);
        }
        return apiResult;
    }

}
