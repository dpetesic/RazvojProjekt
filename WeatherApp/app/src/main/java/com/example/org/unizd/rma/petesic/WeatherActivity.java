package com.example.org.unizd.rma.petesic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prac.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private Button btnZavrsi;
    private TextView tempView;
    private TextView windView;
    private TextView humidView;
    private TextView city;
    String cityChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_druga_aktivnost);

        //Get the city choice and set it as header
        city = findViewById(R.id.nameView);
        cityChoice = getIntent().getStringExtra("city");
        city.setText(cityChoice);

        //Init views and set initial state
        tempView = findViewById(R.id.tempView);
        windView = findViewById(R.id.speedView);
        humidView = findViewById(R.id.humidView);
        tempView.setText("Temperature: ");
        windView.setText("Wind Speed: ");
        humidView.setText("Humidity: ");

        //Fetch api data
        dataCollect();

        //init return button
        btnZavrsi = findViewById(R.id.btnFinish);
        btnZavrsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zavrsi();
            }
        });
    }

    private void zavrsi() {
        finish();
    }

    public void dataCollect() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //Init client and fetch json
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.tomorrow.io/v4/weather/forecast?location="+ cityChoice +"&timesteps=1h&units=metric&apikey=[API KEY HERE]")
                        .get()
                        .addHeader("accept", "application/json")
                        .build();

                //async method, avoids crash
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        city.setText("Call failed!");
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {

                            //Call json parser class
                            JsonParser parser = new JsonParser();
                            String data = response.body().string();

                            //fetch the data
                            String temp = parser.readTemp(data);
                            String wind = parser.readWindSpeed(data);
                            String humidity = parser.readHumidity(data);


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //put data into the views
                                    tempView.setText("Temperature: " + temp + "C");
                                    windView.setText("Wind Speed: " + wind + "km/h");
                                    humidView.setText("Humidity: " + humidity + "%");
                                }
                            });
                        }
                    }
                });
            }
        });
        thread.run();
    }
}