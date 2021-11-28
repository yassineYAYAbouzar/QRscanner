package com.abc.theqrscannerprofree.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.abc.theqrscannerprofree.db.TheQrItemCodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheQrDaoCode extends BaseDaoImpl<TheQrItemCodes, Integer> {

    private List<TheQrItemCodes> contactCallRecordList = new ArrayList<>();

    public TheQrDaoCode(ConnectionSource connectionSource, Class<TheQrItemCodes> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<TheQrItemCodes> getAllItems() throws SQLException {
        return this.queryForAll();
    }

    public TheQrItemCodes getItem(Integer id) throws SQLException {
        return this.queryForId(id);
    }

    public void deleteItem(int id) throws SQLException {
        DeleteBuilder<TheQrItemCodes, Integer> deleteBuilder = this.deleteBuilder();
        deleteBuilder.where().eq(TheQrItemCodes.NAME_FIELD_ID, id);
        deleteBuilder.delete();
    }

    public  List<TheQrItemCodes> getLastItem() throws SQLException {
        QueryBuilder<TheQrItemCodes, Integer> queryBuilder = queryBuilder();
        queryBuilder.limit(1);
        queryBuilder.orderBy(TheQrItemCodes.NAME_FIELD_ID, false);
        PreparedQuery<TheQrItemCodes> preparedQuery = queryBuilder.prepare();
        List<TheQrItemCodes> itemList = query(preparedQuery);
        return itemList;
    }
}