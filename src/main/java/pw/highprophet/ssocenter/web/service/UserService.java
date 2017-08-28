package pw.highprophet.ssocenter.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.highprophet.ssocenter.web.common.Utils;
import pw.highprophet.ssocenter.web.pojo.User;
import pw.highprophet.ssocenter.web.repository.IUserRepository;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static pw.highprophet.ssocenter.web.common.ConstantPool.*;

/**
 * Created by HighProphet945 on 2017/7/28.
 */
@Service
public class UserService {

    @Autowired
    private IUserRepository repo;

    @Autowired
    private Utils utils;

    private Set<User> users;

    @Autowired
    @SuppressWarnings("unchecked")
    public UserService(ServletContext context) {
        this.users = (Set<User>) context.getAttribute("user.currentSignedOnUserSet");
    }

    public boolean doesUserExist(String name) {
        return getUserByName(name) != null;
    }

    public User validateAndGetUser(User user) {
        List<User> result = repo.getUsersByParamMap(utils.generateQueryMap(user));
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    public User getUserByName(String name) {
        return repo.getUserByName(name);
    }

    public void createUser(User user) {
        user.setSuid(utils.md5digest(user.getName() + System.currentTimeMillis()));
        user.setCreateTime(new Date());
        user.setId(repo.add(user));
    }

    public User signOn(String suid, HttpSession session) {
        User user = getUserBySuid(suid);
        //生成accessToken
        String token = utils.md5digest(user.getName() + System.currentTimeMillis());
        user.setAccessToken(token);
        //将用户的相关信息保存到session中
        session.setAttribute(IS_USER_LOG_IN, true);
        session.setAttribute(SSO_USER_IDENTIFIER, user.getSuid());
        session.setAttribute(SSO_USER_ACCESS_TOKEN, token);
        session.setAttribute(CURRENT_USER, user);
        session.setMaxInactiveInterval(MAX_SESSION_INACTIVE_INTERVAL);
        User previousLoggedUser = users.stream().filter(u -> u.getSuid().equals(user.getSuid())).findFirst().orElse(null);
        if (previousLoggedUser != null) {
            previousLoggedUser.setAccessToken(token);
            return previousLoggedUser;
        }
        users.add(user);
        return user;
    }

    public boolean isSuidAndTokenValid(String suid, String token) {
        return users.stream()
                .filter((u) -> u.getSuid().equals(suid) && u.getAccessToken().equals(token))
                .findFirst().orElse(null) != null;
    }

    public void signOff(String suid, HttpSession session) {
        users.removeIf((u) -> u.getSuid().equals(suid));
        session.setAttribute(IS_USER_LOG_IN, false);
        session.removeAttribute(SSO_USER_IDENTIFIER);
        session.removeAttribute(SSO_USER_ACCESS_TOKEN);
        session.removeAttribute(CURRENT_USER);
    }

    public void recoverSessionSignOn(String suid, String token, HttpSession session) {
        if (isSuidAndTokenValid(suid, token) && !(boolean) session.getAttribute(IS_USER_LOG_IN)) {
            signOn(suid, session);
        }
    }

    public void saveCookie(HttpServletResponse response, String suid, String token) {
        Cookie c1 = new Cookie(SSO_USER_IDENTIFIER, suid);
        Cookie c2 = new Cookie(SSO_USER_ACCESS_TOKEN, token);
        c1.setMaxAge(DEFAULT_COOKIE_MAX_AGE);
        c2.setMaxAge(DEFAULT_COOKIE_MAX_AGE);
        c1.setPath("/ssocenter");
        c2.setPath("/ssocenter");
        response.addCookie(c1);
        response.addCookie(c2);
    }

    public void clearCookie(HttpServletResponse response) {
        Cookie c1 = new Cookie(SSO_USER_IDENTIFIER, null);
        Cookie c2 = new Cookie(SSO_USER_ACCESS_TOKEN, null);
        c1.setMaxAge(0);
        c2.setMaxAge(0);
        c1.setPath("/ssocenter");
        c2.setPath("/ssocenter");
        response.addCookie(c1);
        response.addCookie(c2);
    }

    public User getUserBySuid(String suid) {
        return repo.getUserBySuid(suid);
    }
}
