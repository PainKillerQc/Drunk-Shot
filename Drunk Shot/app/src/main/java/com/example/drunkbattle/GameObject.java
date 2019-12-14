package com.example.drunkbattle;

import android.graphics.Canvas;

/**
 * Classe qui contient les composantes graphiques de Game Object
 */
public interface GameObject {
    void draw(Canvas canvas);
    void update();
}
