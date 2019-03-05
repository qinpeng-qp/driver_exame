package com.sjqp.driverexame.controller;

import com.sjqp.driverexame.inc.Const;
import com.sjqp.driverexame.service.AccountService;
import com.sjqp.driverexame.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author qinpeng on 2018/12/20
 * 用户控制层
 */
@Controller
@RequestMapping(value = "/user")
public class AccountController {

    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String login(String userName,String password){
        logger.info(userName);
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password) ){
            return Const.USERNAME_PWD_NOT_EMPTY;
        }else {
            if (accountService.login(userName,password)){
                return Const.LOGIN_SUCCESS;
            }
        }
        return Const.LOGIN_FAILURE;
    }


}
