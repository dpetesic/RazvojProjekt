package com.example.crudapp.petesic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.crudapp.R;

public class DataActivity extends AppCompatActivity {

    EditText name;
    EditText developer;
    EditText date;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        //init elements
        name = findViewById(R.id.name);
        developer = findViewById(R.id.developer);
        date = findViewById(R.id.date);
        icon = findViewById(R.id.icon);

        //read data from db
        DBClass dbClass = new DBClass();
        String dataName = getIntent().getStringExtra("name");
        try {
            dbClass.readGame(dataName, name, developer, date, icon, DataActivity.this);
        }catch (Exception e){
            Toast.makeText(DataActivity.this, "No SQL data found", Toast.LENGTH_LONG).show();
        }
    }
}