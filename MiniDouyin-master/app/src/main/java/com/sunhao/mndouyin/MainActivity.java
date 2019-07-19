package com.sunhao.mndouyin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.sunhao.mndouyin.adapter.StaggeredItemDecoration;
import com.sunhao.mndouyin.adapter.VideoListAdapter;
import com.sunhao.mndouyin.beans.Feed;
import com.sunhao.mndouyin.beans.FeedResponse;
import com.sunhao.mndouyin.ui.VideoDetailActivity;
import com.sunhao.mndouyin.ui.VideoRecordActivity;
import com.sunhao.mndouyin.util.IMiniDouyinService;
import com.sunhao.mndouyin.util.RetrofitManager;
import com.sunhao.unity_to_android2.UnityPlayerActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements VideoListAdapter.ListItemOnClickListener {

    private static final String TAG = MainActivity.class.getName();
    private static final String URL_KEY = "video_url";
    private static final String TITLE_KEY = "video_title";
    private static final String URL_BUNDLE = "url_bundle";

    private RecyclerView recyclerView;
    private VideoListAdapter videoListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    //Restful API host
    private static final String HOST = "http://test.androidcamp.bytedance.com/mini_douyin/invoke/";

    private static final int REQUEST_CAMERA_RECORD_AUDIO_STORAGE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//hide status bar
        getSupportActionBar().hide();//hide title bar

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        Button button_music = (Button) findViewById(R.id.music);

        button_music.setOnClickListener(v ->
            startActivity(new Intent(MainActivity.this, MusicACtivity.class))
        );




        Button btn1 = findViewById(R.id.camera);
        btn1.setOnClickListener(v -> {
            // 申请存储、相机、麦克风权限
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CAMERA_RECORD_AUDIO_STORAGE);

            } else {
                startActivity(new Intent(MainActivity.this, VideoRecordActivity.class));
            }
        });
//        Button btn2 = findViewById(R.id.btn_refresh);
//        btn2.setOnClickListener(v -> refresh());


        recyclerView = findViewById(R.id.rv_video_list);

        //set LayoutManager
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new StaggeredItemDecoration(getApplicationContext(), 5));

        //set Adapter
        videoListAdapter = new VideoListAdapter(this);
        recyclerView.setAdapter(videoListAdapter);

        //refresh feed
        refresh();

        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            refresh();
            //刷新完成
            swipeRefreshLayout.setRefreshing(false);
        }, 3000));
    }

    /**
     * 刷新视频Feed流
     */
    private void refresh() {
        Retrofit retrofit = RetrofitManager.get(HOST);

        Call<FeedResponse> call = retrofit.create(IMiniDouyinService.class).feed();
        //异步网络请求
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "视频流刷新成功", Toast.LENGTH_LONG).show();
                    FeedResponse feedResponse = response.body();
                    if (feedResponse != null && feedResponse.isSuccess()) {
                        Log.d(TAG, feedResponse.getFeeds().toString());
                        videoListAdapter.refresh(feedResponse.getFeeds());
                    }
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                Log.d(TAG, "retrofit request fail");
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length <= 0 && grantResults.length <= 0) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CAMERA_RECORD_AUDIO_STORAGE:
                for (int i = 0; i < grantResults.length; i++) {
                    int state = grantResults[i];
                    if (state == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, permissions[i] + " permission granted",
                                Toast.LENGTH_SHORT).show();
                    } else if (state == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(MainActivity.this, permissions[i] + " permission denied",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                startActivity(new Intent(MainActivity.this, VideoRecordActivity.class));
                break;
        }
    }

    @Override
    public void onListItemClick(Feed feed) {
        Log.d(TAG, "onListItemClick");
        Intent intent = new Intent(MainActivity.this, VideoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(URL_KEY, feed.getVideoUrl());
        bundle.putString(TITLE_KEY, feed.getUsername());
        bundle.putString("cover", feed.getImageUrl());
        bundle.putString("stuid", feed.getStudentId());
        intent.putExtra(URL_BUNDLE, bundle);
        startActivity(intent);
    }
}
