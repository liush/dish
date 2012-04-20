package com.dish.base.permisson;

import com.dish.base.UserSessionManager;
import com.dish.dao.UserLogMapper;
import com.dish.domain.User;
import com.dish.service.ConsumeService;
import com.dish.service.InformationService;
import com.dish.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-3
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
@Aspect
public class PermissionAspectj {

    @Autowired
    private UserService userService;

    @Autowired
    private ConsumeService consumeService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private UserLogMapper userLogMapper;

    @Before(value = "@annotation(type)")
    public void userTypePermission(UserType type) {
        String userName = UserSessionManager.getLoginUserName();
        User user = userService.get(userName);
        short userType = user.getUserType();
        if (!(userType == User.ADMIN || userType == type.value())) {
            throw new RuntimeException("没有权限。");
        }
    }

    @After(value = "@annotation(userLog)")
    public void saveUserLog(JoinPoint point, UserLog userLog) throws Throwable {
        Object[] args = point.getArgs();
        System.out.println(args);
    }
}
