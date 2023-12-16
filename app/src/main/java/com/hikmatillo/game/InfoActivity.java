package com.hikmatillo.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    private TextView timeInfo;
    private TextView countInfo,stepInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        timeInfo = findViewById(R.id.timeInfo);
        timeInfo.setText(AppCashe.getObject().getTime());
        stepInfo=findViewById(R.id.stepInfo);
        stepInfo.setText(String.valueOf(AppCashe.getObject().getStep()));


    }
}





