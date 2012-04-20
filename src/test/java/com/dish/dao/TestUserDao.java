package com.dish.dao;

import com.dish.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-1
 * Time: 下午9:34
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(value = "classpath:user.xml")
public class TestUserDao extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void testGetUser() throws Exception {
        String userName = "liush";
        User user = userDao.getUser(userName);
        if(user == null){
            user = new User();
            user.setUserName(userName);
            user.setPassword("123456");
            user.setRealName("测试");
            user.setUserType(User.CASH);
            userDao.createUser(user);
            Assert.notNull(userDao.getUser(userName));
        }

    }


}
