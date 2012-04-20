package com.dish.util;

import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-15
 * Time: 下午11:49
 * To change this template use File | Settings | File Templates.
 */
public class TestDateUtil {
    @Test
    public void testName() throws Exception {
        System.out.println(DateUtil.getCurrTimeString());
    }
}
