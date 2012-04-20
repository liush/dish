package com.dish.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-10
 * Time: 下午10:36
 * To change this template use File | Settings | File Templates.
 */
public class UserLog {

    private String userName;
    private String comment;
    private long time;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
