package com.sjqp.driverexame.inc;

public class Const {
    public final static String LOGIN_SUCCESS = "登录成功";

    public final static String LOGIN_FAILURE = "用户名或密码错误";

    public final static String USERNAME_PWD_NOT_EMPTY = "用户名或密码不能为空";

    public static String LOGIN_URL = "/api/system/login";

    public static String ADMIN_LOGIN_URL = "/api/system/admin/login";

    public static String  SECURITY_CODE_URL = "/api/login/securitycode";

    public static String LOGOUT_URL = "/api/system/logout";
    /**
     * 前端查询企业Id和Name都调用了此接口，特殊处理，如果包含此url就放行
     */
    public static String SPECIAL_URL = "/api/enterpriseModule/enterprise/enterpriseIdAndNames";

    public static int NO_LOGIN_STATUS = 401;

    public static int NO_AUTHORIZATION_STATUS = 403;

    public static String ENTERPRISE_STTING = "/enterpriseModule/";

    public static String ROUTER_SETTING = "/routerModule/";

    public static String REPORT = "/reportModule/";

    public static String CDR = "/cdrModule/";

    public static String CURL = "/curlModule/";

    public static String SYSTEM_SETTING = "/systemModule/";

    public static String IVR = "/ivrModule/";

    /** 记录登录用户 */
    public static final String LOGINED_KEY = "logined";

}
