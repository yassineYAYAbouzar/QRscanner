package com.abc.theqrscannerprofree.helper;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.abc.theqrscannerprofree.db.SqlDatabaseListHelper;


public class NamedFactoryHlp {
    private static SqlDatabaseListHelper sqlDatabaseListHelper;

    public static SqlDatabaseListHelper getHelper(){
        return sqlDatabaseListHelper;
    }
    public static void setHelper(Context context){
        sqlDatabaseListHelper = OpenHelperManager.getHelper(context, SqlDatabaseListHelper.class);
    }
    public static void releaseHelper(){
        OpenHelperManager.releaseHelper();
        sqlDatabaseListHelper = null;
    }
}
