package com.sjqp.driverexame.controller;

import com.sjqp.driverexame.entity.SimulatedExercise;
import com.sjqp.driverexame.service.SimulatedExerciseService;
import com.sjqp.driverexame.util.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author qinpeng
 * @date 2019/01/08
 * 模拟练习控制层
 */
@Controller
@RequestMapping(value = "simulate")
public class SimulatedExerciseController {

    private Logger logger = LoggerFactory.getLogger(SimulatedExerciseController.class);

    @Autowired
    private SimulatedExerciseService simulatedExerciseService;

    @RequestMapping(value = "simulated-exercise")
    public String index(){
        return "simulated-exercise";
    }

    @RequestMapping(value = "getSimulated")
    @ResponseBody
    public ApiResult<List<SimulatedExercise>> getSimulatedExercise(@RequestParam(value = "page",defaultValue = "1") Integer currentPageNo,
                                                                   @RequestParam(value = "limit",defaultValue = "1")  Integer pageSize){
        try {
            return   simulatedExerciseService.getSimulatedExercise(currentPageNo, pageSize);
        } catch (Exception e) {
            logger.error("SimulatedExerciseController error {}",e);
        }
        return new ApiResult<>(ApiResult.FAIL_RESULT,"系统异常");
    }


}
