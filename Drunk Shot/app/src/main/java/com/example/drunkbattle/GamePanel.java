package com.example.drunkbattle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final String SCORE = "SCORE";
    private MainThread thread;
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private Rect r = new Rect();
    private boolean movingPlayer = false;
    private boolean gameOver = false;

    //private SceneManager manager;

    private long gameOverTime;

    public GamePanel(Context context) {
        super(context);

        this.setZOrderOnTop(true);
        SurfaceHolder surfaceHolderMainSV = this.getHolder();
        surfaceHolderMainSV.setFormat(PixelFormat.TRANSPARENT);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(),this);


        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update();

        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);

        setFocusable(true);

    }

    public GamePanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub

        this.setZOrderOnTop(true);
        SurfaceHolder surfaceHolderMainSV = this.getHolder();
        surfaceHolderMainSV.setFormat(PixelFormat.TRANSPARENT);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(),this);

        Constants.CURRENT_CONTEXT = context;

        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update();

        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);

        setFocusable(true);
    }

    public GamePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        this.setZOrderOnTop(true);
        SurfaceHolder surfaceHolderMainSV = this.getHolder();
        surfaceHolderMainSV.setFormat(PixelFormat.TRANSPARENT);

        getHolder().addCallback(this);


        thread = new MainThread(getHolder(),this);

        Constants.CURRENT_CONTEXT = context;

        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update();

        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);

        setFocusable(true);
    }

    public void reset()
    {
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);
        movingPlayer = false;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(),this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
            try
            {
                thread.setRunning(false);


                thread.join();
                thread.interrupt();

            }catch (Exception e){
                e.printStackTrace();
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int) event.getY()))
                    movingPlayer = true;
                if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000)
                {

                    Intent intent = new Intent(getContext(), Activity_Score.class);

                    intent.putExtra(SCORE, String.valueOf(ObstacleManager.SCORE));

                    getContext().startActivity(intent);

                    ((Activity)getContext()).finish();
                }
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer) {
                    playerPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
        return true;
    }

    public void update()
    {
        if(!gameOver) {
            player.update(playerPoint);
            obstacleManager.update();
            if(obstacleManager.playerCollide(player))
            {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }


    }

    @Override
    public void draw(Canvas canvas)
    {
        if(canvas != null)
        {
            super.draw(canvas);

            //player.draw(canvas);

            obstacleManager.draw(canvas);
        }



        if(gameOver)
        {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas,paint);
        }
    }

    public void drawPlayer(Canvas canvas)
    {
        player.draw(canvas);
    }

    private void drawCenterText(Canvas canvas, Paint paint) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds("Game Over", 0, "Game Over".length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText("Game Over", x, y, paint);
    }
}
