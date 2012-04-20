package com.dish.dao;

import com.dish.base.QueryParam;
import com.dish.controller.Page;
import com.dish.domain.Consume;
import com.dish.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-4
 * Time: 下午5:07
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(value = "classpath:consume.xml")
public class TestConsumeDao extends AbstractTestNGSpringContextTests {

    @Autowired
    private ConsumeDao consumeDao;

    @Autowired
    private ConsumeMapper consumeMapper;

    @Test
    public void testGetConsume() throws Exception {
        Consume consume = consumeDao.getConsume("");
        Assert.isNull(consume);
    }

    @Test
    public void testCreate() throws Exception {
        Consume consume = new Consume();
        consume.setId(UUID.randomUUID().toString());
        consume.setTableNo("1");
        consume.setPeopleNum(1);
        consumeDao.saveConsume(consume);
    }

    @Test
    public void testQuery() throws Exception {
        QueryParam param = new QueryParam();
        long begin = DateUtil.getCurrDay();
        param.setBeginTime(begin);
        param.setEndTime(begin + DateUtil.DAY_TIME_IN_MILLIS);
        Page<Consume> consumes = consumeDao.queryConsume(param);
        System.out.println(consumes.getPageNo());
    }

    @Test
    public void testMaxOrderNo() throws Exception {
        long today = DateUtil.getCurrDay();
        long tomorrow = today + DateUtil.DAY_TIME_IN_MILLIS;
        HashMap<String, Long> map = new HashMap<String, Long>();
        map.put("today", today);
        map.put("tomorrow", tomorrow);
        String maxOrderNo = consumeMapper.getTodayMaxOrderNo(map);
        System.out.println(maxOrderNo);
    }
}
