package com.hikmatillo.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView stepView, textView, timeView;
    private ImageView stopBack;
    private Button refreshButton, stopButtom,startButton;
    private RelativeLayout buttonGroup;
    private TextView[][] buttons = new TextView[4][4];
    private ArrayList<String> numbers = new ArrayList<>();
    private Integer emptyI = 3;
    private Integer emptyJ = 3;
    private Integer timerCount = 0;
    private Integer stepCount =0;
    private View refreshButtom;
    private CountDownTimer timer;
    private String timeFormat;
    private boolean isStop = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadView();
        if(savedInstanceState!= null ){

            stepCount = savedInstanceState.getInt(KEY_STEP);
            stepView.setText(String.valueOf(stepCount));
            timerCount = savedInstanceState.getInt(KEY_TIME);
            timer=new CountDownTimer(100000_000L,1_000){
                @Override
                public void onTick(long millisUntilFinished){
                    timerCount++;
                    setTime();
                }
                @Override
                public void onFinish(){

                }
            };
            timer.start();



            emptyI = savedInstanceState.getInt(KEY_EMPTYI);
            emptyJ = savedInstanceState.getInt(KEY_EMPTYJ);

            String[] numbers = savedInstanceState.getStringArray(KEY_NUMBERS);
            for (int i = 0; i <16; i++) {
                buttons [i/4][i%4].setText(numbers[i]);
                buttons [i/4][i%4].setVisibility(View.VISIBLE);
            }
            buttons[emptyI][emptyI].setVisibility(View.INVISIBLE);
            isStop = savedInstanceState.getBoolean(KEY_STOP);
            if(!isStop) {
                stopBack.setVisibility(View.GONE);
                startButton.setVisibility(View.GONE);
                stopButtom.setVisibility(View.VISIBLE);
                buttonGroup.setVisibility(View.VISIBLE);
                timer.start();
                isStop = false;
            }else {

                buttonGroup.setVisibility(View.GONE);
                stopBack.setVisibility(View.VISIBLE);
                stopButtom.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);
                setTime();
                timer.cancel();
                isStop = true;
            }

        }else {
            loadnumber();
            loadDataToViwe();
        }


        loadAction();

    }

    private void loadAction() {
        for (int i = 0; i < 16; i++) {
            buttons[i / 4][i % 4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView button = (TextView) view;
                    String tag = button.getTag().toString();
                    int i = Integer.parseInt(tag.split(":")[0]);
                    int j = Integer.parseInt(tag.split(":")[1]);
                    int deltaI = Math.abs(emptyI - i);
                    int deltaj = Math.abs(emptyJ - j);
                    if (deltaI + deltaj == 1) {
                        buttons[emptyI][emptyJ].setText(buttons[i][j].getText());
                        buttons[emptyI][emptyJ].setVisibility(View.VISIBLE);
                        buttons[i][j].setText("");
                        buttons[i][j].setVisibility(View.INVISIBLE);
                        emptyI = i;
                        emptyJ = j;
                        if (emptyI ==3&& emptyJ==3){
                            chekWin();
                        }
                        stepCount++;
                        stepView.setText(stepCount.toString());

                    }
                }
            });
        }
        refreshButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                setRefresh();
                stopBack.setVisibility(View.GONE);
                buttonGroup.setVisibility(View.VISIBLE);

                
            }
        });
   stopButtom.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {

           buttonGroup.setVisibility(View.GONE);
           stopBack.setVisibility(View.VISIBLE);
           stopButtom.setVisibility(View.GONE);
           startButton.setVisibility(View.VISIBLE);
           timer.cancel();


       }
   });
   startButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           stopBack.setVisibility(View.GONE);
           startButton.setVisibility(View.GONE);
           stopButtom.setVisibility(View.VISIBLE);
           buttonGroup.setVisibility(View.VISIBLE);
           timer.start();
       }
   });



    }

    private void setRefresh() {

        timerCount = 0;
        stepCount = 0;
        timer.cancel();
        loadnumber();
        emptyI = 3;
        emptyJ = 3;
        loadDataToViwe();


    }

    private void chekWin() {

        for (int i = 0; i < 14; i++) {

            int current =Integer.parseInt(buttons[i/4][i%4].getText().toString());
            int next =Integer.parseInt(buttons[(i+1)/4][(i+1)%4].getText().toString());

            if (current>next){
                return;
            }


        }
        Toast.makeText(this, "You win !", Toast.LENGTH_SHORT).show();
        timer.cancel();
        Intent intent = new Intent(MainActivity.this,WinActivity.class);
        intent.putExtra("timeFormat",timeFormat);
        intent.putExtra("stepCount",stepCount);
        startActivity(intent);
    }

       private void loadDataToViwe() {

        
        timer=new CountDownTimer(100000_000L,1_000){
            @Override
            public void onTick(long millisUntilFinished){
                timerCount++;
                setTime();


            }
            @Override
            public void onFinish(){

            }
        };
       timer.start();


       for (int i = 0; i <15; i++) {
            buttons [i/4][i%4].setText(numbers.get(i));
           buttons [i/4][i%4].setVisibility(View.VISIBLE);
        }
       buttons[emptyI][emptyJ].setVisibility(View.INVISIBLE);

    }
    

    private void setTime() {
         timeFormat = converter(timerCount);//00:00:56
        timeView.setText(timeFormat);

    }
    private String  converter(Integer n){
        int hour = n/3600;
        int minut = n % 3600/60;
        int second = n % 60;
        return String.format("%02d:%02d:%02d",hour,minut,second);

    };

    private void loadnumber() {
        numbers.clear();
        for (Integer i= 1; i <=15; i++) {
       numbers.add(String.valueOf(i));
        }
        /*Collections.shuffle(numbers);*/

    }

    private void loadView() {
        startButton=findViewById(R.id.StartButtom);
        stepView = findViewById(R.id.stepView);
        timeView = findViewById(R.id.tiemView);
        stopButtom = findViewById(R.id.StopButtom);
        refreshButton = findViewById(R.id.RefreshButtum);
        buttonGroup = findViewById(R.id.buttonGroup);
        stopBack = findViewById(R.id.stop_back);


        for (int i = 0; i < buttonGroup.getChildCount(); i++) {

            TextView textButton = (TextView) buttonGroup.getChildAt(i);
            buttons[i/4][i%4] =textButton;

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
       String[] texts = new String[16];
        for (int i = 0; i < 16; i++) {
           TextView child = (TextView) buttonGroup.getChildAt(i);
           String text = child.getText().toString();
        texts[i] = text;




        }
        outState.putInt (KEY_STEP, stepCount);
        outState.putInt(KEY_TIME,timerCount);
        outState.putInt(KEY_EMPTYI,emptyI);
        outState.putInt(KEY_EMPTYJ,emptyJ);
        outState.putStringArray("NUMBERS", texts);
        outState.putBoolean("KEY_STOP", isStop);


        super.onSaveInstanceState(outState);
    }
    private  static final String KEY_STEP ="key";
    private  static final String KEY_TIME="time";
    private  static final String KEY_EMPTYI ="emptyI";
    private  static final String KEY_EMPTYJ ="emptyJ";
    private  static final String KEY_NUMBERS ="NUMBERS";
    private  static final String KEY_STOP ="KEY_STOP";
}











