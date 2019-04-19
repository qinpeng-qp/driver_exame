package com.sjqp.driverexame.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sjqp.driverexame.entity.Role;
import com.sjqp.driverexame.entity.UserInfo;
import com.sjqp.driverexame.entity.dto.UserInfoDto;
import com.sjqp.driverexame.mapper.RoleMapper;
import com.sjqp.driverexame.mapper.UserInfoMapper;
import com.sjqp.driverexame.service.UserInfoService;
import com.sjqp.driverexame.util.ApiResult;
import com.sjqp.driverexame.util.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * @author qinpeng
 * @date 2019/02/21
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public ApiResult changePassword(Integer userId, String oldPwd, String newPwd, String role) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            UserInfo userInfo = new UserInfo();
            if (Objects.nonNull(userId)){
                userInfo.setId(userId);
                UserInfo object = userInfoMapper.selectByPrimaryKey(userInfo);
                if (Objects.isNull(object)){
                    apiResult.setMsg("用户不存在,或者输入的userId有误");
                    return apiResult;
                }
            }
            if (Objects.nonNull(oldPwd)) {
                userInfo.setId(userId);
                userInfo.setPassword(MD5Encoder.encode(oldPwd));
                logger.info(MD5Encoder.encode(oldPwd));
                List<UserInfo> userInfoList = userInfoMapper.select(userInfo);
                if (!CollectionUtils.isEmpty(userInfoList)) {
                    apiResult = updatePassword(newPwd, userId, apiResult);
                } else {
                    apiResult.setMsg("旧密码错误");
                }
            } else {
                //管理员可以直接修改用户密码，不需要输入旧密码
                apiResult = updatePassword(newPwd, userId, apiResult);
            }
        } catch (Exception e) {
            apiResult = new ApiResult<>(ApiResult.FAIL_RESULT, "系统异常");
            logger.error("修改用户密码异常:{}", e);
        }
        return apiResult;
    }

    @Override
    public ApiResult<List<UserInfo>> getUserInfo(Integer currentNo,Integer pageSize) {
        ApiResult<List<UserInfo>> apiResult = new ApiResult<>(ApiResult.FAIL_RESULT);
        try {
            PageHelper.startPage(currentNo,pageSize);
            List<UserInfo> userInfoList = userInfoMapper.selectAll();
            PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoList);
            apiResult.setCode(ApiResult.SUCCESS_RESULT);
            apiResult.setData(pageInfo.getList());
            apiResult.setCount((int) pageInfo.getTotal());
            apiResult.setLimit(pageInfo.getPageSize());
            apiResult.setPage(pageInfo.getPageNum());
        } catch (Exception e) {
            apiResult = new ApiResult<>(ApiResult.FAIL_RESULT, "系统异常");
            logger.error("获取用户名异常:{}", e);
        }
        return apiResult;
    }

    @Override
    public ApiResult getUserInfoByName(String username) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            String roleName = null;
            Condition condition = new Condition(UserInfo.class);
            Example.Criteria conditionCriteria = condition.createCriteria();
            conditionCriteria.andEqualTo("username", username);
            List<UserInfo> userInfoList = userInfoMapper.selectByCondition(condition);
            if (!CollectionUtils.isEmpty(userInfoList)) {
                apiResult.setCode(ApiResult.SUCCESS_RESULT);
                UserInfo userInfo = userInfoList.get(0);
                Integer roleId = userInfo.getRoleId();
                Role role = roleMapper.selectByPrimaryKey(roleId);
                if (Objects.nonNull(role)){
                    roleName = role.getName();
                }
                userInfo.setPassword("");
                String userJsonStr = JSON.toJSONString(userInfo);
                JSONObject jsonObject = JSON.parseObject(userJsonStr);
                //前端需要角色信息，封装返回的信息
                if (Objects.nonNull(jsonObject)) {
                    jsonObject.put("role", roleName);
                }
                apiResult.setData(jsonObject);
            }else {
                apiResult.setMsg("没有查到用户信息！");
            }
        } catch (Exception e) {
            apiResult = new ApiResult<>(ApiResult.FAIL_RESULT, "系统异常");
            logger.error("获取用户名信息异常:{}", e);
        }
        return apiResult;
    }

    @Override
    public ApiResult createAccount(UserInfo userInfo) {

        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            if (Objects.nonNull(userInfo)){
                //判断用户名是否存在
                UserInfo object = new UserInfo();
                object.setUsername(userInfo.getUsername());
                List<UserInfo> userInfoList = userInfoMapper.select(object);
                if (!CollectionUtils.isEmpty(userInfoList)){
                    return new ApiResult(ApiResult.FAIL_RESULT,"用户名已经存在");
                }
                int row = userInfoMapper.insert(userInfo);
                if (row > 0){
                    apiResult.setCode(ApiResult.SUCCESS_RESULT);
                    apiResult.setMsg("创建用户成功");
                }else {
                    apiResult.setCode(ApiResult.FAIL_RESULT);
                    apiResult.setMsg("创建用户失败");
                }
            }
        } catch (Exception e) {
            logger.error("UserInfoServiceImpl createAccount e{}",e);
            apiResult.setCode(ApiResult.FAIL_RESULT);
            apiResult.setMsg("系统异常");
        }
        return apiResult;
    }

    @Override
    public ApiResult deleteAccount(Integer userId) {

        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            if (Objects.nonNull(userId)){
                UserInfo userInfo = new UserInfo();
                userInfo.setId(userId);
                int row = userInfoMapper.deleteByPrimaryKey(userInfo);
                if (row > 0){
                    apiResult.setCode(ApiResult.SUCCESS_RESULT);
                    apiResult.setMsg("删除成功");
                }else {
                    apiResult.setCode(ApiResult.FAIL_RESULT);
                    apiResult.setMsg("删除用户失败");
                    logger.error("删除用户失败，用户id为{}",userInfo.getId());
                }
            }
        } catch (Exception e) {
            apiResult.setCode(ApiResult.FAIL_RESULT);
            apiResult.setMsg("系统异常");
            logger.error("UserInfoServiceImpl deletAccount e{}",e);
        }
        return apiResult;
    }

    @Override
    public ApiResult updateRole(UserInfoDto userInfoDto) {

        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            if (Objects.nonNull(userInfoDto.getId())){
                UserInfo userInfo = new UserInfo();
                userInfo.setId(userInfoDto.getId());
                userInfo.setRoleId(userInfoDto.getRoleId());
                int row = userInfoMapper.updateByPrimaryKeySelective(userInfo);
                if (row > 0){
                    apiResult.setCode(ApiResult.SUCCESS_RESULT);
                    apiResult.setMsg("更新成功");
                }else {
                    apiResult.setCode(ApiResult.FAIL_RESULT);
                    apiResult.setMsg("更新失败");
                    logger.error("更新失败，用户id为{}",userInfo.getId());
                }
            }
        } catch (Exception e) {
            apiResult.setCode(ApiResult.FAIL_RESULT);
            apiResult.setMsg("系统异常");
            logger.error("UserInfoServiceImpl updateRole e{}",e);
        }
        return apiResult;
    }

    /**
     * 修改密码
     *
     * @param newPwd
     * @param userId
     * @param apiResult
     * @return
     */
    private ApiResult updatePassword(String newPwd, Integer userId, ApiResult apiResult) {

        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userId);
            userInfo.setPassword(MD5Encoder.encode(newPwd));
            int row = userInfoMapper.updateByPrimaryKeySelective(userInfo);
            if (row > 0) {
                apiResult.setCode(ApiResult.SUCCESS_RESULT);
                apiResult.setMsg("修改密码成功");
                return apiResult;
            } else {
                apiResult.setCode(ApiResult.FAIL_RESULT);
                apiResult.setMsg("修改密码失败");
            }
        } catch (Exception e) {
            logger.error("UserInfoServiceImpl updatePassword exception{}", e);
            apiResult.setCode(ApiResult.FAIL_RESULT);
            apiResult.setMsg("系统异常");
        }
        return apiResult;
    }
}
