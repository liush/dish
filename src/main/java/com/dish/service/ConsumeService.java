package com.dish.service;

import com.dish.base.QueryParam;
import com.dish.base.permisson.Log;
import com.dish.base.permisson.UserLog;
import com.dish.controller.Page;
import com.dish.dao.ConsumeDao;
import com.dish.domain.Consume;
import com.dish.domain.ConsumeDetail;
import com.dish.domain.Table;
import com.dish.exception.InputMoneyErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.accessibility.AccessibleStateSet;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-3
 * Time: 下午8:48
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ConsumeService {

    @Autowired
    private ConsumeDao consumeDao;

    @Autowired
    private InformationService informationService;

    /**
     * 餐桌开单
     *
     * @param consume
     */
    @UserLog(Log.CREATE_ORDER)
    public void createOrder(Consume consume) {
        Assert.hasText(consume.getId());
        informationService.updateTableConsume(consume.getTableNo(), consume.getId(), consume.getPeopleNum());
        consumeDao.saveConsume(consume);
    }

    /**
     * 更新订单信息
     *
     * @param consume
     */
    public void updateOrder(Consume consume) {
        Assert.hasText(consume.getId());
        String tableNo = consume.getTableNo();
        if (consume.getType() == Consume.PAY) {
            informationService.updateTableConsume(tableNo, "", 0);
        } else {
            informationService.updateTableConsume(tableNo, consume.getId(), consume.getPeopleNum());
        }
        consumeDao.updateConsume(consume);
    }

    /**
     * 转台，没有对目标桌进行判断，是否有人正在用餐
     * 调用前请先判断
     * @param consumeId
     * @param tableNo
     */
    public void turnTable(String consumeId,String tableNo){
        Consume consume = getConsume(consumeId);
        informationService.updateTableConsume(consume.getTableNo(), "", 0);
        informationService.updateTableConsume(tableNo,consumeId,consume.getPeopleNum());
        consume.setTableNo(tableNo);
        updateOrder(consume);
    }

    public ConsumeDetail getConsumeDetail(String id) {
        return consumeDao.getConsumeDetail(id);
    }

    public List<ConsumeDetail> getConsumeDetails(String consumeId) {
        return consumeDao.getConsumeDetails(consumeId);
    }

    public Consume getConsume(String id) {
        return getConsume(id, false);
    }

    public Consume getConsume(String id, boolean needDetails) {
        Consume consume = consumeDao.getConsume(id);
        if (needDetails) {
            List<ConsumeDetail> consumeDetails = consumeDao.getConsumeDetails(id);
            consume.setDetails(consumeDetails);
        }
        return consume;
    }


    public void deleteConsumeDetails(String id) {
        consumeDao.deleteConsumeDetail(id);
    }

    public void updateCount(String id, String count) {
        ConsumeDetail consumeDetail = consumeDao.getConsumeDetail(id);
        consumeDetail.setCount(count);
        consumeDetail.setUpdateTime(System.currentTimeMillis());
        consumeDao.updateConsumeDetails(consumeDetail);
        this.countMoney(consumeDetail.getConsumeId());
    }

    public void saveConsumeDetails(ConsumeDetail detail) {
        long time = System.currentTimeMillis();
        detail.setCreateTime(time);
        detail.setUpdateTime(time);
        consumeDao.saveConsumeDetail(detail);
        this.countMoney(detail.getConsumeId());
    }

    public void countMoney(String consumeId) {
        consumeDao.countPrice(consumeId);
    }

    public Page<Consume> query(QueryParam param) {
        return consumeDao.queryConsume(param);
    }

    /**
     * 反结算单据
     * @param consumeId
     * @return
     */
    public boolean cancelOrder(String consumeId) {
        Consume consume = consumeDao.getConsume(consumeId);
        if(consume.getType() != Consume.PAY){
            throw new RuntimeException("非法请求。");
        }
        String tableNo = consume.getTableNo();
        Table table = informationService.getTable(tableNo);
        if(StringUtils.isBlank(table.getConsumeId())){
            consume.setType(Consume.REVERSE_PAY);
            consumeDao.updateConsume(consume);
            informationService.updateTableConsume(tableNo,consumeId,consume.getPeopleNum());
            return true;
        }
        return false;
    }
}
