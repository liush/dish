package com.dish.dao;


import com.dish.base.DishConst;
import com.dish.base.QueryParam;
import com.dish.controller.Page;
import com.dish.domain.Consume;
import com.dish.domain.ConsumeDetail;
import com.dish.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-3
 * Time: 下午8:49
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ConsumeDao {

    @Autowired
    private ConsumeMapper consumeMapper;

    public void saveConsume(Consume consume) {
        long time = System.currentTimeMillis();
        consume.setOrderNo(getNextOrderNO());
        consume.setCreateTime(time);
        consume.setUpdateTime(time);
        consumeMapper.saveConsume(consume);
    }

    private String getNextOrderNO() {
        long today = DateUtil.getCurrDay();
        long tomorrow = today + DateUtil.DAY_TIME_IN_MILLIS;
        HashMap<String, Long> map = new HashMap<String, Long>();
        map.put("today", today);
        map.put("tomorrow", tomorrow);
        String todayMaxOrderNo = consumeMapper.getTodayMaxOrderNo(map);
        if (StringUtils.isBlank(todayMaxOrderNo)) {
            return DateUtil.getCurrDayString()+"001";
        } else {
            return String.valueOf(Long.valueOf(todayMaxOrderNo) + 1);
        }
    }

    public void saveConsumeDetail(ConsumeDetail consumeDetail) {
        consumeMapper.saveConsumeDetail(consumeDetail);
    }

    public void updateConsume(Consume consume) {
        long time = System.currentTimeMillis();
        consume.setUpdateTime(time);
        consumeMapper.updateConsume(consume);
    }

    public void deleteConsumeDetail(String id) {
        consumeMapper.deleteConsumeDetail(id);
    }

    /**
     * 计算价格
     *
     * @param consumeId 消费表ID
     */
    public void countPrice(String consumeId) {
        consumeMapper.countPrice(consumeId);
    }

    public Consume getConsume(String id) {
        return consumeMapper.getConsume(id);
    }

    public ConsumeDetail getConsumeDetail(String id) {
        return consumeMapper.getConsumeDetail(id);
    }

    public List<ConsumeDetail> getConsumeDetails(String consumeId) {
        return consumeMapper.getConsumeDetails(consumeId);
    }

    public void updateConsumeDetails(ConsumeDetail consumeDetail) {
        consumeMapper.updateConsumeDetails(consumeDetail);
    }

    public Page<Consume> queryConsume(QueryParam queryParam) {
        Page<Consume> page = new Page<Consume>();
        page.setPageNo(queryParam.getPageNo());
        page.setPageSize(DishConst.PAGE_SIZE);
        page.setResults(consumeMapper.queryConsume(queryParam));
        page.setTotalNum(consumeMapper.getConsumeCount(queryParam));
        return page;
    }
}
