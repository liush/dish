package com.dish.util;

import org.springframework.util.Assert;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-9
 * Time: 下午8:18
 * To change this template use File | Settings | File Templates.
 */
public class TestPriceUtil {

    @Test
    public void testGetPrice() throws Exception {
        Assert.isTrue(PriceUtil.getPrice("200") == 20000);
        Assert.isTrue(PriceUtil.getPrice("200.00") == 20000);
        Assert.isTrue(PriceUtil.getPrice("200.40") == 20040);
        Assert.isTrue(PriceUtil.getPrice("200.96") == 20096);
        Assert.isTrue(PriceUtil.getPrice("200.9") == 20090);
        Assert.isTrue(PriceUtil.getPrice("2,000") == 200000);
        Assert.isTrue(PriceUtil.getPrice("2,000.12") == 200012);
    }
}
