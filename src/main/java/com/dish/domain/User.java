package com.dish.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-3-31
 * Time: 下午9:41
 * To change this template use File | Settings | File Templates.
 */
public class User {

    public final static short CASH = 1;
    public final static short MANAGER = 2;
    public final static short SHOPKEEPER = 3;
    public final static short ADMIN = 9;

    private String userName;   //	用户名
    private transient String password;   //	密码
    private String realName;   //	姓名
    private short userType;   //1：收银员，2：大堂经理，3：店长，9：管理员

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public short getUserType() {
        return userType;
    }

    public void setUserType(short userType) {
        this.userType = userType;
    }
}
