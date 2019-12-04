package com.example.drunkbattle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView circle;
    private ViewGroup mainLayout;

    private int dx;
    private int dy;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (RelativeLayout) findViewById(R.id.reLayout);
        circle = findViewById(R.id.circleImage);

        circle.setClickable(false);

        circle.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int x = (int)motionEvent.getRawX();
                final int y = (int)motionEvent.getRawY();

                switch(motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN: {
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

                        dx = x - lParams.leftMargin;
                        dy = y - lParams.topMargin;

                    }
                    break;

                    case MotionEvent.ACTION_MOVE: {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

                        layoutParams.leftMargin = x - dx;
                        layoutParams.topMargin = y - dy;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);

                    }
                    break;
                    case MotionEvent.ACTION_UP: {


                    }
                    break;

                }
                mainLayout.invalidate();
                return true;

            }
        });

    }
}
