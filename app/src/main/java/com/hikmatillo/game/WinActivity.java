package com.hikmatillo.game;

import static androidx.core.view.PointerIconCompat.load;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {
    private TextView stepWin, timeWin,textForEdit;
    private Integer tempStep;
    Button backBtn, saveBtn;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        load();
        loadData();

    }
    private void loadData() {
        timeWin.setText(getIntent().getStringExtra("timeFormat"));
        tempStep = getIntent().getIntExtra("stepCount",0);
        stepWin.setText(String.valueOf(tempStep + 1));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WinActivity.this, StartActivity.class);
                startActivity(intent);


            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCashe.getObject().saveTime(getIntent().getStringExtra("timeFormat"));
                Integer temp = tempStep+1;
                AppCashe.getObject().saveStep(temp);
            }
        });
    }
    private void load(){
        timeWin = findViewById(R.id.timeWin);
        stepWin = findViewById(R.id.stepWin);
        textForEdit= findViewById(R.id.textEdit);
        editText = findViewById(R.id.editText);
        backBtn = findViewById(R.id.backButton);
        saveBtn = findViewById(R.id.saveButton);
    }
}