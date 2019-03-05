package com.sjqp.driverexame.service.impl;

import com.sjqp.driverexame.entity.Account;
import com.sjqp.driverexame.entity.Admin;
import com.sjqp.driverexame.mapper.AccountMapper;
import com.sjqp.driverexame.mapper.AdminMapper;
import com.sjqp.driverexame.service.AccountService;
import com.sjqp.driverexame.service.AdminService;
import com.sjqp.driverexame.util.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author qinpeng on 2018/12/20
 * 用户服务实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public boolean login(String userName, String password) {
        /**
         * 获得用户名和密码，并进行MD5加密
         */
        Admin admin = new Admin(userName,MD5Encoder.encode(password));
        try {
            List<Admin> accounts = adminMapper.select(admin);
            if (!CollectionUtils.isEmpty(accounts)){
                return true;
            }
        } catch (Exception e) {
            logger.info("AdminServiceImpl login exception{}",e);
        }
        return false;
    }
}
