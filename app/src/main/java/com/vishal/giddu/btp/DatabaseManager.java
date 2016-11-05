package com.vishal.giddu.btp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by giddu on 8/9/16.
 */

public class DatabaseManager {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_UNIQUE_ID = "unique_id";
    public static final String KEY_SITE_ID = "site_id";
    public static final String KEY_SITE_LOCATION = "site_location";
    public static final String KEY_PARAMETER = "parameter";
    public static final String KEY_UPDATE_STATUS = "update_status";

    private static final String DATBASE_NAME = "mydb";
    private static final String DATABASE_TABLE = "mytable";
    private static final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DATBASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_UNIQUE_ID + " TEXT NOT NULL, " +
                    KEY_SITE_ID + " TEXT NOT NULL, " +
                    KEY_SITE_LOCATION + " TEXT NOT NULL, " +
                    KEY_PARAMETER + " TEXT NOT NULL, " +
                    KEY_UPDATE_STATUS + " TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public DatabaseManager(Context c) {
        ourContext = c;
    }

    public DatabaseManager open() {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public long createEntry(String uniqueID, String siteID, String siteLocation, String parameter) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_UNIQUE_ID, uniqueID);
        cv.put(KEY_SITE_ID, siteID);
        cv.put(KEY_SITE_LOCATION, siteLocation);
        cv.put(KEY_PARAMETER, parameter);
        cv.put(KEY_UPDATE_STATUS, "no");

        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public RowElement[] getData() {
        String[] columns = new String[]{KEY_ROWID, KEY_UNIQUE_ID, KEY_SITE_ID, KEY_SITE_LOCATION, KEY_PARAMETER};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        //for return array
        RowElement[] result = new RowElement[c.getCount()];
        Log.d("Size", "" + c.getCount());
        int i = 0;

        //int iRow = c.getColumnIndex(KEY_ROWID);
        int iUniqueID = c.getColumnIndex(KEY_UNIQUE_ID);
        int iSiteID = c.getColumnIndex(KEY_SITE_ID);
        int iSiteLocation = c.getColumnIndex(KEY_SITE_LOCATION);
        int iParameter = c.getColumnIndex(KEY_PARAMETER);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            result[i] = new RowElement();

            result[i].setUniqueID(c.getString(iUniqueID));
            result[i].setSiteID(c.getString(iSiteID));
            result[i].setSiteLocation(c.getString(iSiteLocation));
            result[i].setParamter(c.getString(iParameter));

            i++;
        }

        return result;
    }

    //useless right now
    //need to open database before calling this function and close after use
    public ArrayList<HashMap<String, String>> getAllEntry() {

        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();

        String[] columns = new String[]{KEY_ROWID, KEY_UNIQUE_ID, KEY_SITE_ID, KEY_SITE_LOCATION, KEY_PARAMETER};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        Log.d("Size", "" + c.getCount());

        //int iRow = c.getColumnIndex(KEY_ROWID);
        int iUniqueID = c.getColumnIndex(KEY_UNIQUE_ID);
        int iSiteID = c.getColumnIndex(KEY_SITE_ID);
        int iSiteLocation = c.getColumnIndex(KEY_SITE_LOCATION);
        int iParameter = c.getColumnIndex(KEY_PARAMETER);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("unique_id", c.getString(iUniqueID));
            map.put("site_id", c.getString(iSiteID));
            map.put("site_location", c.getString(iSiteLocation));
            map.put("parameter", c.getString(iParameter));

            wordList.add(map);
        }

        return wordList;
    }

    /**
     * Compose JSON out of SQLite records
     *
     * @return
     */
    public String composeJSONfromSQLite() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE + " where " + KEY_UPDATE_STATUS + " = 'no'";
        Cursor c = ourDatabase.rawQuery(selectQuery, null);

        int iUniqueID = c.getColumnIndex(KEY_UNIQUE_ID);
        int iSiteID = c.getColumnIndex(KEY_SITE_ID);
        int iSiteLocation = c.getColumnIndex(KEY_SITE_LOCATION);
        int iParameter = c.getColumnIndex(KEY_PARAMETER);

        if (c.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("unique_id", c.getString(iUniqueID));
                map.put("site_id", c.getString(iSiteID));
                map.put("site_location", c.getString(iSiteLocation));
                map.put("parameter", c.getString(iParameter));
                wordList.add(map);
            } while (c.moveToNext());
        }

        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    /**
     * Get Sync status of SQLite
     *
     * @return
     */
    public String getSyncStatus() {
        String msg = null;
        if (this.dbSyncCount() == 0) {
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        } else {
            msg = "DB Sync needed";
        }
        return msg;
    }

    /**
     * Get SQLite records that are yet to be Synced
     *
     * @return
     */
    public int dbSyncCount() {
        int count = 0;
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE + " where " + KEY_UPDATE_STATUS + " = 'no'";
        Cursor cursor = ourDatabase.rawQuery(selectQuery, null);
        count = cursor.getCount();
        return count;
    }

    /**
     * Update Sync status against each User ID
     *
     * @param UniqueId
     * @param status
     */
    public void updateSyncStatus(String UniqueId, String status) {
        String updateQuery = "UPDATE " + DATABASE_TABLE + " SET " + KEY_UPDATE_STATUS + " = '" + status +
                "' WHERE " + KEY_UNIQUE_ID + " = '" + UniqueId + "'";
        Log.d("query", updateQuery);
        ourDatabase.execSQL(updateQuery);
    }

    public void updateEntry(String UniqueId, String SiteID, String SiteLocation, String Parameter) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_UNIQUE_ID, UniqueId);
        cv.put(KEY_SITE_ID, SiteID);
        cv.put(KEY_SITE_LOCATION, SiteLocation);
        cv.put(KEY_PARAMETER, Parameter);
        cv.put(KEY_UPDATE_STATUS, "no");

        ourDatabase.update(DATABASE_TABLE, cv, KEY_UNIQUE_ID + "=" + UniqueId, null);
    }


}
