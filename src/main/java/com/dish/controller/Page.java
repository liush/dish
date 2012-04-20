package com.dish.controller;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-8
 * Time: 下午6:48
 * To change this template use File | Settings | File Templates.
 */
public class Page<T> {
    private long pageNo;
    private long pageSize;
    private long totalPage;
    private long totalNum;
    private List<T> results;

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public long getTotalPage() {
        return ((totalNum - 1) / pageSize) + 1;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
