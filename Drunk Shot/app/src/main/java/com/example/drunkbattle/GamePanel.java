package com.example.drunkbattle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Classe qui est la view principal du jeu
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final String SCORE = "SCORE";
    private MainThread thread;
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private Rect r = new Rect();
    private boolean movingPlayer = false;
    private boolean gameOver = false;

    private long gameOverTime;

    /**
     * Constructeur de la view qui reçoit en argument le contexte de l'activité
     * @param context
     */
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

    /**
     * Constructeur de base la view pour que l'on puisse le mettre dans le layout
     * @param context
     */
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

    /**
     * Constructeur de base la view pour que l'on puisse le mettre dans le layout
     * @param context
     * @param attrs
     */
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


    /**
     * Est appellée lorsque la surface est créée et débute le thread
     * @param holder
     */

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(),this);

        thread.setRunning(true);
        thread.start();
    }

    /**
     * Est appellée lorsque à chaque fois que les objets dans la view se déplacent
     * @param holder
     * @param format
     * @param width
     * @param height
     */
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

    /**
     * Est appellé lorsque le joueur touche l'écran
     * @param event
     * @return
     */
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

    /**
     * Met le joueur au bon endroit et les obstacles
     */
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

    /**
     * Dessine sur tous les objets sur le canvas sauf lw joueur
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas)
    {
        if(canvas != null)
        {
            super.draw(canvas);

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

    /**
     * Dessine le Joueur dans le canvas
     * @param canvas
     */
    public void drawPlayer(Canvas canvas)
    {
        player.draw(canvas);
    }

    /**
     * Fonction qui dessine le texte lorsque Game Over
     * @param canvas
     * @param paint
     */
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
