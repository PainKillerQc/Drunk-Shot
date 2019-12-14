package com.example.drunkbattle;

import android.graphics.Canvas;

/**
 * Classe qui contient les composantes graphiques de Game Object
 */
public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
