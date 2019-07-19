package com.sunhao.mndouyin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.sunhao.unity_to_android2.UnityPlayerActivity;
//import com.chibde.visualizer.CircleVisualizer;
//import com.chibde.visualizer.CircleVisualizer;
//import com.gauravk.audiovisualizer.visualizer.BlastVisualizer;
import com.sunhao.mndouyin.R;

public class MusicACtivity extends AppCompatActivity {
//    public CircleVisualizer circleVisualizer = findViewById(R.id.visualizer);
////    public BlastVisualizer mVisualizer = findViewById(R.id.blast);
//

   // set custom color to the line.

//    public void setCircleVisualizer(CircleVisualizer circleVisualizer) {
//        this.circleVisualizer = circleVisualizer;
//        circleVisualizer.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        // Customize the size of the circle. by default multipliers is 1.
//        circleVisualizer.setRadiusMultiplier(1.2f);
//
//// set the line with for the visualizer between 1-10 default 1.
//        circleVisualizer.setStrokeWidth(1);
//
//// Set you media player to the visualizer.
//        circleVisualizer.setPlayer(mp.getAudioSessionId());
//
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_activity);
        getSupportActionBar().hide();//hide title bar

        Button audioplay = (Button) findViewById(R.id.button_audioplay);
        MediaPlayer mp;
        Button gamemode = (Button) findViewById(R.id.button_gamemode);

//        gamemode.setOnClickListener(view ->
//                startActivity(new Intent(getApplicationContext(), UnityPlayerActivity.class)));
//
        gamemode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), TestActivity2.class));
                startActivity(new Intent(getApplicationContext(), UnityPlayerActivity.class));
            }
        });
//        int audioSessionId = mp.getAudioSessionId();s
//        if (audioSessionId != -1)
//            mVisualizer.setAudioSessionId(audioSessionId);


//        CircleVisualizer circleVisualizer = findViewById(R.id.visualizer);
//        mp = MediaPlayer.create(this, R.raw.trip);
////
//// set custom color to the line.
//        circleVisualizer.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
//
//// Customize the size of the circle. by default multipliers is 1.
//        circleVisualizer.setRadiusMultiplier(1.2f);
//
//// set the line with for the visualizer between 1-10 default 1.
//        circleVisualizer.setStrokeWidth(1);
//
//// Set you media player to the visualizer.
//        circleVisualizer.setPlayer(mp.getAudioSessionId());
    }
}
