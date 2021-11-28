package com.abc.theqrscannerprofree.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.abc.theqrscannerprofree.db.ItemFormCode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoCodeForm extends BaseDaoImpl<ItemFormCode, Integer> {

    private List<ItemFormCode> contactCallRecordList = new ArrayList<>();

    public DaoCodeForm(ConnectionSource connectionSource, Class<ItemFormCode> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<ItemFormCode> getAllItems() throws SQLException {
        return this.queryForAll();
    }


}