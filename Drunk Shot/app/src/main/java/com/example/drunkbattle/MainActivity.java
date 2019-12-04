package com.example.drunkbattle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(new GamePanel(this));


       // MainThread mt = new MainThread( , new GamePanel(this));
        //mt.start();


    }
}
