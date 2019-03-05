package com.sjqp.driverexame.controller;

import com.sjqp.driverexame.entity.SimulatedExercise;
import com.sjqp.driverexame.mapper.SimulatedExerciseMapper;
import com.sjqp.driverexame.service.SimulatedExerciseService;
import com.sjqp.driverexame.util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 基础控制层
 * @author qinpeng on 2018/12/26
 */
@Controller
@RequestMapping(value = "")
public class BaseController {

    @Autowired
    private SimulatedExerciseMapper exerciseMapper;

    @RequestMapping(value = "admin/index")
    public String index(){
        return "admin-index";
    }

    @RequestMapping(value = "index")
    public String frontIndex(){
        return "index";
    }

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    /** 导航 */
    @RequestMapping(value = "nav",method = RequestMethod.GET)
    public String nav(){
        return "nav";
    }

//    @RequestMapping(value = "/getSimulated")
//    @ResponseBody
//    public ApiResult<List<SimulatedExercise>> getSimulatedExercise(@RequestParam(value = "currentPageNo",defaultValue = "0") Integer currentPageNo,
//                                                                   @RequestParam(value = "pageSize",defaultValue = "10")  Integer pageSize){
//        try {
//            return simulatedExerciseService.getSimulatedExercise(currentPageNo,pageSize);
//        } catch (Exception e) {
//            logger.error("SimulatedExerciseController error {}",e);
//        }
//        return new ApiResult<>(ApiResult.FAIL_RESULT,"系统异常");
//    }
    @ResponseBody
    @RequestMapping("test")
    public List<SimulatedExercise> TEST(){
        return exerciseMapper.selectAll();
    }

}
