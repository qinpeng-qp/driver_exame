package com.sjqp.driverexame.controller;

import com.sjqp.driverexame.entity.ErrorExercise;
import com.sjqp.driverexame.service.ErrorExerciseService;
import com.sjqp.driverexame.util.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author qinpeng
 * @date 2019/01/08
 * 错题练习控制层
 */
@Controller
@RequestMapping(value = "error")
public class ErrorExerciseController {

    private Logger logger = LoggerFactory.getLogger(ErrorExerciseController.class);

    @Autowired
    private ErrorExerciseService errorExerciseService;

    @RequestMapping(value = "error-exercise")
    public String index(){
        return "error-exercise";
    }

    @RequestMapping(value = "getError")
    @ResponseBody
    public ApiResult<List<ErrorExercise>> getErrorExercise(@RequestParam(value = "page",defaultValue = "1") Integer currentPageNo,
                                                          @RequestParam(value = "limit",defaultValue = "1")  Integer pageSize){
        try {
            return   errorExerciseService.getErrorExercise(currentPageNo, pageSize);
        } catch (Exception e) {
            logger.error("ErrorExerciseController error {}",e);
        }
        return new ApiResult<>(ApiResult.FAIL_RESULT,"系统异常");
    }
}
