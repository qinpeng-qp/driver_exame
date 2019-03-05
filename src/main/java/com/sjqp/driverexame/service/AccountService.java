package com.sjqp.driverexame.service;

/**
 * @author qinpeng on 2018/12/26
 */
public interface AccountService {
    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    public boolean login(String userName,String password);
}
