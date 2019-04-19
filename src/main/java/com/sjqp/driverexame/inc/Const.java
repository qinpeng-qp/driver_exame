package com.sjqp.driverexame.inc;

public class Const {
    public final static String LOGIN_SUCCESS = "登录成功";

    public final static String LOGIN_FAILURE = "用户名或密码错误";

    public final static String USERNAME_PWD_NOT_EMPTY = "用户名或密码不能为空";

    public static String LOGIN_URL = "/api/user/login";

    public static String ADMIN_LOGIN_URL = "/api/admin/login";

    public static String  SECURITY_CODE_URL = "/api/login/securitycode";

    public static String LOGOUT_URL = "/api/system/logout";


    public static int NO_LOGIN_STATUS = 401;

    public static int NO_AUTHORIZATION_STATUS = 403;



    /** 记录登录用户 */
    public static final String LOGINED_KEY = "logined";

}
