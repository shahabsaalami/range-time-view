package com.pirooze.showtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CircleRangeTimeView circleTimeView = findViewById(R.id.circleTimeView);
        circleTimeView.setRangeTime("15:00-18:49");

        LineRangeTimeView lineRangeTime = findViewById(R.id.lineRangeTime);
        lineRangeTime.addRangeTime("06:00-18:30");
        lineRangeTime.addRangeTime("00:00-03:30");


    }
}
