package com.example.drunkbattle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class MainActivity extends Activity {
    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;


        MediaPlayer music = MediaPlayer.create(MainActivity.this,R.raw.car_chase);
        music.setLooping(true);
        music.start();

        SurfaceView mainSurfaceView = findViewById(R.id.surfaceView);

        LinearLayout mainLayout = findViewById(R.id.container);

        final int random = new Random().nextInt(4);



        switch (random)
        {
            case 0: Drawable drawable0 = ContextCompat.getDrawable(this,R.drawable.backgroundroad);
                mainLayout.setBackground(drawable0);

            case 1:Drawable drawable1 = ContextCompat.getDrawable(this,R.drawable.backgroundroad);
                mainLayout.setBackground(drawable1);

            case 2:Drawable drawable2 = ContextCompat.getDrawable(this,R.drawable.backgroundroad);
                mainLayout.setBackground(drawable2);

            case 3:Drawable drawable3 = ContextCompat.getDrawable(this,R.drawable.backgroundroad);
                mainLayout.setBackground(drawable3);
        }


//        sfvTrack.setZOrderOnTop(true);    // necessary
//        SurfaceHolder sfhTrackHolder = sfvTrack.getHolder();
//        sfhTrackHolder.setFormat(PixelFormat.TRANSPARENT);


        setContentView(R.layout.activity_main);

    }
}
