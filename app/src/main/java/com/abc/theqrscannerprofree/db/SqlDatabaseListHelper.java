package com.abc.theqrscannerprofree.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.dao.DaoCodeForm;
import com.abc.theqrscannerprofree.dao.TheQrDaoCode;
import com.abc.theqrscannerprofree.helper.NamedFactoryHlp;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;

import java.sql.SQLException;
import java.util.Calendar;

public class SqlDatabaseListHelper extends OrmLiteSqliteOpenHelper implements InterfaceConstan {
    private static final String TAG = SqlDatabaseListHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private TheQrDaoCode theQrDaoCode = null;
    private DaoCodeForm daoCodeForm = null;

    public SqlDatabaseListHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TheQrItemCodes.class);
            TableUtils.createTable(connectionSource, ItemFormCode.class);
        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer) {
        try {
            if (oldVer < 2) {
            }
        } catch (Exception e) {
            Log.e(TAG, "error upgrading db " + DATABASE_NAME + "from ver " + oldVer);
            throw new RuntimeException(e);
        }
    }

    public TheQrDaoCode getQRCodeDAO() throws SQLException {
        if (theQrDaoCode == null) {
            theQrDaoCode = new TheQrDaoCode(getConnectionSource(), TheQrItemCodes.class);
        }
        return theQrDaoCode;
    }

    public DaoCodeForm getDaoCodeForm() throws SQLException {
        if (daoCodeForm == null) {
            daoCodeForm = new DaoCodeForm(getConnectionSource(), ItemFormCode.class);
        }
        return daoCodeForm;
    }

    @Override
    public void close() {
        super.close();
        theQrDaoCode = null;
        daoCodeForm = null;
    }

    public void addQRCodeItemDB(boolean isAutoSave, Context context, int type, String callName, String desc) {
        final long date = Calendar.getInstance().getTime().getTime();
        Calendar timeC = Calendar.getInstance();
        String hours = String.valueOf(timeC.get(Calendar.HOUR_OF_DAY));
        if (hours.length() == 1)
            hours = "0" + hours;
        String minutes = String.valueOf(timeC.get(Calendar.MINUTE));
        if (minutes.length() == 1)
            minutes = "0" + minutes;
        String time = hours + ":" + minutes;
        try {
            TheQrItemCodes callRecordItem = new TheQrItemCodes(type, callName, date, time, desc);
            NamedFactoryHlp.getHelper().getQRCodeDAO().create(callRecordItem);
            if (isAutoSave)
                Toast.makeText(context, context.getString(R.string.db_save),
                        Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCodeFormatItemDB(Context context, int number, String name, boolean isActive) {
        try {
            ItemFormCode callRecordItem = new ItemFormCode(number, name, isActive);
            NamedFactoryHlp.getHelper().getDaoCodeForm().create(callRecordItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}