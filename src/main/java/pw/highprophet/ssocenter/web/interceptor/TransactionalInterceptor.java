package pw.highprophet.ssocenter.web.interceptor;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by HighProphet945 on 2017/8/1.
 */
@Component
public class TransactionalInterceptor extends HandlerInterceptorAdapter {

    @Inject
    private SessionFactory factory;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Transaction tx = factory.getCurrentSession().getTransaction();
        if (tx == null || !tx.getStatus().equals(TransactionStatus.ACTIVE)) {
            factory.getCurrentSession().beginTransaction();
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Transaction tx = factory.getCurrentSession().getTransaction();
        if (tx != null && tx.getStatus().equals(TransactionStatus.ACTIVE)) {
            tx.commit();
        }
    }
}
