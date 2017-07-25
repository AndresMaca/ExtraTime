package com.macapps.developer.extratime.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.macapps.developer.extratime.provider.ActionContract.ActionEntry;

/**
 * Created by Developer on 24/7/2017.
 */

public class ActionDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="extratime.db";
    private static final int DATABASE_VERSION=1;
    public ActionDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ACTIONS_TABLE="CREATE TABLE "+ ActionEntry.TABLE_NAME+" ("+ActionEntry._ID
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ ActionEntry.COLUMN_ACTION_ID+" TEXT NOT NULL, "+ ActionEntry.COLUMN_ACTION_LOGO
        +"REAL NOT NULL,"+ ActionEntry.COLUMN_ACTION_HOURS+"REAL NOT NULL,"+"UNIQUE ("+
                ActionEntry.COLUMN_ACTION_ID+ ") ON CONFLICT REPLACE"+"); ";
        db.execSQL(SQL_CREATE_ACTIONS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //TODO hacer un upgrade mejor sin soltar y ya
        db.execSQL("DROP TABLE IF EXISTS "+ActionEntry.TABLE_NAME);
        onCreate(db);


    }
}
