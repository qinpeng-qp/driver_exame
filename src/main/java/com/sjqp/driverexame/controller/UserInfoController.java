package com.sjqp.driverexame.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sjqp.driverexame.entity.UserInfo;
import com.sjqp.driverexame.entity.dto.UserInfoDto;
import com.sjqp.driverexame.service.UserInfoService;
import com.sjqp.driverexame.util.ApiResult;
import com.sjqp.driverexame.util.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author qinpeng
 * @date 2019/02/24
 */
@RestController
@RequestMapping("/api/userModule")
public class UserInfoController {

    private Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 验证密码，密码长度为6到16位且必需为数字、大写字母或小写字母、符号的组合
     */
    private Pattern pwdPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[#@!~%^&*])[a-zA-Z\\d#@!~%^&*]{6,16}$");

    /**
     * 邮箱正则
     */
    private Pattern emailPattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    /**
     * 手机号正则
     */
    private Pattern mobilePattern = Pattern.compile("^13[0-9]{1}[0-9]{8}$|15[0-9]{1}[0-9]{8}$|18[0-9]{1}[0-9]{8}$|17[0-9]{1}[0-9]{8}$");

    /**
     * 管理员
     */
    private final String ADMIN = "admin";

    @GetMapping(value = "/getUsername", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult getUsername(@RequestParam(defaultValue = "1") Integer currentNo,@RequestParam(defaultValue = "10")Integer pageSize) {
        ApiResult<List<UserInfo>> apiResult = new ApiResult<>(ApiResult.FAIL_RESULT);
        try {
            apiResult = userInfoService.getUserInfo(currentNo,pageSize);
        } catch (Exception e) {
            logger.error("UserInfoController getUsername e:{}", e);
            apiResult.setMsg("系统异常");
        }
        logger.info(JSON.toJSONString(apiResult));
        return apiResult;
    }

    @GetMapping(value = "/getUserInfoByName", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult getUserInfoByName(String username) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            if (Objects.isNull(username)) {
                return new ApiResult<>(ApiResult.FAIL_RESULT, "用户名不能为空");
            }
            apiResult = userInfoService.getUserInfoByName(username);
        } catch (Exception e) {
            logger.error("UserInfoController getUserInfoByName e:{}", e);
            apiResult.setMsg("系统异常");
        }
        return apiResult;
    }

    @PutMapping(value = "/updatePwd/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult updatePassword(@PathVariable Integer userId,@RequestBody UserInfoDto userInfoDto) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            String oldPwd = userInfoDto.getOldPwd();
            String newPwd = userInfoDto.getNewPwd();
            String role = userInfoDto.getRole();
            if (Objects.isNull(userId)) {
                apiResult.setMsg("userId不能为空");
                return apiResult;
            }
            if (Objects.isNull(role)) {
                apiResult.setMsg("角色不能为空");
                return apiResult;
            }
            if (Objects.isNull(newPwd)) {
                apiResult.setMsg("新密码不能为空");
                return apiResult;
            } else {
                if (!pwdPattern.matcher(newPwd).find()) {
                    apiResult.setMsg("密码长度为6到16位且必需为数字、字母、符号的组合");
                    return apiResult;
                }
            }
            //管理员可以直接修改用户密码
            if (ADMIN.equals(role)) {
                apiResult = userInfoService.changePassword(userId, null, newPwd, role);
            } else {
                if (Objects.isNull(oldPwd)) {
                    apiResult.setMsg("旧密码不能为空");
                    return apiResult;
                } else {
                    apiResult = userInfoService.changePassword(Integer.valueOf(userId), oldPwd, newPwd, role);
                }
            }
        } catch (Exception e) {
            logger.error("UserInfoController updatePwd e:{}", e);
            apiResult.setMsg("系统异常");
        }
        return apiResult;
    }

    @PostMapping(value = "/userInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult createAccount(@RequestBody UserInfo userInfoDto) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {

            if (userInfoDto.getUsername() == null
                    || userInfoDto.getPassword() == null
                    || userInfoDto.getRoleId() == null
                    || userInfoDto.getMobile() == null
                    || userInfoDto.getEmail() == null) {
                return new ApiResult(ApiResult.FAIL_RESULT, "参数错误");
            }
            if (!pwdPattern.matcher(userInfoDto.getPassword()).find()) {
                return new ApiResult(ApiResult.FAIL_RESULT, "密码长度为6到16位且必须为数字、字母、符号的组合");
            }
            if (!mobilePattern.matcher(userInfoDto.getMobile()).find()) {
                return new ApiResult(ApiResult.FAIL_RESULT, "手机号格式不正确");
            }
            if (!emailPattern.matcher(userInfoDto.getEmail()).find()) {
                return new ApiResult(ApiResult.FAIL_RESULT, "邮箱格式不正确");
            }
            userInfoDto.setCreateTime(new Date());
            userInfoDto.setPassword(MD5Encoder.encode(userInfoDto.getPassword()));
            apiResult = userInfoService.createAccount(userInfoDto);

        } catch (Exception e) {
            logger.error("UserInfoController createAccount e:{}", e);
            apiResult.setMsg("系统异常");
        }
        return apiResult;
    }

    @DeleteMapping(value = "/userInfo/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult deleteAccount(@PathVariable Integer userId) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {

            if (Objects.isNull(userId)) {
                return new ApiResult(ApiResult.FAIL_RESULT, "用户id不能为空");
            }
            apiResult = userInfoService.deleteAccount(userId);
        } catch (Exception e) {
            logger.error("UserInfoController deleteAccount e:{}", e);
            apiResult.setMsg("系统异常");
        }
        return apiResult;
    }


    @PutMapping(value = "/updateRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult updateRole(@RequestBody UserInfo userInfo) {
        ApiResult apiResult = new ApiResult(ApiResult.FAIL_RESULT);
        try {
            return userInfoService.updateRole(userInfo);
        } catch (Exception e) {
            logger.error("UserInfoController updatePwd e:{}", e);
            apiResult.setMsg("系统异常");
        }
        return apiResult;
    }

}
