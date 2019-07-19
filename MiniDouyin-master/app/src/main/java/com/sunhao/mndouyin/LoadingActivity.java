package com.sunhao.mndouyin;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Calendar;

public class LoadingActivity extends AppCompatActivity {
    private final long SLEEP_TIME = 5000;
//    private Handler handler_loading = new Handler();
    protected Animation animation_loading;
    protected Animation animation_text_time;
    public TextView text_loading;
    public TextView text_loading_ad;

    public TextView text_time;
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide status bar
        getSupportActionBar().hide();//hide title bar

        text_loading_ad  = findViewById(R.id.text_loading_ad);
        text_loading  = findViewById(R.id.loading_text);
        animation_loading = AnimationUtils.loadAnimation(this, R.anim.loading_anim);

        text_time = findViewById(R.id.text_time);
        animation_text_time = AnimationUtils.loadAnimation(this, R.anim.text_time);
        if((hour >= 0 && hour <= 4) || (hour >= 20 && hour <= 24)){
            text_time.setText("晚上好");
        }else if(hour > 4 && hour < 11){
            text_time.setText("早上好");
        }else if(hour >= 11 && hour <= 14){
            text_time.setText("中午好");
        }else{
            text_time.setText("下午好");
        }

        Thread thread_loadingActivity = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(SLEEP_TIME);

                    //start Main Activity
                    Intent MainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(MainActivityIntent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        text_time.startAnimation(animation_text_time);
        text_loading.startAnimation(animation_loading);
        thread_loadingActivity.start();

    }
}
