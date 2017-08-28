package pw.highprophet.ssocenter.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pw.highprophet.ssocenter.web.pojo.User;
import pw.highprophet.ssocenter.web.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by HighProphet945 on 2017/7/25.
 */
@Controller
@RequestMapping("/user")
public class UserRegisterLoginController {

    private UserService service;

    /*
       展示注册页面
    */
    @RequestMapping(path = "register", method = RequestMethod.GET)
    public String showRegisterPage(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute(new User());
        }
        return "register";
    }

    /*
        根据参数创建新用户
     */
    @RequestMapping(path = "register", method = RequestMethod.POST)
    public String doRegister(User user, Errors errors, Model model) {
        if (service.doesUserExist(user.getName())) {//同名用户已存在,返回注册页面
            model.addAttribute(user);
            errors.rejectValue("name", "duplicated", "duplicated username");
            return "register";
        }
        service.createUser(user);
        //注册成功,重定向至登陆页面
        return "redirect:/user/signon";
    }

    /*
        展示登陆界面
     */
    @RequestMapping(path = "signon", method = RequestMethod.GET)
    public String showSignOnPage(@RequestParam(value = "redirectAddress", defaultValue = "/index") String redirectPath,
                                 Model model, HttpSession session) {
        if (session.getAttribute("redirectAddress") == null) {
            session.setAttribute("redirectAddress", redirectPath);
        }
        if (!model.containsAttribute("user")) {
            model.addAttribute(new User());
        }
        return "signon";
    }

    /*
        处理登陆请求
     */
    @RequestMapping(path = "signon", method = RequestMethod.POST)
    public String doSignOn(HttpServletResponse response, HttpSession session,
                           User user, Errors errors, Model model) {
        //校验用户身份,若不合法则返回登录页面
        model.addAttribute(user);
        if ((user = service.validateAndGetUser(user)) == null) {
            errors.rejectValue("name", "non-existence", "Username or password error");
            return "signon";
        }
        //登录当前用户
        user = service.signOn(user.getSuid(), session);

        //保存Cookie以便下次登录
        service.saveCookie(response, user.getSuid(), user.getAccessToken());

        //返回原应用并附带用户名及其accessToken
        String redirectPath = session.getAttribute("redirectAddress").toString() +
                "?suid=" + user.getSuid() + "&accessToken=" + user.getAccessToken();
        session.removeAttribute("redirectAddress");
        return "redirect:" + redirectPath;
    }

    @Autowired
    public UserRegisterLoginController setService(UserService service) {
        this.service = service;
        return this;
    }
}
