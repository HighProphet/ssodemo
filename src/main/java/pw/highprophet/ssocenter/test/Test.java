package pw.highprophet.ssocenter.test;

import pw.highprophet.ssocenter.web.common.Utils;

/**
 * Created by HighProphet945 on 2017/7/28.
 */
public class Test {

    public static void main(String[] args) {
        Utils utils = Utils.getInstance();
        System.out.println(utils.md5digest(Math.random() + ""));
        System.out.println(utils.md5digest(Math.random() + ""));
        System.out.println(utils.md5digest(Math.random() + ""));
        System.out.println(utils.md5digest(Math.random() + ""));
        System.out.println(utils.md5digest(Math.random() + ""));
        System.out.println(utils.md5digest(Math.random() + ""));

    }
}
