package com.example.crudapp.petesic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "Video Games DB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Games";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String RELEASE_DATE_COL = "release_date";
    private static final String ICON_COL = "icon";
    private static final String DEVELOPER_COL = "developer";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null ,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + RELEASE_DATE_COL + " TEXT,"
                + ICON_COL + " BLOB,"
                + DEVELOPER_COL + " TEXT)";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void addNewGame(String name, String releaseDate, byte[] icon, String developer){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(NAME_COL, name);
        cv.put(RELEASE_DATE_COL, releaseDate);
        cv.put(ICON_COL, icon);
        cv.put(DEVELOPER_COL, developer);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void updateGame(String originalName, String name, String releaseDate, byte[] icon, String developer){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(NAME_COL, name);
        cv.put(RELEASE_DATE_COL, releaseDate);
        cv.put(ICON_COL, icon);
        cv.put(DEVELOPER_COL, developer);

        db.update(TABLE_NAME, cv, "name=?", new String[]{originalName});
        db.close();
    }

    public void deleteGame(String name){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "name=?", new String[]{name});
        db.close();
    }

    public Cursor getDataByName(String name){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " +TABLE_NAME+ " WHERE name =" + "'" + name + "'" +";";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        return data;
    }
}
