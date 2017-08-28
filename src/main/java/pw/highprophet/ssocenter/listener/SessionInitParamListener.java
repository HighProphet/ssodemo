package pw.highprophet.ssocenter.listener;

import pw.highprophet.ssocenter.web.common.ConstantPool;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by HighProphet945 on 2017/7/27.
 */
@WebListener
public class SessionInitParamListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(ConstantPool.IS_USER_LOG_IN, false);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
