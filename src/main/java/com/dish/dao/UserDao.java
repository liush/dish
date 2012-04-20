package com.dish.dao;

import com.dish.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-1
 * Time: 下午8:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public User getUser(String userName) {
        return userMapper.get(userName);
    }

    public void createUser(User user) {
        userMapper.save(user);
    }

    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    public void delUser(String userName) {
        userMapper.delUser(userName);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }
}
