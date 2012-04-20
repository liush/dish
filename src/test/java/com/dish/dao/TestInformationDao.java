package com.dish.dao;

import com.dish.domain.Information;
import com.dish.domain.Table;
import com.dish.util.SpellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-4
 * Time: 下午5:25
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(value = "classpath:information.xml")
public class TestInformationDao extends AbstractTestNGSpringContextTests {

    @Autowired
    private InformationDao informationDao;

    @Test
    public void testName() throws Exception {
        List<Information> query = informationDao.query("1");
        Assert.notEmpty(query);
    }

}
