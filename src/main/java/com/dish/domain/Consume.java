package com.dish.domain;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-3
 * Time: 下午8:48
 * To change this template use File | Settings | File Templates.
 */
public class Consume {

    public static final short NO_PAY = 0;
    public static final short PAY = 1;
    public static final short REVERSE_PAY = 2;

    private String id; //uuid
    private String tableNo;
    private int peopleNum;
    private long dishPrice;
    private long waterPrice;
    private long otherPrice;
    private long totalPrice; // 总额
    private short type; // 0 未结算 1，已结算 2，反结算
    private long money; //客户实付款金额
    private double discount = 10.0d; //折扣 默认是10折
    private long actualPrice; //折扣后总额

    private String orderNo;
    private long createTime;
    private long updateTime;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public long getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(long actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    private List<ConsumeDetail> details;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public List<ConsumeDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ConsumeDetail> details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
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
}
