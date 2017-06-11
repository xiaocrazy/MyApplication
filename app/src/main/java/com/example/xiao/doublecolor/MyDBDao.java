package com.example.xiao.doublecolor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by xiao on 2017/6/3.
 */

public class MyDBDao {

    private MyDBOpenHelper helper;
    public MyDBDao(Context context) {
        helper = new MyDBOpenHelper(context);
    }

    public long insert(String name,String sex) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("sex",sex);
        long result = db.insert("student",null,values);
        db.close();
        return result;
    }

    public int delete(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int result = db.delete("student","name=?",new String[]{name});
        db.close();
        return result;
    }

    public int update(String name,String newSex) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sex",newSex);
        int result = db.update("student",values,"name=?",new String[]{name});
        return result;
    }

    public String select(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("studnet",new String[]{"sex"},"name=?",new String[]{name},null,null,null);
        String sex = null;
        while (cursor.moveToNext()) {
            sex = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return sex;
    }

    public String selectAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("studnet",new String[]{"name","sex"},null,null,null,null,null);
        String sex = null;
        String name = null;;
        while (cursor.moveToNext()) {
            name = cursor.getString(0);
            sex = cursor.getString(1);
        }
        cursor.close();
        db.close();
        return sex;
    }
}
