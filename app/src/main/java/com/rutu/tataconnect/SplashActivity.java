package com.rutu.tataconnect;



import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.rutu.tataconnect.Common.NetworkChangeListener;

public class SplashActivity extends AppCompatActivity
{

    VideoView vd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        vd = findViewById(R.id.vvEducationVideo);
       // String videoPath = "android.resource://"+getPackageName() +"/raw/tcsplash";
        String videoPath = "android.resource://" + getPackageName() + "/raw/tcsplash";

//        Uri videoUri = Uri.parse(R.raw.tcsplash);

        Uri videoUri = Uri.parse(videoPath);



        vd.setVideoURI(videoUri);

        vd.start();

       // MediaController mediaController = new MediaController(SplashActivity.this);
       // vd.setMediaController(mediaController);

        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,SplashAfterActivity.class);
                startActivity(i);
            }
        },3000);

    }





}