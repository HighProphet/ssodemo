package pw.highprophet.ssocenter.web.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

/**
 * Created by HighProphet945 on 2017/8/3.
 */
public abstract class BaseRepository {

    @Inject
    private SessionFactory factory;

    protected Session session() {
        return factory.getCurrentSession();
    }


}
