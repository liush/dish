package com.dish.controller;

import com.dish.base.DishConst;
import com.dish.base.QueryParam;
import com.dish.base.UserSessionManager;
import com.dish.base.permisson.UserType;
import com.dish.domain.*;
import com.dish.service.ConsumeService;
import com.dish.service.InformationService;
import com.dish.service.UserService;
import com.dish.util.DateUtil;
import com.dish.util.JSONUtil;
import com.dish.util.PriceUtil;
import com.dish.util.PrintUtil;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.accessibility.AccessibleStateSet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-3
 * Time: 下午8:47
 * 消费相关控制层
 */
@Controller
@RequestMapping("/home/*")
public class ConsumeController {


    private static final String TABLE_EXISTS_CONSUME = "0";
    private static final String MONEY_ERROR = "9";
    private static final String MONEY_SMALL = "90";

    @Autowired
    private ConsumeService consumeService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private UserService userService;

    /**
     * 跳转到开单页面.
     *
     * @param map
     * @param tableNo
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/showOrderDish/{tableNo}")
    public String showOrderDish(ModelMap map, @PathVariable("tableNo") String tableNo) {
        map.put("tableNo", tableNo);
        Table table = informationService.getTable(tableNo);
        if (StringUtils.isNotBlank(table.getConsumeId())) {
            map.put(DishConst.RESULT, TABLE_EXISTS_CONSUME);
            return "redirect:/home/";
        } else {
            Consume consume = new Consume();
            String id = UUID.randomUUID().toString();
            consume.setId(id);
            consume.setTableNo(tableNo);
            consumeService.createOrder(consume);
            map.put("id", id);
            return "cash/orderDish";
        }
    }

    /**
     * 调转到单据编辑页面
     *
     * @param map
     * @param consumeId
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/showEditDish/{consumeId}")
    public String showEditDish(ModelMap map, @PathVariable("consumeId") String consumeId) {
        Consume consume = consumeService.getConsume(consumeId, true);
        map.put("consume", consume);
        map.put("details", JSONUtil.parseToString(buildViewByConsumeDetail(consume.getDetails())));
        return "cash/editDish";
    }

    /**
     * 调转到付款页面
     *
     * @param map
     * @param consumeId
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/showPayDish/{consumeId}")
    public String showPayDish(ModelMap map, @PathVariable(value = "consumeId") String consumeId) {
        Consume consume = consumeService.getConsume(consumeId, true);
        map.put("consume", consume);
        map.put("details", JSONUtil.parseToString(buildViewByConsumeDetail(consume.getDetails())));
        return "cash/payDish";
    }

    /**
     * 单据详细页面
     *
     * @param map
     * @param consumeId
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/showConsumeDetails/{consumeId}")
    public String showConsumeDetails(ModelMap map, @PathVariable("consumeId") String consumeId) {
        Consume consume = consumeService.getConsume(consumeId, true);
        map.put("consume", consume);
        map.put("details", JSONUtil.parseToString(buildViewByConsumeDetail(consume.getDetails())));
        return "cash/showConsumeDetails";
    }

    /**
     * 开单
     *
     * @param param
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/orderDish")
    @ResponseBody
    public Map<String, String> orderDish(OrderDishParam param) {
        String consumeId = param.getConsumeId();
        Assert.hasText(consumeId);
        HashMap<String, String> map = new HashMap<String, String>();
        Consume consume = consumeService.getConsume(consumeId);
        consume.setPeopleNum(param.getPeopleNum());
        consume.setTableNo(param.getTableNo());
        consume.setType(Consume.NO_PAY);
        consume.setDishPrice(PriceUtil.getPrice(param.getDishPrice()));
        consume.setWaterPrice(PriceUtil.getPrice(param.getWaterPrice()));
        consume.setOtherPrice(PriceUtil.getPrice(param.getOtherPrice()));
        consume.setTotalPrice(PriceUtil.getPrice(param.getTotalPrice()));
        consume.setActualPrice(PriceUtil.getPrice(param.getActualPrice()));

        consumeService.updateOrder(consume);
        //计算价格。
        map.put(DishConst.RESULT, DishConst.SUCCESS);
        return map;
    }

    private ConsumeDetail buildDetail(String informationId) {
        Information information = informationService.getInformation(informationId);
        ConsumeDetail detail = new ConsumeDetail();
        detail.setInformationId(informationId);
        detail.setId(UUID.randomUUID().toString());
        detail.setName(information.getName());
        detail.setPrice(information.getPrice());
        detail.setType(information.getType());
        detail.setUnit(information.getUnit());
        return detail;
    }

    /**
     * 付款
     *
     * @param param
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/payDish")
    @ResponseBody
    public Map<String, String> payDish(PayDishParam param) {
        HashMap<String, String> map = new HashMap<String, String>();
        String consumeId = param.getConsumeId();
        Consume consume = consumeService.getConsume(consumeId, true);
        consume.setDiscount(Double.valueOf(param.getDiscount()));
        consume.setActualPrice(PriceUtil.getPrice(param.getActualPrice()));
        long money = 0l;
        try {
            money = PriceUtil.getPrice(param.getMoney());
        } catch (NumberFormatException e) {
            //价格输入异常
            map.put(DishConst.RESULT, MONEY_ERROR);
            return map;
        }
        if (money < consume.getActualPrice()) {
            //实付款小于客户应付款。
            map.put(DishConst.RESULT, MONEY_SMALL);
            return map;
        }
        consume.setMoney(money);
        consume.setDishPrice(PriceUtil.getPrice(param.getDishPrice()));
        consume.setWaterPrice(PriceUtil.getPrice(param.getWaterPrice()));
        consume.setOtherPrice(PriceUtil.getPrice(param.getOtherPrice()));
        consume.setTotalPrice(PriceUtil.getPrice(param.getTotalPrice()));
        consume.setActualPrice(PriceUtil.getPrice(param.getActualPrice()));
        //更新订单状态
        consume.setType(Consume.PAY);
        consumeService.updateOrder(consume);
        map.put(DishConst.RESULT, DishConst.SUCCESS);
        return map;
    }


    /**
     * 反结账
     *
     * @param consumeId
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/cancelConsume")
    @ResponseBody
    public Map<String, Object> cancelConsume(String consumeId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (consumeService.cancelOrder(consumeId)) {
            map.put(DishConst.RESULT, DishConst.SUCCESS);
        } else {
            map.put(DishConst.RESULT, DishConst.FAIL);
        }
        return map;
    }

    /*查询消费情况相关管理方法*/

