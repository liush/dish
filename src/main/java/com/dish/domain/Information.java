package com.dish.domain;

import com.dish.util.PriceUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-2
 * Time: 上午10:28
 * 基础信息表
 */
public class Information {

    public final static short DISH = 1;//菜
    public final static short WATER = 2;//酒水
    public final static short OTHER = 3;//其他

    private String id;
    private String name;
    private String number; //编号
    private String spell; //名字的拼音首字母
    private long price; //价格 单位：分
    private short type;
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
