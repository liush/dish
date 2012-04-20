package com.dish.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-8
 * Time: 下午7:34
 * 日统计表
 */
public class DayStatistics {

    private String id; //uuid
    private long consumeNum; //消费桌数
    private long dishPrice;
    private long waterPrice;
    private long otherPrice;
    private long totalPrice; //应付款金额
    private long payPrice;  //已付款金额

    private long createTime;
    private long updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getConsumeNum() {
        return consumeNum;
    }

    public void setConsumeNum(long consumeNum) {
        this.consumeNum = consumeNum;
    }

    public long getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(long dishPrice) {
        this.dishPrice = dishPrice;
    }

    public long getWaterPrice() {
        return waterPrice;
    }

    public void setWaterPrice(long waterPrice) {
        this.waterPrice = waterPrice;
    }

    public long getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(long otherPrice) {
        this.otherPrice = otherPrice;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(long payPrice) {
        this.payPrice = payPrice;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
