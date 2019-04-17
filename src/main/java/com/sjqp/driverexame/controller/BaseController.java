package com.sjqp.driverexame.controller;

import com.sjqp.driverexame.entity.SimulatedExercise;
import com.sjqp.driverexame.mapper.SimulatedExerciseMapper;
import com.sjqp.driverexame.service.SimulatedExerciseService;
import com.sjqp.driverexame.util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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


    @RequestMapping(value = "userInfo",method = RequestMethod.GET)
    public String userInfo(){
        return "userInfo";
    }


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

    /** 真题列表 */
    @RequestMapping(value = "real-exercise-list",method = RequestMethod.GET)
    public String realExerciseList(){
        return "real-exercise-list";
    }

    /** 错题列表 */
    @RequestMapping(value = "error-exercise-list",method = RequestMethod.GET)
    public String errorExerciseList(){
        return "error-exercise-list";
    }

    /** 模拟练习题列表 */
    @RequestMapping(value = "simulated-exercise-list",method = RequestMethod.GET)
    public String simulatedExerciseList(){
        return "simulated-exercise-list";
    }
    /**批量上传题目页面*/
    @RequestMapping(value = "upload-question",method = RequestMethod.GET)
    public String uploadQuestion(HttpServletRequest request, Model model){
        model.addAttribute("questionType",request.getParameter("questionType"));
        return "upload-question";
    }
    /**答题记录页面*/
    @RequestMapping(value = "record-score-list",method = RequestMethod.GET)
    public String recordScoreList(HttpServletRequest request){
        return "record-score-list";
    }

    /**后台欢迎页面*/
    @RequestMapping(value = "welcome",method = RequestMethod.GET)
    public String welcome(HttpServletRequest request){
        return "welcome";
    }
}
