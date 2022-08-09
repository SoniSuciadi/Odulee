package com.sonisuciadi.odulee.DataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsersDataBaseHelper extends SQLiteOpenHelper {
    Context context;
    private static final String TABLE_NAME = "tbl_uses";
    private static final int DATABASE_VERSION = 1;
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAMA = "nama";
    private static final String FIELD_ALAMAT = "alamat";
    private static final String FIELD_PASSWORD = "password";
    public UsersDataBaseHelper(Context context) {
        super(context,TABLE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+" ("+
                FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                FIELD_NAMA+ " TEXT, " +
                FIELD_ALAMAT+ " TEXT, " +
                FIELD_PASSWORD+ " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public long insertUser(String nama, String alamat,String password){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(FIELD_NAMA,nama);
        cv.put(FIELD_ALAMAT,alamat);
        cv.put(FIELD_PASSWORD,password);

        long exc=db.insert(TABLE_NAME,null,cv);
        return exc;
    }
}
