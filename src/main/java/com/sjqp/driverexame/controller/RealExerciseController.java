package com.sjqp.driverexame.controller;

import com.sjqp.driverexame.entity.RealExercise;
import com.sjqp.driverexame.service.RealExerciseService;
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
 * 真题练习控制层
 */
@Controller
@RequestMapping(value = "real")
public class RealExerciseController {

    private Logger logger = LoggerFactory.getLogger(RealExerciseController.class);

    @Autowired
    private RealExerciseService realExerciseService;

    @RequestMapping(value = "real-exercise")
    public String index(){
        return "real-exercise";
    }

    @RequestMapping(value = "getReal")
    @ResponseBody
    public ApiResult<List<RealExercise>> getRealExercise(@RequestParam(value = "currentPageNo",defaultValue = "1") Integer currentPageNo,
                                                         @RequestParam(value = "pageSize",defaultValue = "1")  Integer pageSize){
        try {
            return   realExerciseService.getRealExercise(currentPageNo, pageSize);
        } catch (Exception e) {
            logger.error("RealExerciseController error {}",e);
        }
        return new ApiResult<>(ApiResult.FAIL_RESULT,"系统异常");
    }
}
