package com.example.org.unizd.rma.petesic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prac.R;

public class MainActivity extends AppCompatActivity {

    private TextView tvPozdrav;
    private Button btnDrugaAktivnost;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initial text
        tvPozdrav = findViewById(R.id.tvPozdrav);
        editText = findViewById(R.id.inputText);

        //init button
        btnDrugaAktivnost = findViewById(R.id.btnDrugaAktivnost);
        btnDrugaAktivnost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeatherActivity();
            }
        });
    }

    private void openWeatherActivity(){
        //fetch city choice and send to weather activity
        String city = editText.getText().toString();

        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);
    }

}