package com.dish.base;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-3-30
 * Time: 下午7:56
 * 用户会话管理
 */
public class UserSessionManager {

    private final static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static String getLoginUserName() {
        return threadLocal.get();
    }

    static void setLoginUserName(String userName) {
        threadLocal.set(userName);
    }

    static void clear() {
        threadLocal.remove();
    }
}
