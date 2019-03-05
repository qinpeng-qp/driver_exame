package com.sjqp.driverexame.service.impl;

import com.sjqp.driverexame.entity.Account;
import com.sjqp.driverexame.mapper.AccountMapper;
import com.sjqp.driverexame.service.AccountService;
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
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;
    @Override
    public boolean login(String userName, String password) {
        /**
         * 获得用户名和密码，并进行MD5加密
         */
        Account account = new Account(userName,MD5Encoder.encode(password));
        try {
            List<Account> accounts = accountMapper.select(account);
            if (!CollectionUtils.isEmpty(accounts)){
                return true;
            }
        } catch (Exception e) {
            logger.info("BusinessServiceImpl login exception{}",e);
        }
        return false;
    }
}
