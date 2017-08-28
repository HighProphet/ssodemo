package pw.highprophet.ssocenter.web.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by HighProphet945 on 2017/8/3.
 */
@Entity
@Table(name = "user")
public class User {
    private Integer id;
    private String name;
    private String password;
    private String role;
    private Date createTime;
    private String suid;
    private transient String accessToken;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 24)
    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 16)
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Basic
    @Column(name = "role", nullable = true, length = 10)
    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    @Basic
    @Column(name = "suid", nullable = false, length = 32)
    public String getSuid() {
        return suid;
    }

    public User setSuid(String suid) {
        this.suid = suid;
        return this;
    }

    @Transient
    public String getAccessToken() {
        return accessToken;
    }

    public User setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        if (createTime != null ? !createTime.equals(user.createTime) : user.createTime != null) return false;
        if (suid != null ? !suid.equals(user.suid) : user.suid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (suid != null ? suid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"role\":\"" + role + '\"' +
                ", \"createTime\":\"" + createTime + '\"' +
                ", \"suid\":\"" + suid + '\"' +
                '}';
    }
}