    /**
     * 跳转到单据历史页面
     *
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/showConsumes")
    public String showHistory() {
        String userName = UserSessionManager.getLoginUserName();
        User user = userService.get(userName);
        if (User.ADMIN == user.getUserType()) {
            return "admin/showConsume";
        } else {
            return "cash/showConsume";
        }
    }

    /**
     * 查询单据历史
     *
     * @param param
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/getConsume")
    @ResponseBody
    public Page<Consume> getHistory(QueryParam param) {
        String userName = UserSessionManager.getLoginUserName();
        User user = userService.get(userName);
        if (User.ADMIN == user.getUserType()) {
            return consumeService.query(param);
        } else {
            long begin = DateUtil.getCurrDay();
            param.setBeginTime(begin);
            param.setEndTime(begin + DateUtil.DAY_TIME_IN_MILLIS);
            return consumeService.query(param);
        }
    }


    private final static short CREATE = 1;
    private final static short UPDATE = 2;
    private final static short DELETE = 3;

    /**
     * 更新单据明细信息（单条）
     *
     * @param dishParam
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/updateDetails")
    @ResponseBody
    public String updateConsumeDetail(OrderDishParam dishParam) {
        String consumeId = dishParam.getConsumeId();
        Assert.hasText(consumeId);
        String detailId = null;
        switch (dishParam.getType()) {
            case CREATE: {
                String informationId = dishParam.getInformationId();
                ConsumeDetail detail = buildDetail(informationId);
                detail.setConsumeId(consumeId);
                detail.setCount(dishParam.getCount());
                detailId = detail.getId();
                consumeService.saveConsumeDetails(detail);
                break;
            }
            case UPDATE: {
                consumeService.updateCount(dishParam.getDetailId(), dishParam.getCount());
                break;
            }
            case DELETE: {
                consumeService.deleteConsumeDetails(dishParam.getDetailId());
                break;
            }
        }
        consumeService.countMoney(consumeId);
        return detailId;
    }

    /**
     * 获得单据明细信息
     *
     * @param consumeId
     * @return
     */
    @UserType(User.CASH)
    @RequestMapping(value = "/getConsumeDetails")
    @ResponseBody
    public List<DetailView> getConsumeDetails(@RequestParam("consumeId") String consumeId) {
        List<ConsumeDetail> consumeDetails = consumeService.getConsumeDetails(consumeId);
        List<DetailView> views = buildViewByConsumeDetail(consumeDetails);
        return views;
    }


    /**
     * 模糊查找菜品
     *
     * @param q 要查询的值
     * @return
     */
    @RequestMapping(value = "/queryInformation")
    @ResponseBody
    public Map<String, Object> queryInformation(@RequestParam(value = "q", required = false) String q) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(q)) {
            List<Information> informations = informationService.query(q);
            List<DetailView> views = buildViewByInformation(informations);
            map.put("views", views);
        }
        return map;
    }

    @RequestMapping(value = "/turnTable")
    @ResponseBody
    public Map<String, Object> turnTable(@RequestParam("tableNo") String tableNo,
                                         @RequestParam("consumeId") String consumeId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Table table = informationService.getTable(tableNo);
        if (table == null) {
            map.put(DishConst.RESULT, DishConst.FAIL);
            return map;
        }
        if (StringUtils.isBlank(table.getConsumeId())) {
            consumeService.turnTable(consumeId, tableNo);
            map.put(DishConst.RESULT, DishConst.SUCCESS);
        } else {
            map.put(DishConst.RESULT, TABLE_EXISTS_CONSUME);
        }
        return map;
    }

    @RequestMapping(value = "/print")
    public void print(@RequestParam("consumeId") String consumeId
            , HttpServletResponse response) throws IOException, TemplateException {
        Consume consume = consumeService.getConsume(consumeId, true);
        Map<String, Object> view = PrintUtil.createView(consume);
        String s = PrintUtil.buildHtml(view);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(s);
        response.flushBuffer();
    }

    private List<DetailView> buildViewByInformation(List<Information> informations) {
        ArrayList<DetailView> views = new ArrayList<DetailView>();
        for (Information information : informations) {
            DetailView view = new DetailView();
            view.init(information);
            views.add(view);
        }
        return views;
    }

    private List<DetailView> buildViewByConsumeDetail(List<ConsumeDetail> consumeDetails) {
        List<DetailView> views = new ArrayList<DetailView>();
        for (ConsumeDetail consumeDetail : consumeDetails) {
            DetailView view = new DetailView();
            view.init(consumeDetail);
            views.add(view);
        }
        return views;
    }


}
