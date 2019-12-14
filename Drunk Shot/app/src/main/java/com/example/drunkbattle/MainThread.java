package com.example.drunkbattle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private static final int MAX_FPS = 60;
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    Canvas canvas;



    public void setRunning(boolean running)
    {
        this.running = running;

    }

    MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run()
    {

        long startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;


        while(running)
        {
            startTime = System.nanoTime();
            canvas = null;


            try
            {

                synchronized (surfaceHolder)
                {
                    canvas = this.surfaceHolder.lockCanvas();
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);

                    // reset the canvas to blank at the start
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    this.gamePanel.draw(canvas);

                    if(canvas != null)
                    {
                        this.gamePanel.drawPlayer(canvas);
                    }
                }

            }
            catch (Exception e ){
                e.printStackTrace();
            }
            finally {
                if(canvas != null)
                {
                    try
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);

                    }
                    catch (Exception e)
                    {
                    e.printStackTrace();
                    }
                }
                timeMillis = (System.nanoTime() - startTime)/1000000;
                waitTime = targetTime - timeMillis;
                try
                {
                    if(waitTime > 0)
                    {
                        sleep(waitTime);
                    }

                }
                catch(Exception ignored)
                {

                }

                totalTime += System.nanoTime() - startTime;
                frameCount++;
                if(frameCount == MAX_FPS)
                {
                    double averageFPS = 1000 / ((totalTime / frameCount / 1000000));
                    frameCount = 0;
                    totalTime = 0;
                    System.out.println(averageFPS);
                }
            }

        }

    }



}
