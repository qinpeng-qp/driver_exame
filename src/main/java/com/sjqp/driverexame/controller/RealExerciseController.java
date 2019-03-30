package com.sjqp.driverexame.controller;

import com.sjqp.driverexame.entity.RealExercise;
import com.sjqp.driverexame.service.RealExerciseService;
import com.sjqp.driverexame.util.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ApiResult<List<RealExercise>> getRealExercise(@RequestParam(value = "page",defaultValue = "1") Integer currentPageNo,
                                                         @RequestParam(value = "limit",defaultValue = "1")  Integer pageSize){
        try {
            return   realExerciseService.getRealExercise(currentPageNo, pageSize);
        } catch (Exception e) {
            logger.error("RealExerciseController error {}",e);
        }
        //"id":3,"questionId":null,"comment":"第三题","createTime":"2019-02-12T07:34:50.849+0000","answer":"C","choice":"{\"A\": \"酒驾\", \"B\": \"超速\", \"C\": \"违章\", \"D\": \"逃逸\"}"}
        return new ApiResult<>(ApiResult.FAIL_RESULT,"系统异常");
    }

    @DeleteMapping(value = "deleteReal",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ApiResult<List<RealExercise>> deleteRealExercise(@RequestBody List<RealExercise> realExerciseList){
        try {
            return   realExerciseService.deleteRealExercise(realExerciseList);
        } catch (Exception e) {
            logger.error("RealExerciseController error {}",e);
        }
        return new ApiResult<>(ApiResult.FAIL_RESULT,"系统异常");
    }
}
