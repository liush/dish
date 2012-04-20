package com.dish.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-14
 * Time: 下午2:22
 * 明细视图类
 */
public class DetailView {

    private String detailId;
    private String consumeId;
    private String informationId;
    private int count;
    private String name;
    private long price; //价格 单位：分
    private short type;
    private String unit;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId;
    }

    public String getInformationId() {
        return informationId;
    }

    public void setInformationId(String informationId) {
        this.informationId = informationId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void init(Information information) {
        this.informationId = information.getId();
        this.name = information.getName();
        this.price = information.getPrice();
        this.unit = information.getUnit();
        this.type = information.getType();
    }

    public void init(ConsumeDetail consumeDetail) {
        this.informationId = consumeDetail.getInformationId();
        this.detailId = consumeDetail.getId();
        this.count = consumeDetail.getCount();
        this.consumeId = consumeDetail.getConsumeId();
        this.name = consumeDetail.getName();
        this.price = consumeDetail.getPrice();
        this.unit = consumeDetail.getUnit();
        this.type = consumeDetail.getType();
    }
}
