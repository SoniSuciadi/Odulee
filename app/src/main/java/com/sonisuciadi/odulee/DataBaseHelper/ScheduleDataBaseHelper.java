package com.sonisuciadi.odulee.DataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScheduleDataBaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final String TABLE_NAME = "tbl_schedule";
    private static final int DATABASE_VERSION = 1;
    private static final String FIELD_ID = "id";
    private static final String FIELD_JUDUL = "judul";
    private static final String FIELD_TEMPAT = "tempat";
    private static final String FIELD_TANGGAL = "tanggal";
    private static final String FIELD_DESKRIPSI = "deskripsi";
    private static final String FIELD_PEMBUAT = "pembuat";
    public ScheduleDataBaseHelper(Context context) {
        super(context,TABLE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+" ("+
                FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                FIELD_JUDUL+ " TEXT, " +
                FIELD_TEMPAT+ " TEXT, " +
                FIELD_TANGGAL+ " TEXT, " +
                FIELD_DESKRIPSI+ " TEXT, " +
                FIELD_PEMBUAT+ " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public long insertSchedule(String judul, String tempat,String tanggal, String deskripsi,String pembuat){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(FIELD_JUDUL,judul);
        cv.put(FIELD_TEMPAT,tempat);
        cv.put(FIELD_TANGGAL,tanggal);
        cv.put(FIELD_DESKRIPSI,deskripsi);
        cv.put(FIELD_PEMBUAT,pembuat);

        long exc=db.insert(TABLE_NAME,null,cv);
        return exc;
    }
    public long updateSchedule(String id,String judul, String tempat,String tanggal, String deskripsi,String pembuat){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(FIELD_JUDUL,judul);
        cv.put(FIELD_TEMPAT,tempat);
        cv.put(FIELD_TANGGAL,tanggal);
        cv.put(FIELD_DESKRIPSI,deskripsi);
        cv.put(FIELD_PEMBUAT,pembuat);

        long exc=db.update(TABLE_NAME,cv,"id = ?",new String[]{id});
        return exc;
    }
    public Cursor retriveSchedule(){
        String querry="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(querry,null);
        if (cursor==null){
            cursor=sqLiteDatabase.rawQuery(querry,null);
        }
        return cursor;
    }
    public long deleteSchedule(String index){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        long exc=sqLiteDatabase.delete(TABLE_NAME,"id= ?",new String[]{index});
        return exc;
    }
}
