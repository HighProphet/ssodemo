package pw.highprophet.ssocenter.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pw.highprophet.ssocenter.web.pojo.User;

import javax.servlet.http.HttpSession;

import static pw.highprophet.ssocenter.web.common.ConstantPool.CURRENT_USER;
import static pw.highprophet.ssocenter.web.common.ConstantPool.IS_USER_LOG_IN;

/**
 * Created by HighProphet945 on 2017/7/30.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(path = {"/", "/index", "/home"})
    public String index(Model model, HttpSession session) {
        if ((boolean) session.getAttribute(IS_USER_LOG_IN)) {
            model.addAttribute("user", (User) session.getAttribute(CURRENT_USER));
        }
        return "/index";
    }
}
