package com.example.crudapp.petesic;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
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

public class UpdateActivity extends AppCompatActivity {

    Button datePicker;
    Button cameraBtn;
    Button updateBtn;
    byte[] img;
    String date;
    EditText name;
    EditText developer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //init elements
        datePicker = findViewById(R.id.dateBtn);
        cameraBtn = findViewById(R.id.photoBtn);
        name = findViewById(R.id.updateName);
        developer = findViewById(R.id.updateDeveloper);
        updateBtn = findViewById(R.id.updateGameBtn);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updates with new data
                DBClass dbClass = new DBClass();
                dbClass.updateDb(getIntent().getStringExtra("originalName"), name.getText().toString(), date, img, developer.getText().toString(), UpdateActivity.this);

                Toast.makeText(UpdateActivity.this, "Database Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        //camera function
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(intent);
            }
            ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                        BitmapUtils bmu = new BitmapUtils();
                        img = bmu.getBytes(bitmap);
                        Toast.makeText(UpdateActivity.this, "Photo Saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        //calendar function
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date = MessageFormat.format("{0}/{1}/{2}", String.valueOf(dayOfMonth),String.valueOf(month), String.valueOf(year));
                        Toast.makeText(UpdateActivity.this, "Date Selected", Toast.LENGTH_SHORT).show();

                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }
}