package pw.highprophet.ssocenter.web.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HighProphet945 on 2017/7/26.
 */
public class Utils {

    private volatile static Utils instance;

    public static Utils getInstance() {
        synchronized (Utils.class) {
            if (instance == null) {
                instance = new Utils();
            }
        }
        return instance;
    }

    private MessageDigest md;

    private Utils() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断字串(或对象的toString())是否为null或空白.
     *
     * @param str 待判断的值
     * @return 为null或空白字符组成的串则返回true
     */
    public boolean isBlank(Object str) {
        return str == null || str.toString().trim().equals("");
    }

    public String md5digest(String src) {
        byte[] bytes = md.digest(src.getBytes());
        return new BigInteger(1, bytes).toString(16);
    }

    /*
     * 将对象的含值成员变量收集到Map中
     * 会忽略transient修饰的变量以及值为null的变量
     */
    public Map<String, Object> generateQueryMap(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Parameter must not be null!");
        }
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isTransient(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (value != null) {
                        map.put(field.getName(), value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    public static void main(String[] args) {
        System.out.println(Utils.getInstance().md5digest("1" + System.currentTimeMillis()));
        System.out.println(Utils.getInstance().md5digest("2" + System.currentTimeMillis()));
        System.out.println(Utils.getInstance().md5digest("3" + System.currentTimeMillis()));
    }
}
