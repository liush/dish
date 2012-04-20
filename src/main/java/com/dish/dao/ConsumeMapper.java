package com.dish.dao;

import com.dish.base.QueryParam;
import com.dish.domain.Consume;
import com.dish.domain.ConsumeDetail;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-3
 * Time: 下午8:49
 */
public interface ConsumeMapper {

    void saveConsume(Consume consume);

    void saveConsumeDetail(ConsumeDetail consumeDetail);

    void updateConsume(Consume consume);

    void deleteConsumeDetail(String id);

    void countPrice(String consumeId);

    Consume getConsume(String id);

    ConsumeDetail getConsumeDetail(String id);

    List<ConsumeDetail> getConsumeDetails(String consumeId);

    void updateConsumeDetails(ConsumeDetail consumeDetail);

    List<Consume> queryConsume(QueryParam queryParam);

    String getTodayMaxOrderNo(Map<String,Long> map);

    long getConsumeCount(QueryParam queryParam);
}
