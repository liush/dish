package com.dish.service;

import com.dish.base.DishConst;
import com.dish.dao.UserDao;
import com.dish.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-3-30
 * Time: 下午7:36
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserService {


    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean verify(String userName, String password) {
        Assert.hasText(userName, "用户名不能为空。");
        Assert.hasText(password, "密码不能为空。");
        User user = userDao.getUser(userName);
        return user.getPassword().equals(sha1(password));
    }

    public User get(String userName) {
        Assert.hasText(userName, "用户名不能为空。");
        return userDao.getUser(userName);
    }

    /**
     * 创建用户
     *
     * @param user password字段仅需入参页面传入值
     */
    public void createUser(User user) {
        Assert.notNull(user);
        Assert.hasText(user.getUserName());
        Assert.hasText(user.getPassword());
        Assert.hasText(user.getRealName());
        user.setPassword(sha1(user.getPassword()));
        userDao.createUser(user);
    }

    /**
     * 对入参的密码进行加密后比较
     *
     * @param password
     */
    private String sha1(String password) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            byte[] digest = instance.digest(password.getBytes());
            StringBuilder builder = new StringBuilder();
            for (byte b : digest) {
                String s = Integer.toHexString(b & 0xFF);
                if (s.length() == 1) {
                    builder.append(DishConst.ZERO_STR);
                }
                builder.append(s);
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> queryAll() {
        return userDao.queryAll();
    }

    public boolean exists(String userName) {
        User user = userDao.getUser(userName);
        return user != null;
    }

    public boolean deleteUser(String userName) {
        User user = userDao.getUser(userName);
        if(user.getUserType() == User.ADMIN){
            return false;
        }
        userDao.delUser(userName);
        return true;
    }

    public void updateUser(User user) {
        Assert.notNull(user);
        Assert.hasText(user.getUserName());
        Assert.hasText(user.getPassword());
        Assert.hasText(user.getRealName());
        user.setPassword(sha1(user.getPassword()));
        userDao.updateUser(user);
    }
}
