package com.dish.service;

import com.dish.dao.UserDao;
import com.dish.domain.User;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-1
 * Time: 下午10:20
 * To change this template use File | Settings | File Templates.
 */
public class TestUserService{

    private UserService userService;

    @BeforeMethod
    public void setUp() throws Exception {
        userService = new UserService();
    }

    @Test
    public void testVerity() throws Exception {
        String usreName = "liush";
        String password = "123456";
        User user = new User();
        user.setUserName(usreName);
        Method sha1 = UserService.class.getDeclaredMethod("sha1", String.class);
        sha1.setAccessible(true);
        user.setPassword((String) sha1.invoke(userService, password));

        UserDao userDao = Mockito.mock(UserDao.class);
        Mockito.when(userDao.getUser(usreName)).thenReturn(user);
        userService.setUserDao(userDao);

        Assert.isTrue(userService.verify(usreName, password));
    }

    @Test
    public void test() throws Exception {
        System.out.println(UUID.randomUUID().toString().length());
    }
}
