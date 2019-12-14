package com.example.drunkbattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;
    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager animManager;

    /**
     * Permet aux autres classes de travailler sur l'objet joueur
     * @return l'objet rect représentnant le carré rouge déplacé par l'utilisateur
     */
    public Rect getRectangle()
    {
        return rectangle;
    }

    /**
     * Permet de créer le carré rouge représentant le joueur
     * Définie la couleur et les animations effectuées par le carré lorsqu'il est déplacé
     * @param rectangle représente le carré du joueur
     * @param color Représente la couleur donnée au carré
     */
    public RectPlayer(Rect rectangle, int color)
    {
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cybertruckmini);
        Bitmap walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cybertruckmini);
        Bitmap walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.cybertruckmini);

        idle = new Animation(new Bitmap[]{idleImg}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);

        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        animManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});

    }

    /**
     * Permet d'afficher le carré dans la view
     * @param canvas Représente la view dans laquelle le carré est affiché
     */
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);

        canvas.drawRect(rectangle,paint);


    }

    @Override
    public void update() {
    }

    /**
     * Permet de calculer les bordures du carré afin de créer les animations
     * @param point Représente les coordonées du centre du carré
     */
    public void update(Point point)
    {
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);

        int state = 0;
        if(rectangle.left - oldLeft > 5)
            state = 1;
        else if(rectangle.left - oldLeft < -5)
            state = 2;

        animManager.playAnim(state);
        animManager.update();
        }
}
