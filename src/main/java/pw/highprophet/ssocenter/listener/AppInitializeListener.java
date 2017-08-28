package pw.highprophet.ssocenter.listener;

import pw.highprophet.ssocenter.web.pojo.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashSet;

import static pw.highprophet.ssocenter.web.common.ConstantPool.BOOTSTRAP_CSS_HREF;
import static pw.highprophet.ssocenter.web.common.ConstantPool.BOOTSTRAP_JS_SRC;

/**
 * Created by HighProphet945 on 2017/7/26.
 */
@WebListener
public class AppInitializeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute(BOOTSTRAP_CSS_HREF, "https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css");
        ctx.setAttribute("bootstrap.css.integrity", "sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u");
        ctx.setAttribute(BOOTSTRAP_JS_SRC, "https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js");
        ctx.setAttribute("bootstrap.js.integrity", "sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa");
        //应用程序级别的在线用户列表,存放了真正登陆的用户资料
        ctx.setAttribute("user.currentSignedOnUserSet", new HashSet<User>());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
