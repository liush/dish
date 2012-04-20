package com.dish.service;

import com.dish.base.InformationQueryParam;
import com.dish.controller.Page;
import com.dish.util.SpellUtil;
import com.dish.dao.InformationDao;
import com.dish.domain.Information;
import com.dish.domain.Table;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-2
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
@Service
public class InformationService {

    @Autowired
    private InformationDao informationDao;


    public void createTable(Table table) {
        Assert.notNull(table);
        Assert.hasText(table.getTableNo());
        informationDao.save(table);
    }


    public void createInformation(Information information) {
        Assert.notNull(information);
        String name = information.getName();
        Assert.hasText(name);
        information.setSpell(SpellUtil.getFirstLetter(name));
        information.setId(UUID.randomUUID().toString());
        informationDao.save(information);
    }


    public List<Table> queryAllTable() {
        return informationDao.queryAllTable();
    }

    public boolean isTableExists(String tableNo) {
        Table table = informationDao.getTable(tableNo);
        return table != null;
    }

    public Table getTable(String tableNo){
        return informationDao.getTable(tableNo);
    }

    public void updateTableConsume(String tableNo,String consumeId,int peopleNum){
        Table table = informationDao.getTable(tableNo);
        table.setConsumeId(consumeId);
        table.setPeopleNum(peopleNum);
        informationDao.updateTable(table);
    }

    public Information getInformation(String id){
        return informationDao.getInformation(id);
    }

    public List<Information> queryAllInformation() {
        return informationDao.queryAllInformation();
    }

    public List<Information> query(String q) {
        Assert.hasText(q);
        return informationDao.query(q);
    }

    public boolean isInformationExists(String number) {
        return informationDao.getInformation(number) != null;
    }

    public void delInformation(String id) {
        informationDao.delInformation(id);
    }

    public Page<Information> queryInformation(InformationQueryParam param) {
        return informationDao.queryInformation(param);
    }

    public boolean delTable(String tableNo) {
        Table table = informationDao.getTable(tableNo);
        if(StringUtils.isNotBlank(table.getConsumeId())){
            return false;
        }else {
            informationDao.delTable(tableNo);
            return true;
        }
    }
}
