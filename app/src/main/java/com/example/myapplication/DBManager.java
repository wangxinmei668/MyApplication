package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context){
        dbHelper=new DBHelper(context);
        TBNAME=DBHelper.TB_NAME;

    }

    public void add(NewsItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curNews",item.getCurNews());
        values.put("curlink",item.getCurNews());
        db.insert(TBNAME,null,values);
        db.close();
    }

    public void addAll(List<NewsItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (NewsItem item : list){
            ContentValues values = new ContentValues();
            values.put("curNews",item.getCurNews());
            values.put("curlink",item.getCurlink());
            db.insert(TBNAME,null,values);

        }
        db.close();
    }

    public void deleteALL(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();

    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,"ID=?",new String[]{String.valueOf(id)});
        db.close();

    }

    public void update(NewsItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curNews",item.getCurNews());
        values.put("curlink",item.getCurlink());
        db.insert(TBNAME,null,values);
        db.update(TBNAME,values,"ID=?",new String[]{String.valueOf(item.getId())});
        db.close();

    }

    public List<NewsItem> listAll(){
        List<NewsItem> rateList=null;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,null,null,null,null,null);
        if(cursor!=null){
            rateList = new ArrayList<NewsItem>();
            while (cursor.moveToNext()){
                NewsItem item = new NewsItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurlink(cursor.getString(cursor.getColumnIndex("CURLINK")));
                item.setCurNews(cursor.getString(cursor.getColumnIndex("CURNEWS")));

                rateList.add(item);
            }
            cursor.close();

        }
        db.close();
        return rateList;

    }

    public NewsItem findById(int id){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,"ID=?",new String[]{String.valueOf(id)},null,null,null);
        NewsItem rateItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            rateItem = new NewsItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurlink(cursor.getString(cursor.getColumnIndex("CURLINK")));
            rateItem.setCurNews(cursor.getString(cursor.getColumnIndex("CURNEWS")));
            cursor.close();
        }
        db.close();
        return rateItem;

    }



}
