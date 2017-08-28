package pw.highprophet.ssocenter.web.common;

/**
 * Created by HighProphet945 on 2017/7/28.
 */
public class ConstantPool {
    public static final String BOOTSTRAP_CSS_HREF = "bootstrap.css.href";
    public static final String BOOTSTRAP_JS_SRC = "bootstrap.js.src";
    public static final String JQUERY_JS_SRC = "jquery.min.js.src";
    public static final String SSO_SIGN_ON_URL = "ssocenter.signon.url";
    public static final String SSO_CHECK_JSONP = "ssocenter.check.jsonp";
    public static final String SSO_VALIDATE_JSON = "ssocenter.validate.json";
    public static final String SSO_USER_IDENTIFIER = "sso.user.suid";
    public static final String SSO_USER_ACCESS_TOKEN = "sso.user.accessToken";


    public static final String AUTO_SIGN_ON_ATTEMPT_COUNT = "auto.signOn.attempt.count";
    public static final String IS_USER_LOG_IN = "user.login";
    public static final String USER_NAME = "user.name";
    public static final String CURRENT_USER = "currentUser";

    public static final String USER_VALID = "valid";
    public static final String USER_LOG_IN_PATH = "/user/login";

    public static final String DEFAULT_REDIRECT_PATH = "http://localhost:8080/bbs/index";
    public static final int DEFAULT_COOKIE_MAX_AGE = 3600 * 24 * 7;
    public static final int MAX_SESSION_INACTIVE_INTERVAL = 3600;
}
