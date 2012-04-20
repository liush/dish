package com.dish.dao;

import com.dish.base.InformationQueryParam;
import com.dish.base.QueryParam;
import com.dish.domain.Information;
import com.dish.domain.Table;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-2
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public interface InformationMapper {

    void saveTable(Table table);

    void saveInformation(Information information);

    List<Table> queryAllTable();

    Table getTable(String tableNo);

    List<Information> queryAllInformation();

    Information getInformation(String informationId);

    List<Information> queryInformation(String q);

    void updateTable(Table table);

    void delInformation(String id);

    List<Information> queryInformations(InformationQueryParam param);

    long getInformationsCount(InformationQueryParam param);

    void delTable(String tableNo);

    void updateInformation(Information information);
}
