package com.he.data.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.he.config.KeyConfig;
import com.he.data.User;

import java.util.ArrayList;
import java.util.List;

public class DBCenter extends SQLiteOpenHelper {
    private static DBCenter instance;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "he.db";
    private static Context context;
    private static SQLiteDatabase mDatabase;
    private static DatabaseContext mContext;

    public static DBCenter getInstance(Context context) {
        if (instance == null) {
            instance = new DBCenter(context.getApplicationContext());
        }

        return instance;
    }

    private DBCenter(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        setContext(context);
    }

    @SuppressLint("NewApi")
    @Override
    public SQLiteDatabase getWritableDatabase() {
        mContext = new DatabaseContext(context);
        if (DB_NAME == null) {
            mDatabase = SQLiteDatabase.create(null);
        } else {
            mDatabase = mContext.openOrCreateDatabase(DB_NAME, 0, null, new DatabaseErrorHandler() {

                @Override
                public void onCorruption(SQLiteDatabase arg0) {
                    Log.i(KeyConfig.TAG_NAME, "openOrCreateDatabase onCorruption");
                }
            });
            if (mDatabase != null) {
                onCreate(mDatabase);
            }
        }
        return mDatabase;
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table if not exists user_table (_id integer primary key autoincrement ,time integer, password varchar(20), username)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS user_table");
            onCreate(db);
        }
    }

    public synchronized int clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DB_NAME, null, null);
    }

    public synchronized void close() {
        try {
            this.getWritableDatabase().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public long insertOrUpdateLoginUserName(String userName, String password, long time) {
        boolean isExistUser = isExistUser(userName) ;
        long id = -1L;
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            if (isExistUser) {
                //这个得修改
                values.put("time", time);
                values.put("password", password);
                id = db.update("user_table", values, "username = ?", new String[]{userName});
            } else {
                values.put("username", userName);
                values.put("password", password);
                values.put("time", time);
                id = db.insert("user_table", null, values);
            }
        }
        return id;
    }

    //查询之前 按照时间大小排序z
    public List<User> selectAllUser() {
        List<User> ret = new ArrayList<User>();
        SQLiteDatabase db = this.getWritableDatabase();
        if (null == db) {
            return ret;
        }

        String _orderBy = "time desc";
        Cursor cursor = db.query("user_table", null, null, null, null, null, _orderBy);
        cursor.moveToFirst();
        int count = cursor.getCount();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            ret.add(user);
            cursor.moveToNext();
        }
        return ret;
    }

    private boolean isExistUser(String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        if(null == db){
            return false ;
        }
        Cursor cursor = db.query("user_table", null, "username=?", new String[]{userName}, null, null, null);
        return  cursor.getCount() > 0 ;
    }

    
    public String[] queryAllUserName()
    {
      SQLiteDatabase db = this.getWritableDatabase();
      if (db != null) {
        String _orderBy = "_id desc";
        Cursor cursor = db.query("user_table", null, null, null, null, null, _orderBy);
        int count = cursor.getCount();
        String[] userNames = new String[count];
        if (count > 0) {
          cursor.moveToFirst();
          for (int i = 0; i < count; i++) {
            userNames[i] = cursor.getString(cursor.getColumnIndex("username"));
            cursor.moveToNext();
          }
        }
        return userNames;
      }
      return new String[0];
    }
    
    public String queryPasswordByName(String username)
    {
      SQLiteDatabase db = this.getWritableDatabase();
      String sql = "select * from user_table where username = '" + username + "'";
      Cursor cursor = db.rawQuery(sql, null);
      String password = "";
      if (cursor.getCount() > 0) {
        cursor.moveToFirst();
        password = cursor.getString(cursor.getColumnIndex("password"));
      }
      return password;
    }

    public long delete(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("user_table", "username = '" + userName + "'", null);
    }
}