package com.example.drunkbattle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Classe qui crée les lignes d'Obstacles et incrémente le score
 */
public class ObstacleManager {

    public static int SCORE;
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    private int score = 0;

    /**
     * Sert à detecter si le rectangle du joueur et celui de l'obstacle se sont touchés
     * @param player
     * @return si le joueur a touché la ligne
     */

    public boolean playerCollide(RectPlayer player)
    {
        for(Obstacle ob:obstacles)
        {
            if(ob.playerCollide(player))
            {
                return true;
            }

        }

        return false;
    }

    /**
     * Constructeur de Obstacle Manager
     * @param playerGap
     * @param obstacleGap
     * @param obstacleHeight
     * @param color
     */
    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color)
    {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime =  System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacle();

    }

    /**
     * Crée la position du trou où le joueur pourra passer, la position où la ligne va se créer et la ligne avec les difféernets obstacles.
     */
    private void populateObstacle()
    {
        int currY = -5* Constants.SCREEN_HEIGHT/4;

        while(currY < 0)
        {
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight,color,xStart, currY,playerGap));
            currY += obstacleHeight + obstacleGap;
        }

    }

    /**
     * S'occupe d'enlever les obstacles du ArrayList au fur et à mesure .
     * S'occupe aussi de faire que plus le temps avance plus les lignes apparaissent rapidement.
     * S'occupe d'incrémenter le score.
     */
    public  void update()
    {
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(((1 + startTime- initTime)/2000.0))*Constants.SCREEN_HEIGHT/10000.0f);

        for (Obstacle ob: obstacles) {
            ob.incrementY(speed*elapsedTime);

        }
        if(obstacles.get(obstacles.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT)
        {
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight,color,xStart,obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap ,playerGap));
            obstacles.remove(obstacles.size()-1);
            score ++;

            SCORE = score;
        }


    }

    /**
     * Fait apparaître les obstacles dans le canvas ainsi que le texte de Game over su
     * @param canvas
     */
    public void draw(Canvas canvas)
    {
        for (Obstacle ob: obstacles)
        {
            ob.draw(canvas);
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            canvas.drawText("" + score,50, 50 + paint.descent() - paint.ascent(), paint);
        }
    }

}
