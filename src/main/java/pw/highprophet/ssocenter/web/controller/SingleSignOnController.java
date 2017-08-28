package pw.highprophet.ssocenter.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pw.highprophet.ssocenter.web.pojo.User;
import pw.highprophet.ssocenter.web.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static pw.highprophet.ssocenter.web.common.ConstantPool.*;

/**
 * Created by HighProphet945 on 2017/7/25.
 */
@Controller
@RequestMapping("/sso")
public class SingleSignOnController {

    @Autowired
    private UserService service;

    /**
     * 单点登录接口.认证中心会从session和Cookie中寻找有效的登陆凭据.
     *
     * @param redirectPath 请求处理完成后要返回原应用的地址.
     * @param suid         从认证中心Cookie中读取的suid
     * @param token        从认证中心Cookie中读取的token
     * @param leadToSignOn 如果用户没有登录,是否将其引导至认证中心登录界面的标志
     * @return 如果请求是非法的则返回错误页面的视图名;其他情况则返回重定向字符串
     */
    @RequestMapping(path = "signon")
    @SuppressWarnings("unchecked")
    public String signOn(@RequestParam("redirectAddress") String redirectPath,
                         @RequestParam(defaultValue = "true") boolean leadToSignOn,
                         @CookieValue(value = SSO_USER_IDENTIFIER, required = false) String suid,
                         @CookieValue(value = SSO_USER_ACCESS_TOKEN, required = false) String token, HttpSession session) {
        //把请求附带的重定向地址保存到session中,用于处理完单点登录后转回原应用.
        session.setAttribute("redirectAddress", redirectPath);

        //处理用户已登录但session被销毁的情况(使用cookie保存的凭据)
        service.recoverSessionSignOn(suid, token, session);

        //判断当前用户是否登录
        if ((Boolean) session.getAttribute(IS_USER_LOG_IN)) {
            //如果已经登录则返回其suid与accessToken,返回原应用
            redirectPath = session.getAttribute("redirectAddress").toString();
            session.removeAttribute("redirectAddress");
            return "redirect:" + redirectPath +
                    "?suid=" + session.getAttribute(SSO_USER_IDENTIFIER) +
                    "&accessToken=" + session.getAttribute(SSO_USER_ACCESS_TOKEN);
        }
        //用户没有登录
        //判断leadToSignOn参数,若为true则说明需要进行登录,引导至登陆界面
        if (leadToSignOn) {
            return "redirect:/user/signon";
        } else {
            //不需要引导至登录界面,直接返回到原应用
            session.removeAttribute("redirectAddress");
            return "redirect:" + redirectPath;
        }
    }

    /*
        身份校验,判断请求提供的suid和token是否有效.
        有效"valid",否则返回"invalid"
     */
    @RequestMapping("authenticate")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    String authenticate(String suid, String accessToken) {
        if (service.isSuidAndTokenValid(suid, accessToken)) {
            return "valid";
        }
        return "invalid";
    }

    @RequestMapping("fetch")
    public @ResponseBody
    String fetchUserInfo(String suid, String accessToken) {
        if (service.isSuidAndTokenValid(suid, accessToken)) {
            User user = service.getUserBySuid(suid);
            return user.toString();
        }
        return "{}";
    }

    /*
        处理用户注销
     */
    @RequestMapping(path = "signoff")
    @SuppressWarnings("unchecked")
    public String signOff(String suid, String accessToken, String redirectAddress,
                          HttpSession session, HttpServletResponse response) {
        //判断suid和token是否有效
        if (service.isSuidAndTokenValid(suid, accessToken)) {
            //先通过提供的suid和token来进行注销
            service.signOff(suid, session);
        } else {
            //若无法通过suid和token进行注销则对当前session中的用户进行注销
            Object id = session.getAttribute(SSO_USER_IDENTIFIER);
            if (id != null) {
                service.signOff(id.toString(), session);
            }
        }
        service.clearCookie(response);
        return "redirect:" + redirectAddress;
    }

}
