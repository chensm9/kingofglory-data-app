package com.example.a17980.herolist;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    boolean vol = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        ImageButton epigraph = findViewById(R.id.epigraph);
        epigraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EpigraphActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //初始化视频
        videoInit();
    }

    public void videoInit(){
        final VideoView videoView= findViewById(R.id.backVideo);
        final ImageButton volume = findViewById(R.id.volume);
        MediaController mediaController = new MediaController(this);

        //获取视频文件路径
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.video;
        videoView.setVideoURI(Uri.parse(uri));

        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        //隐藏进度条
        mediaController.setVisibility(View.GONE);
        videoView.requestFocus();
        videoView.start();

        //加载结束后的监听事件
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);

                //点击音量按钮
                volume.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (vol){
                            volume.setBackgroundResource(R.mipmap.close);
                            mp.setVolume(0f, 0f);
                            vol = false;
                        }
                        else{
                            volume.setBackgroundResource(R.mipmap.open);
                            mp.setVolume(1f,1f);
                            vol = true;
                        }
                    }
                });
            }
        });
    }
}
