package com.dish.controller;

import com.dish.base.DishConst;
import com.dish.base.InformationQueryParam;
import com.dish.base.UserSessionManager;
import com.dish.base.permisson.UserType;
import com.dish.domain.Information;
import com.dish.domain.Table;
import com.dish.domain.User;
import com.dish.service.InformationService;
import com.dish.service.UserService;
import com.dish.util.JSONUtil;
import com.dish.util.PriceUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-3-30
 * Time: 下午7:52
 * 非管理员后台操作控制层
 */
@Controller
@RequestMapping(value = "/home/*")
public class UserController {

    public static final String USER_EXISTS = "0";
    public static final String TABLE_EXISTS = "0";

    @Autowired
    private UserService userService;

    @Autowired
    private InformationService informationService;

    @RequestMapping(value = "/")
    public String home(ModelMap map) {
        String loginUserName = UserSessionManager.getLoginUserName();
        User user = userService.get(loginUserName);
        if (user == null) {
            throw new RuntimeException("异常登录。");
        }
        map.put("user", user);
        switch (user.getUserType()) {
            case User.CASH: {
                List<Table> tables = informationService.queryAllTable();
                map.put("tables", JSONUtil.parseToString(tables));
                return "cash/home";
            }
            case User.MANAGER: {
                return "manager/home";
            }
            case User.SHOPKEEPER: {
                return "shopkeeper/home";
            }
            case User.ADMIN: {
                List<User> userList = userService.queryAll();
                map.put("users", JSONUtil.parseToString(userList));
                return "admin/home";
            }
        }
        map.clear();
        return "redirect:/";
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String editUser(ModelMap map, @RequestParam(value = "userName", required = false) String userName) {
        if (StringUtils.isNotBlank(userName)) {
            map.put("user", userService.get(userName));
            map.put("isEdit", true);
        }
        return "/admin/editUser";
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createUser(User user, @RequestParam(value = "isEdit", required = false) String isEdit) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (Boolean.valueOf(isEdit)) {
            userService.updateUser(user);
        } else {
            if (userService.exists(user.getUserName())) {
                map.put(DishConst.RESULT, USER_EXISTS);
            } else {
                userService.createUser(user);
            }
        }

        return map;
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/getUsers")
    @ResponseBody
    public List<User> getUsers() {
        return userService.queryAll();
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/delUser/{userName}")
    @ResponseBody
    public boolean delUser(@PathVariable("userName") String userName) {
        return userService.deleteUser(userName);
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tableManager(ModelMap map) {
        List<Table> tables = informationService.queryAllTable();
        map.put("tables", JSONUtil.parseToString(tables));
        return "/admin/tables";
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/getTables")
    @ResponseBody
    public List<Table> getTables(long pageNo) {
        return informationService.queryAllTable();
    }


    @UserType(User.ADMIN)
    @RequestMapping(value = "/addTable", method = RequestMethod.GET)
    public String createTable() {
        return "/admin/addTable";
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/addTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createTable(Table table) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (informationService.isTableExists(table.getTableNo())) {
            map.put(DishConst.RESULT, TABLE_EXISTS);
        } else {
            informationService.createTable(table);
        }
        return map;
    }


    @UserType(User.ADMIN)
    @RequestMapping(value = "/delTable/{tableNo}")
    @ResponseBody
    public String delTable(@PathVariable("tableNo") String tableNo) {
        return String.valueOf(informationService.delTable(tableNo));
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public String informationManager(ModelMap map) {
        return "/admin/information";
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/getInformaations")
    @ResponseBody
    public Page<Information> getInformaations(InformationQueryParam param) {
        return informationService.queryInformation(param);
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/delInformaation/{id}")
    @ResponseBody
    public String delInformation(@PathVariable("id") String id) {
        informationService.delInformation(id);
        return null;
    }


    @UserType(User.ADMIN)
    @RequestMapping(value = "/addInformation", method = RequestMethod.GET)
    public String createInformation() {
        return "/admin/addInformation";
    }

    @UserType(User.ADMIN)
    @RequestMapping(value = "/addInformation", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createInformation(Information information, @RequestParam("price") String price) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (informationService.isInformationExists(information.getNumber())) {
            map.put(DishConst.RESULT, TABLE_EXISTS);
        } else {
            information.setPrice(PriceUtil.getPrice(price));
            informationService.createInformation(information);
        }
        return map;
    }

    @RequestMapping(value = "/getMessage")
    @ResponseBody
    public Map<String, Object> getUserAndTime() {
        String userName = UserSessionManager.getLoginUserName();
        HashMap<String, Object> map = new HashMap<String, Object>();
        User user = userService.get(userName);
        map.put("user", user);
        map.put("time", System.currentTimeMillis());
        return map;
    }

    @RequestMapping(value = "/exit")
    public String exit(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(true);
        session.setAttribute("userName", null);
        return "redirect:/";
    }


}
