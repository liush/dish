package com.dish.base;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-15
 * Time: 上午1:52
 * To change this template use File | Settings | File Templates.
 */
public class InformationQueryParam extends QueryParam {

    private String name;

    public String getName() {
        return DishConst.LIKE+name+DishConst.LIKE;
    }

    public void setName(String name) {
        this.name = name;
    }
}
