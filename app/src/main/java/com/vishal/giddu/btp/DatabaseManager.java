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
    public static final String KEY_COLOUR = "colour";
    public static final String KEY_ODOUR = "odour";
    public static final String KEY_TEMP = "temp";
    public static final String KEY_PH = "ph";
    public static final String KEY_EC = "ec";
    public static final String KEY_DO = "do";
    public static final String KEY_NO2NO3 = "no2no3";
    public static final String KEY_BOD = "bod";
    public static final String KEY_TOTAL_COLIFORMS = "total_coliforms";
    public static final String KEY_FAECAL_COLIFORMS = "faecal_coliforms";
    public static final String KEY_UPDATE_STATUS = "update_status";

    private static final String DATBASE_NAME = "mydb";
    private static final String DATABASE_TABLE = "mytable";
    private static final int DATABASE_VERSION = 1;
    private final Context ourContext;
    private DBHelper ourHelper;
    private SQLiteDatabase ourDatabase;

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

    public long createEntry(String uniqueID, String siteID, String siteLocation, String colour
            , String odour, String temp, String ph, String ec, String p_do
            , String no2no3, String bod, String total_coliforms, String faecal_coliforms, String status) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_UNIQUE_ID, uniqueID);
        cv.put(KEY_SITE_ID, siteID);
        cv.put(KEY_SITE_LOCATION, siteLocation);
        cv.put(KEY_COLOUR, colour);
        cv.put(KEY_ODOUR, odour);
        cv.put(KEY_TEMP, temp);
        cv.put(KEY_PH, ph);
        cv.put(KEY_EC, ec);
        cv.put(KEY_DO, p_do);
        cv.put(KEY_NO2NO3, no2no3);
        cv.put(KEY_BOD, bod);
        cv.put(KEY_TOTAL_COLIFORMS, total_coliforms);
        cv.put(KEY_FAECAL_COLIFORMS, faecal_coliforms);
        cv.put(KEY_UPDATE_STATUS, status);

        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public RowElement[] getData() {
        String[] columns = new String[]{KEY_ROWID, KEY_UNIQUE_ID, KEY_SITE_ID, KEY_SITE_LOCATION, KEY_COLOUR
                , KEY_ODOUR, KEY_TEMP, KEY_PH, KEY_EC, KEY_DO, KEY_NO2NO3
                , KEY_BOD, KEY_TOTAL_COLIFORMS, KEY_FAECAL_COLIFORMS};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, KEY_ROWID + " DESC");
        //for return array
        RowElement[] result = new RowElement[c.getCount()];
        Log.d("Size", "" + c.getCount());
        int i = 0;

        //int iRow = c.getColumnIndex(KEY_ROWID);
        int iUniqueID = c.getColumnIndex(KEY_UNIQUE_ID);
        int iSiteID = c.getColumnIndex(KEY_SITE_ID);
        int iSiteLocation = c.getColumnIndex(KEY_SITE_LOCATION);
        int iColour = c.getColumnIndex(KEY_COLOUR);
        int iOdour = c.getColumnIndex(KEY_ODOUR);
        int iTemp = c.getColumnIndex(KEY_TEMP);
        int iPH = c.getColumnIndex(KEY_PH);
        int iEC = c.getColumnIndex(KEY_EC);
        int iDO = c.getColumnIndex(KEY_DO);
        int iNO2NO3 = c.getColumnIndex(KEY_NO2NO3);
        int iBOD = c.getColumnIndex(KEY_BOD);
        int iTotalColiforms = c.getColumnIndex(KEY_TOTAL_COLIFORMS);
        int iFaecalColiforms = c.getColumnIndex(KEY_FAECAL_COLIFORMS);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            result[i] = new RowElement();

            result[i].setUniqueID(c.getString(iUniqueID));
            result[i].setSiteID(c.getString(iSiteID));
            result[i].setSiteLocation(c.getString(iSiteLocation));
            result[i].setColour(c.getString(iColour));
            result[i].setOdour(c.getString(iOdour));
            result[i].setTemp(c.getString(iTemp));
            result[i].setPh(c.getString(iPH));
            result[i].setEc(c.getString(iEC));
            result[i].setP_do(c.getString(iDO));
            result[i].setNo2no3(c.getString(iNO2NO3));
            result[i].setBod(c.getString(iBOD));
            result[i].setTotal_coliforms(c.getString(iTotalColiforms));
            result[i].setFaecal_coliforms(c.getString(iFaecalColiforms));

            i++;
        }

        return result;
    }

    //useless right now
    //need to open database before calling this function and close after use
    public ArrayList<HashMap<String, String>> getAllEntry() {

        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();


        String[] columns = new String[]{KEY_ROWID, KEY_UNIQUE_ID, KEY_SITE_ID, KEY_SITE_LOCATION, KEY_COLOUR
                , KEY_ODOUR, KEY_TEMP, KEY_PH, KEY_EC, KEY_DO, KEY_NO2NO3
                , KEY_BOD, KEY_TOTAL_COLIFORMS, KEY_FAECAL_COLIFORMS};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        Log.d("Size", "" + c.getCount());

        //int iRow = c.getColumnIndex(KEY_ROWID);
        int iUniqueID = c.getColumnIndex(KEY_UNIQUE_ID);
        int iSiteID = c.getColumnIndex(KEY_SITE_ID);
        int iSiteLocation = c.getColumnIndex(KEY_SITE_LOCATION);
        int iColour = c.getColumnIndex(KEY_COLOUR);
        int iOdour = c.getColumnIndex(KEY_ODOUR);
        int iTemp = c.getColumnIndex(KEY_TEMP);
        int iPH = c.getColumnIndex(KEY_PH);
        int iEC = c.getColumnIndex(KEY_EC);
        int iDO = c.getColumnIndex(KEY_DO);
        int iNO2NO3 = c.getColumnIndex(KEY_NO2NO3);
        int iBOD = c.getColumnIndex(KEY_BOD);
        int iTotalColiforms = c.getColumnIndex(KEY_TOTAL_COLIFORMS);
        int iFaecalColiforms = c.getColumnIndex(KEY_FAECAL_COLIFORMS);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("unique_id", c.getString(iUniqueID));
            map.put("site_id", c.getString(iSiteID));
            map.put("site_location", c.getString(iSiteLocation));
            map.put("colour", c.getString(iColour));
            map.put("odour", c.getString(iOdour));
            map.put("temp", c.getString(iTemp));
            map.put("ph", c.getString(iPH));
            map.put("ec", c.getString(iEC));
            map.put("do", c.getString(iDO));
            map.put("no2no3", c.getString(iNO2NO3));
            map.put("bod", c.getString(iBOD));
            map.put("total_coliforms", c.getString(iTotalColiforms));
            map.put("faecal_coliforms", c.getString(iFaecalColiforms));

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
        int iColour = c.getColumnIndex(KEY_COLOUR);
        int iOdour = c.getColumnIndex(KEY_ODOUR);
        int iTemp = c.getColumnIndex(KEY_TEMP);
        int iPH = c.getColumnIndex(KEY_PH);
        int iEC = c.getColumnIndex(KEY_EC);
        int iDO = c.getColumnIndex(KEY_DO);
        int iNO2NO3 = c.getColumnIndex(KEY_NO2NO3);
        int iBOD = c.getColumnIndex(KEY_BOD);
        int iTotalColiforms = c.getColumnIndex(KEY_TOTAL_COLIFORMS);
        int iFaecalColiforms = c.getColumnIndex(KEY_FAECAL_COLIFORMS);

        if (c.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("unique_id", c.getString(iUniqueID));
                map.put("site_id", c.getString(iSiteID));
                map.put("site_location", c.getString(iSiteLocation));
                map.put("colour", c.getString(iColour));
                map.put("odour", c.getString(iOdour));
                map.put("temp", c.getString(iTemp));
                map.put("ph", c.getString(iPH));
                map.put("ec", c.getString(iEC));
                map.put("do", c.getString(iDO));
                map.put("no2no3", c.getString(iNO2NO3));
                map.put("bod", c.getString(iBOD));
                map.put("total_coliforms", c.getString(iTotalColiforms));
                map.put("faecal_coliforms", c.getString(iFaecalColiforms));
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

    public void updateEntry(String UniqueId, String SiteID, String SiteLocation, String colour
            , String odour, String temp, String ph, String ec, String p_do
            , String no2no3, String bod, String total_coliforms, String faecal_coliforms) {

        ContentValues cv = new ContentValues();
        cv.put(KEY_UNIQUE_ID, UniqueId);
        cv.put(KEY_SITE_ID, SiteID);
        cv.put(KEY_SITE_LOCATION, SiteLocation);
        cv.put(KEY_COLOUR, colour);
        cv.put(KEY_ODOUR, odour);
        cv.put(KEY_TEMP, temp);
        cv.put(KEY_PH, ph);
        cv.put(KEY_EC, ec);
        cv.put(KEY_DO, p_do);
        cv.put(KEY_NO2NO3, no2no3);
        cv.put(KEY_BOD, bod);
        cv.put(KEY_TOTAL_COLIFORMS, total_coliforms);
        cv.put(KEY_FAECAL_COLIFORMS, faecal_coliforms);
        cv.put(KEY_UPDATE_STATUS, "no");

        ourDatabase.update(DATABASE_TABLE, cv, KEY_UNIQUE_ID + "=" + UniqueId, null);
    }


    public RowElement getEntry(String uniqueID) {
        RowElement rowElement = new RowElement();

        String[] columns = new String[]{KEY_ROWID, KEY_UNIQUE_ID, KEY_SITE_ID, KEY_SITE_LOCATION, KEY_COLOUR
                , KEY_ODOUR, KEY_TEMP, KEY_PH, KEY_EC, KEY_DO, KEY_NO2NO3
                , KEY_BOD, KEY_TOTAL_COLIFORMS, KEY_FAECAL_COLIFORMS};

        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_UNIQUE_ID + " = '" + uniqueID + "'", null, null, null, null);

        int iUniqueID = c.getColumnIndex(KEY_UNIQUE_ID);
        int iSiteID = c.getColumnIndex(KEY_SITE_ID);
        int iSiteLocation = c.getColumnIndex(KEY_SITE_LOCATION);
        int iColour = c.getColumnIndex(KEY_COLOUR);
        int iOdour = c.getColumnIndex(KEY_ODOUR);
        int iTemp = c.getColumnIndex(KEY_TEMP);
        int iPH = c.getColumnIndex(KEY_PH);
        int iEC = c.getColumnIndex(KEY_EC);
        int iDO = c.getColumnIndex(KEY_DO);
        int iNO2NO3 = c.getColumnIndex(KEY_NO2NO3);
        int iBOD = c.getColumnIndex(KEY_BOD);
        int iTotalColiforms = c.getColumnIndex(KEY_TOTAL_COLIFORMS);
        int iFaecalColiforms = c.getColumnIndex(KEY_FAECAL_COLIFORMS);

        int i = 0;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            rowElement.setUniqueID(c.getString(iUniqueID));
            rowElement.setSiteID(c.getString(iSiteID));
            rowElement.setSiteLocation(c.getString(iSiteLocation));
            rowElement.setColour(c.getString(iColour));
            rowElement.setOdour(c.getString(iOdour));
            rowElement.setTemp(c.getString(iTemp));
            rowElement.setPh(c.getString(iPH));
            rowElement.setEc(c.getString(iEC));
            rowElement.setP_do(c.getString(iDO));
            rowElement.setNo2no3(c.getString(iNO2NO3));
            rowElement.setBod(c.getString(iBOD));
            rowElement.setTotal_coliforms(c.getString(iTotalColiforms));
            rowElement.setFaecal_coliforms(c.getString(iFaecalColiforms));

            i++;
        }

        return rowElement;
    }

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DATBASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_UNIQUE_ID + " TEXT NOT NULL UNIQUE, " +
                    KEY_SITE_ID + " TEXT NOT NULL, " +
                    KEY_SITE_LOCATION + " TEXT NOT NULL, " +
                    KEY_COLOUR + " TEXT NOT NULL, " +
                    KEY_ODOUR + " TEXT NOT NULL, " +
                    KEY_TEMP + " TEXT NOT NULL, " +
                    KEY_PH + " TEXT NOT NULL, " +
                    KEY_EC + " TEXT NOT NULL, " +
                    KEY_DO + " TEXT NOT NULL, " +
                    KEY_NO2NO3 + " TEXT NOT NULL, " +
                    KEY_BOD + " TEXT NOT NULL, " +
                    KEY_TOTAL_COLIFORMS + " TEXT NOT NULL, " +
                    KEY_FAECAL_COLIFORMS + " TEXT NOT NULL, " +
                    KEY_UPDATE_STATUS + " TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

}
