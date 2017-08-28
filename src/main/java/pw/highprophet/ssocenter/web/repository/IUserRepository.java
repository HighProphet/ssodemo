package pw.highprophet.ssocenter.web.repository;

import pw.highprophet.ssocenter.web.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * Created by HighProphet945 on 2017/7/28.
 */
public interface IUserRepository {

    int add(User user);

    void update(User user);

    User getUserBy(String propertyName, Object value);

    User getUserById(int id);

    User getUserByName(String name);

    List<User> getUsersByParamMap(Map<String, Object> params);

    void remove(User user);


    User getUserBySuid(String suid);
}
