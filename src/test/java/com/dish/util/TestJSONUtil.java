package com.dish.util;

import com.dish.domain.ConsumeDetail;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-8
 * Time: 下午12:13
 * To change this template use File | Settings | File Templates.
 */
public class TestJSONUtil {

    @Test
    public void testParseObject() throws Exception {
        ConsumeDetail detail1 = new ConsumeDetail();
        detail1.setInformationId("1");
        detail1.setCount(1);
        String s = JSONUtil.parseToString(detail1);
        Assert.notNull(s);
        ConsumeDetail detail = JSONUtil.parseJSONObject(s, ConsumeDetail.class);
        Assert.notNull(detail);
        Assert.hasText(detail.getInformationId());
        Assert.isTrue(detail.getCount() != 0);
    }

    @Test
    public void testParseArray() throws Exception {
        ArrayList<ConsumeDetail> consumeDetails = new ArrayList<ConsumeDetail>();
        for (int i = 0; i < 2; i++) {
            ConsumeDetail detail1 = new ConsumeDetail();
            detail1.setInformationId("1");
            detail1.setCount(1);
            consumeDetails.add(detail1);
        }
        String s = JSONUtil.parseToString(consumeDetails);
        Assert.notNull(s);
        List<ConsumeDetail> list = JSONUtil.parseJSONArray(s, ConsumeDetail.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(ConsumeDetail.class);
        Object o = mapper.readValue(s, new TypeReference<List<ConsumeDetail>>() {
        });
        Assert.notNull(list);
        Assert.isTrue(list.size() == 2);
    }

    @Test
    public void testName() throws Exception {
        System.out.println(String.valueOf(System.currentTimeMillis()).length());
        System.out.println(DateUtil.getCurrDayString());
    }
}
