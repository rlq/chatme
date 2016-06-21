package com.he.data.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import com.he.config.HeTask;

import java.io.File;
import java.io.IOException;

public class DatabaseContext extends ContextWrapper {
    public DatabaseContext(Context base) {
        super(base);
        DBCenter.getInstance(base);
    }

    public File getDatabasePath(String name) {
        String dbDir = HeTask.getInstance().sdCardRoot();
        if(null == dbDir){
            return null ;
        }
        dbDir = dbDir + "/HE";
        String dbPath = dbDir + "/" + name;

        File dirFile = new File(dbDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        boolean isFileCreateSuccess = false;

        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            isFileCreateSuccess = true;
        }

        if (isFileCreateSuccess) {
            return dbFile;
        }
        return null;
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return  SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }
}