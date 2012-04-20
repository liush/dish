package com.dish.dao;

import com.dish.domain.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-1
 * Time: 下午8:24
 * To change this template use File | Settings | File Templates.
 */
public interface UserMapper {

    User get(String userName);

    void save(User user);

    List<User> queryAll();

    void delUser(String userName);

    void update(User user);
}
