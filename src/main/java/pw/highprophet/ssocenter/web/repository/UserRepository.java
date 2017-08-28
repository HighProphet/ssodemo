package pw.highprophet.ssocenter.web.repository;

import org.springframework.stereotype.Component;
import pw.highprophet.ssocenter.web.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HighProphet945 on 2017/7/26.
 */
@Component
public class UserRepository {

    private List<User> users;
    private int count;

    public UserRepository() {
        users = new ArrayList<>();
        User user = new User();
        user.setId(0);
        user.setName("prophet");
        user.setPassword("1234");
        users.add(user);
        count = users.size();
    }

    public void add(User user) {
        user.setId(count++);
        users.add(user);
    }


    public User findByNameAndPwd(String name, String pwd) {
        return users.stream().filter((user -> user.getName().equals(name) && user.getPassword().equals(pwd))).findFirst().orElse(null);
    }


    public User findByName(String name) {
        return users.stream().filter((u) -> u.getName().equals(name)).findFirst().orElse(null);
    }

}
