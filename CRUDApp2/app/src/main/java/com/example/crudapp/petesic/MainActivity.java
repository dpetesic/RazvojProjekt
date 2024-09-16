package com.example.crudapp.petesic;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudapp.R;

import java.text.MessageFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button datePicker;
    Button cameraBtn;
    Button addBtn;
    Button deleteBtn;
    Button updateBtn;
    Button viewBtn;
    String date;
    byte[] img;
    EditText name;
    EditText developer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //init elements
        datePicker = findViewById(R.id.datePicker);
        cameraBtn = findViewById(R.id.cameraBtn);
        addBtn = findViewById(R.id.addBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        updateBtn = findViewById(R.id.updateBtn);
        name = findViewById(R.id.editName);
        developer = findViewById(R.id.editDeveloper);
        viewBtn = findViewById(R.id.viewBtn);

        //ask for permission to use camera
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }


        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //data view intent
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", name.getText().toString());
                startActivity(intent);
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to db
                DBClass dbClass = new DBClass();
                dbClass.addToDb(name.getText().toString(), date, img, developer.getText().toString(), MainActivity.this);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks if the data point exists
                if (name.getText().toString().length() == 0){
                    Toast.makeText(MainActivity.this, "Please input name for update", Toast.LENGTH_SHORT).show();
                } else {
                    //sends data to update activity
                    String originalName = name.getText().toString();
                    Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                    intent.putExtra("originalName", originalName);
                    startActivity(intent);
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks if data point exists
                if (name.getText().toString().length() == 0){
                    Toast.makeText(MainActivity.this, "Please input name for deleting", Toast.LENGTH_SHORT).show();
                } else {
                    //delete data
                    DBClass dbClass = new DBClass();
                    dbClass.deleteGame(name.getText().toString(), MainActivity.this);
                }
            }
        });

        //camera button actions
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(intent);
            }
            ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //image capture, convert to byte array
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                        BitmapUtils bmu = new BitmapUtils();
                        img = bmu.getBytes(bitmap);
                        Toast.makeText(MainActivity.this, "Photo Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


        //pick date button
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calendar functions
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date = MessageFormat.format("{0}/{1}/{2}", String.valueOf(dayOfMonth),String.valueOf(month), String.valueOf(year));
                        Toast.makeText(MainActivity.this, "Date Selected", Toast.LENGTH_SHORT).show();

                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }
}