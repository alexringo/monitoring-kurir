package com.project.pemesananobat;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Me on 25/04/2016.
 */
public class GPSDatabase extends SQLiteOpenHelper {

    public GPSDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table location(id integer primary key autoincrement, id_pesanan integer(5), lat float(10,6)," +
                "lng float(10,6), kurir varchar(20), tgl date, synced varchar(5));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS location;");
        onCreate(db);
    }

    public void insertlocation(String id_pesanan,String lat, String lng, String kurir, String tgl, String synced){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_pesanan", id_pesanan);
        contentValues.put("lat", lat);
        contentValues.put("lng", lng);
        contentValues.put("kurir", kurir);
        contentValues.put("tgl", tgl);
        contentValues.put("synced", synced);
        this.getWritableDatabase().insert("location", null, contentValues);
    }

    public ArrayList<HashMap<String, String>> getlocation() {
        ArrayList<HashMap<String, String>> listlocation;
        listlocation = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from location where synced='no'",null);
        while (cursor.moveToNext()){
            HashMap<String, String> list = new HashMap<>();
            list.put("id_pesanan", cursor.getString(1));
            list.put("lat", cursor.getString(2));
            list.put("lng", cursor.getString(3));
            list.put("kurir", cursor.getString(4));
            list.put("tgl", cursor.getString(5));
            listlocation.add(list);
        }
        this.getReadableDatabase().close();
        return listlocation;
    }

    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "select * from location where synced='no'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> list = new HashMap<String, String>();
                list.put("id_pesanan", cursor.getString(1));
                list.put("lat", cursor.getString(2));
                list.put("lng", cursor.getString(3));
                list.put("kurir", cursor.getString(4));
                list.put("tgl", cursor.getString(5));
                wordList.add(list);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    public void updateSyncStatus(){
        this.getWritableDatabase().execSQL("update location set synced='yes' where synced='no'");
        this.getWritableDatabase().close();
    }
}
