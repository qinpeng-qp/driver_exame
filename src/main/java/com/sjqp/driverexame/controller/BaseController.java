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

    /**
     * 管理后台页面
     * @return
     */
    @RequestMapping(value = "admin/index")
    public String index(){
        return "admin-index";
    }

    /**
     * 前台页面
     * @return
     */
    @RequestMapping(value = "index")
    public String frontIndex(){
        return "index";
    }

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    /** 导航 */
    @RequestMapping(value = "nav",method = RequestMethod.GET)
    public String nav(){
        return "nav";
    }

    /** 导航 */
    @RequestMapping(value = "userInfo",method = RequestMethod.GET)
    public String userInfo(){
        return "userInfo";
    }

    /** 导航 */
    @RequestMapping(value = "order-list",method = RequestMethod.GET)
    public String order(){
        return "order-list";
    }

    /** 修改密码页面 */
    @RequestMapping(value = "changePwd",method = RequestMethod.GET)
    public String memberAdd(){
        return "change-pwd";
    }

    /** 创建用户页面 */
    @RequestMapping(value = "user-add",method = RequestMethod.GET)
    public String userAdd(){
        return "user-add";
    }



}
