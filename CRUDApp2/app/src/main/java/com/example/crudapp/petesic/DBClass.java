package com.example.crudapp.petesic;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class DBClass {

    DBHandler dbHandler;

    public void addToDb(String name, String releaseDate, byte[] icon, String developer, Context context) {
        dbHandler = new DBHandler(context);
        dbHandler.addNewGame(name, releaseDate, icon, developer);

        Toast.makeText(context, "New Game has been added", Toast.LENGTH_SHORT).show();
    }


    public void updateDb(String originalName, String name, String releaseDate, byte[] icon, String developer, Context context){
        dbHandler = new DBHandler(context);
        dbHandler.updateGame(originalName, name, releaseDate, icon, developer);

        Toast.makeText(context, "Game has been updated", Toast.LENGTH_SHORT).show();
    }


    public void deleteGame(String name, Context context){
        dbHandler = new DBHandler(context);
        dbHandler.deleteGame(name);

        Toast.makeText(context, "Game has been deleted", Toast.LENGTH_SHORT).show();
    }


    public void readGame(String dataName, EditText name, EditText developer, EditText date, ImageView icon, Context context){

            //data reader
            dbHandler = new DBHandler(context);
            Cursor cursor = null;

            cursor = dbHandler.getDataByName(dataName);

            BitmapUtils bu = new BitmapUtils();

            name.setText(cursor.getString(1));
            developer.setText(cursor.getString(4));
            date.setText(cursor.getString(2));
            Bitmap bitmap = bu.getImage(cursor.getBlob(3));
            icon.setImageBitmap(bitmap);
    }
}
