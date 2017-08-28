package pw.highprophet.ssocenter.web.repository;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pw.highprophet.ssocenter.web.pojo.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HighProphet945 on 2017/7/28.
 */
@Repository
@Transactional
public class UserHbnRepository extends BaseRepository implements IUserRepository {

    @Override
    public int add(User user) {
        return (int) session().save(user);
    }

    @Override
    public void update(User user) {
        session().update(user);
    }

    @Override
    public User getUserById(int id) {
        return getUserBy("id", id);
    }

    @Override
    public User getUserByName(String name) {
        List<User> result = session()
                .createQuery("from User where name= :name", User.class)
                .setParameter("name", name).list();
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public User getUserBySuid(String suid) {
        return getUserBy("suid", suid);
    }

    public User getUserBy(String propertyName, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(propertyName, value);
        return getUsersByParamMap(map).stream().findFirst().orElse(null);
    }

    @Override
    public List<User> getUsersByParamMap(Map<String, Object> params) {
        StringBuilder hqlStr = new StringBuilder("from User where 1=1");
        if (params != null) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                hqlStr.append(" and ").append(e.getKey()).append(" = :").append(e.getKey());
            }
        }
        Query<User> query = session().createQuery(hqlStr.toString(), User.class);
        for (Map.Entry<String, Object> e : params.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }
        return query.list();
    }

    @Override
    public void remove(User user) {
        session().delete(user);
    }

}
