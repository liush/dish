package com.dish.controller;

import com.dish.base.DishConst;
import com.dish.base.UserSessionManager;
import com.dish.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-3-29
 * Time: 下午7:23
 * 网站前台引导控制层
 */
@Controller
public class IndexController {

    private static final String USER_NAME_ERROR = "0";
    private static final String PASSWOR_ERROR = "1";
    private static final String VERIFY_ERROR = "9";
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "userName") String userName,
                        @RequestParam(value = "password") String password,
                        ModelMap map,HttpServletRequest request) {

        if (StringUtils.isBlank(userName)) {
            map.put(DishConst.RESULT, USER_NAME_ERROR);
        }
        if (StringUtils.isBlank(password)) {
            map.put(DishConst.RESULT, PASSWOR_ERROR);
        }

        if (userService.verify(userName, password)) {
            request.getSession(true).setAttribute("userName", userName);
            return "redirect:/home/";
        } else {
            map.put(DishConst.RESULT, VERIFY_ERROR);
        }

        return "index";
    }

}
