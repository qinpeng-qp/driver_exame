package com.sjqp.driverexame.controller;

import com.sjqp.driverexame.inc.Const;
import com.sjqp.driverexame.service.AdminService;
import com.sjqp.driverexame.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理员控制层
 * @author qinpeng on 2018/12/27
 */
@Controller
@RequestMapping(value = "/adminModule/admin")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String login(String userName,String password){
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password) ){
            return Const.USERNAME_PWD_NOT_EMPTY;
        }else {
            if (adminService.login(userName,password)){
                return Const.LOGIN_SUCCESS;
            }
        }
        return Const.LOGIN_FAILURE;
    }
}
