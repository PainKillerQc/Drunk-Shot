package com.example.drunkbattle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject {
    private Rect rectangle;
    private Rect rectangle2;
    private int color;

/**
 * Crée les obstacles soit les rectangles noirs de chaque côtés de l'écran
 * @param rectHeight représente la hauteur des rectangles étant les obstacles
 * @param color Définit la couleur des rectangles
 * @param startX représente la position initiale des triangles en X
 * @param startY représente la position initiale des triangles en Y
 * @param playerGap représente l'espace entre les deux obstacles
 */
    public Obstacle (int rectHeight, int color, int startX, int startY, int playerGap)
    {
        this.color = color;
        rectangle = new Rect(0,startY,startX,startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap, startY,Constants.SCREEN_WIDTH,startY+ rectHeight);
    }

    /**
     * Est utilisée dans la fonction update d'obstacle manager afin de modifier la potition en y des obstacles
     * @param y représente la heuteur qui doit être ajoutée (la hauteur va du haut au bas de l'écran, c'est pourquoi elle est ajoutée)
     */
    public void incrementY(float y)
    {
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;

    }

    /**
     * Est utilisée dans la fonction update d'obstacle manager afin d'accéder à l'objet rectangle
     * @return retourne l'obstacle de gauche
     */
    public Rect getRectangle()
    {
        return rectangle;
    }

    /**
     * Permet de retourner une valeur boolean lorsque le joueur entre en contact avec un obstacle
     * @param player représente le carré rouge étant le joueur
     * @return retourne la valeur boolean true lorsque le joueur touche un obstacle
     */
    public boolean playerCollide(RectPlayer player)
    {
        return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle());



    }

    /**
     * Permet d'insérer les obstacles dans la page en leur donnant aussi leur couleur
     * @param canvas est l'endroit ou l'on draw les rectangles
     */
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2, paint);
    }

    @Override
    public void update() {

    }
}
