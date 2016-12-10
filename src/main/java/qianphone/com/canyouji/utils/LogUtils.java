package qianphone.com.canyouji.utils;

/**
 * Created by Admin on 2016/12/6.
 */

public class LogUtils {

    private static final boolean DEBUG = true;

    public static void print(String info) {
        if (DEBUG) {
            System.out.println(info);
        }
    }

}
