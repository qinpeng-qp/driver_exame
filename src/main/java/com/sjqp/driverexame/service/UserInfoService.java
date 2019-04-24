package com.sjqp.driverexame.service;



import com.sjqp.driverexame.entity.UserInfo;
import com.sjqp.driverexame.entity.dto.UserInfoDto;
import com.sjqp.driverexame.util.ApiResult;

import java.util.List;

/**
 * @author qinpeng
 * @date 2019/2/22
 */
public interface UserInfoService {

    /**
     * 修改用户密码，admin可以修改所有用户密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @param role
     * @return
     */
    ApiResult changePassword(Integer userId, String oldPwd, String newPwd, String role);

    /**
     * 获得所有用户的用户名
     * @return
     */
    ApiResult<List<UserInfo>> getUserInfo(Integer currentNo,Integer pageSize);

    /**
     * 通过用户名获得用户信息
     * @param username
     * @return
     */
    ApiResult getUserInfoByName(String username);

    /**
     * 创建用户
     * @param userInfo
     * @return
     */
    ApiResult createAccount(UserInfo userInfo);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    ApiResult deleteAccount(Integer userId);

    /**
     * 修改角色id
     * @param userInfo
     * @return
     */
    ApiResult updateRole(UserInfo userInfo);
}
