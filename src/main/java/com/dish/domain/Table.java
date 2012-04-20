package com.dish.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-2
 * Time: 上午10:27
 * To change this template use File | Settings | File Templates.
 */
public class Table {
    private String tableNo;
    private int peopleNum; //就餐人数
    private String consumeId="";

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }
}
