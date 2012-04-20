package com.dish.dao;

import com.dish.base.DishConst;
import com.dish.base.InformationQueryParam;
import com.dish.base.QueryParam;
import com.dish.controller.Page;
import com.dish.domain.Information;
import com.dish.domain.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-2
 * Time: 上午10:35
 * 对基本信息的一些DB操作
 */
@Repository
public class InformationDao {

    @Autowired
    private InformationMapper informationMapper;

    public void save(Table table) {
        informationMapper.saveTable(table);
    }

    public void save(Information information) {
        informationMapper.saveInformation(information);
    }

    public List<Table> queryAllTable() {
        return informationMapper.queryAllTable();
    }

    public Table getTable(String tableNo) {
        return informationMapper.getTable(tableNo);
    }

    public List<Information> queryAllInformation() {
        return informationMapper.queryAllInformation();
    }

    public Information getInformation(String informationId) {
        return informationMapper.getInformation(informationId);
    }

    public List<Information> query(String q) {
        return informationMapper.queryInformation(q + "%");
    }

    public void updateTable(Table table){
        informationMapper.updateTable(table);
    }

    public void updateInformation(Information information){
        informationMapper.updateInformation(information);
    }

    public void delInformation(String id) {
        informationMapper.delInformation(id);
    }

    public Page<Information> queryInformation(InformationQueryParam param) {
        Page<Information> page = new Page<Information>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(DishConst.PAGE_SIZE);
        page.setResults(informationMapper.queryInformations(param));
        page.setTotalNum(informationMapper.getInformationsCount(param));
        return page;
    }

    public void delTable(String tableNo) {
        informationMapper.delTable(tableNo);
    }
}
