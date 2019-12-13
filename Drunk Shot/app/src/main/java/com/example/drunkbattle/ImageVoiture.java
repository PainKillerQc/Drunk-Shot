package com.example.drunkbattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageVoiture {
    private static Bitmap m_bitmap;

    public ImageVoiture(Bitmap bitmap)
    {
        m_bitmap=bitmap;
    }

    void draw(Canvas canvas, Rect rectangle)
    {
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);

        if(canvas != null)
        {
            canvas.drawBitmap(m_bitmap,rectangle.left, rectangle.top,paint);
//            Paint paint = new Paint();
//            paint.setColor(color);

            //canvas.drawRect(rectangle,paint);
        }
        else if(canvas == null)
        {
            //canvas.drawRect(rectangle,paint);
        }


    }
}
