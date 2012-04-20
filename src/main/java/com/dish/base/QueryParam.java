package com.dish.base;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-8
 * Time: 下午4:06
 * 查询参数
 */
public class QueryParam {

    private long beginTime; //开始时间
    private long endTime;   //结束时间

    private long pageNo = 1;

    private long begin;
    private long end;

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getBegin() {
        return (pageNo - 1) * DishConst.PAGE_SIZE;
    }

    public long getEnd() {
        return DishConst.PAGE_SIZE;
    }

}
