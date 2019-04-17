package com.sjqp.driverexame.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.sjqp.driverexame.entity.Module;
import com.sjqp.driverexame.entity.RoleModule;
import com.sjqp.driverexame.entity.UserInfo;
import com.sjqp.driverexame.inc.CacheKey;
import com.sjqp.driverexame.inc.Const;
import com.sjqp.driverexame.service.RedisService;
import com.sjqp.driverexame.util.ApiResult;
import com.sjqp.driverexame.util.MD5Encoder;
import com.sjqp.driverexame.util.SecurityCodeUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiashiran on 2019/1/8.
 */
@WebFilter(urlPatterns = "/api/*", filterName = "loginFilter")
public class LoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    private RedisService redisService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String currPath = request.getRequestURI();
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Const.LOGINED_KEY);

        if (userInfo == null) {
            if (!StringUtils.isEmpty(currPath) && currPath.contains(Const.LOGIN_URL)) {

                login(request, response);
                return;
            } else if (!StringUtils.isEmpty(currPath) && currPath.contains(Const.ADMIN_LOGIN_URL)) {

                login(request, response);
                return;
            } else if (!StringUtils.isEmpty(currPath) && currPath.contains(Const.SECURITY_CODE_URL)) {

                getLoginSecurityCodeImage(request, response);
                return;
            } else {
                ApiResult result = new ApiResult();
                result.setMsg("未登录");
                result.setCode(Const.NO_LOGIN_STATUS);
                write(response, result);
                return;
            }
        } else {
            //登出
            if (!StringUtils.isEmpty(currPath) && currPath.contains(Const.LOGOUT_URL)) {
                logout(request, response);
                return;
            }
            if (currPath.contains(Const.LOGIN_URL) || currPath.contains(Const.ADMIN_LOGIN_URL) || currPath.contains(Const.SECURITY_CODE_URL)) {
                ApiResult result = new ApiResult();
                result.setCode(ApiResult.FAIL_RESULT);
                result.setMsg("您已经登录");
                write(response, result);
                return;
            }


            boolean volidate = volidate(request, userInfo);
            if (volidate) {

                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ApiResult result = new ApiResult();
                result.setMsg("未授权");
                result.setCode(Const.NO_AUTHORIZATION_STATUS);
                write(response, result);
                return;
            }
        }
    }


    /**
     * 登录 userInfo放到session中
     *
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            InputStream in = request.getInputStream();
            byte[] bytes = new byte[1024];
            int c;
            StringBuilder sb = new StringBuilder();
            while ((c = in.read(bytes)) > 0) {
                sb.append(new String(bytes, 0, c));
            }
            String json = sb.toString();
            if (StringUtils.isNotEmpty(json)) {
                JSONObject jsonObject = JSON.parseObject(json);
                String userName = jsonObject.getString("userName");
                String password = jsonObject.getString("password");
                String securityCode = jsonObject.getString("securityCode");
                if (StringUtils.isEmpty(securityCode)) {
                    ApiResult result = new ApiResult();
                    result.setMsg("验证码不能为空");
                    result.setCode(ApiResult.FAIL_RESULT);
                    write(response, result);
                    return;
                }
                if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
                    ApiResult result = new ApiResult();
                    result.setMsg("用户或密码不能为空");
                    result.setCode(ApiResult.FAIL_RESULT);
                    write(response, result);
                    return;
                }
                String pwdMd5 = MD5Encoder.encode(password.trim());
                String securityCodeInsession = (String) session.getAttribute(SecurityCodeUtil.SECURITY_CODE);

                //登录校验
                ApiResult<UserInfo> apiResult = executeValidateLogin(userName, pwdMd5, securityCode, securityCodeInsession);
                if (apiResult != null) {
                    if (apiResult.getCode() == ApiResult.FAIL_RESULT) {
                        //登录失败
                        write(response, apiResult);
                    } else {
                        //保存当前登录用户信息
                        UserInfo userInfo = apiResult.getData();
                        userInfo.setPassword("");

                        //保存用户信息到session中
                        session.setAttribute(Const.LOGINED_KEY, userInfo);
                        write(response, apiResult);
                    }
                }
            }

        } catch (Exception e) {
            logger.error("login exception e{}", e);
            ApiResult result = new ApiResult();
            result.setMsg("系统异常");
            result.setCode(ApiResult.FAIL_RESULT);
            write(response, result);
        }
    }

    /**
     * 登出 session中删除userInfo
     *
     * @param request
     * @param response
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) {
        ApiResult result = null;
        try {
            request.getSession().removeAttribute(Const.LOGINED_KEY);
            request.getSession().removeAttribute(SecurityCodeUtil.SECURITY_CODE);
            result = new ApiResult();
            result.setCode(ApiResult.SUCCESS_RESULT);
            result.setMsg("登出成功");
        } catch (Exception e) {
            logger.error("logout exception e{}", e);
            result.setMsg("系统异常");
            result.setCode(ApiResult.FAIL_RESULT);
        }
        write(response, result);

    }

    /**
     * 校验权限
     * 根据用户信息判断是否有对应接口的权限
     */
    private boolean volidate(HttpServletRequest request, UserInfo userInfo) {
        boolean flag = false;
        String urlPath = request.getRequestURI();
        List<Integer> moduleIdList = new ArrayList<>();
        if (userInfo != null) {
            Integer roleId = userInfo.getRoleId();
            /**
             * 从缓存获得角色模块信息
             */
            List<RoleModule> roleModuleList = JSON.parseArray(redisService.get(CacheKey.ROLE_MODULE), RoleModule.class);
            if (roleId != null && !CollectionUtils.isEmpty(roleModuleList)) {
                for (RoleModule roleModule : roleModuleList) {
                    if (roleId.equals(roleModule.getRoleId())) {
                        //将通以roleId下的不同模块Id加入集合中
                        moduleIdList.add(roleModule.getModuleId());
                    }
                }
            }
            /**
             * 模块信息
             */
            List<Module> moduleList = JSON.parseArray(redisService.get(CacheKey.MODULE), Module.class);
            //获得对应模块Id的url前缀
            List<String> curlList = getPrfixUrl(moduleList,moduleIdList);
            if (!CollectionUtils.isEmpty(curlList)) {
                for (String url : curlList) {
                    if (urlPath != null && urlPath.contains(url)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 生成校验码
     *
     * @param request
     * @param response
     */
    private void getLoginSecurityCodeImage(HttpServletRequest request, HttpServletResponse response) {
        BufferedImage imgBuf = SecurityCodeUtil.initImage();
        String scode = SecurityCodeUtil.generateSecurityCode(imgBuf);
        HttpSession session = request.getSession();
        session.setAttribute(SecurityCodeUtil.SECURITY_CODE, scode);
        logger.info("sessionId: "+request.getSession().getId()+"产生的验证码："+scode);
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = response.getOutputStream();
            ImageIO.write(imgBuf, "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            logger.error(e.getClass() + "，验证码加载失败！" + e.getMessage(), e);
        }
    }


    /**
     * 发送数据
     *
     * @param response
     * @param result
     */
    private void write(HttpServletResponse response, ApiResult result) {
        try {
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            logger.error("write response date exception:", e);
        }
    }

    /**
     * 登录校验
     * @param userName
     * @param pwdMd5
     * @param securityCode
     * @param securityCodeInSession
     * @return
     * @throws Exception
     */
    public ApiResult<UserInfo> executeValidateLogin(String userName, String pwdMd5, String securityCode, String securityCodeInSession) throws Exception {

        ApiResult<UserInfo> apiResult = new ApiResult<UserInfo>();
        try {


            //判断验证码是否正确
            if (StringUtils.isNotBlank(securityCode)) {
                if (!securityCode.toLowerCase().equals(securityCodeInSession)) {
                    apiResult.setCode(ApiResult.FAIL_RESULT);
                    apiResult.setMsg("验证码输入错误");
                    return apiResult;
                }
            }
            //用户信息
            List<UserInfo> userInfoList = JSON.parseArray(redisService.get(CacheKey.USER_INFO), UserInfo.class);
            if (!CollectionUtils.isEmpty(userInfoList)) {
                for (UserInfo userInfo : userInfoList) {
                    if (userName.equals(userInfo.getUsername()) && pwdMd5.equals(userInfo.getPassword())) {
                        apiResult.setCode(ApiResult.SUCCESS_RESULT);
                        apiResult.setMsg("登录成功");
                        apiResult.setData(userInfo);
                        return apiResult;
                    }
                }
            }
            apiResult.setMsg("用户名或密码错误");
            apiResult.setCode(ApiResult.FAIL_RESULT);
        } catch (Exception e) {
            logger.error("用户名或密码校验异常：e{}",e);
        }
        return apiResult;
    }

    /**
     * 获取所有模块中prfix
     *
     * @param moduleList
     * @return
     */
    public List<String> getPrfixUrl(List<Module> moduleList,List<Integer> modeIdList) {
        try {
            List<String> urlList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(moduleList) && !CollectionUtils.isEmpty(modeIdList)){
                for (Module module : moduleList){
                    for (Integer modleId : modeIdList){
                        if (modleId.equals(module.getId())){
                            //模块url
                            urlList.add(module.getPrefix());
                            break;
                        }
                    }
                }
            }
            return urlList;
        } catch (Exception e) {
            logger.error("loginFilter getPermString exception:{}", e);
            return null;
        }
    }
}
