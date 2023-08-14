package com.example.doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class LineImageView extends AppCompatImageView {
    private Paint linePaint;
    private int lineColor = Color.RED;

    public LineImageView(Context context) {
        super(context);
        init();
    }

    public LineImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(lineColor); // Set your desired line color
        linePaint.setStrokeWidth(10); // Set your desired line width
        linePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerY = getHeight() / 2;
        canvas.drawLine(0, centerY, getWidth(), centerY, linePaint);
    }

    public void setLineColor(int color) {
        lineColor = color;
        linePaint.setColor(lineColor);
        invalidate(); // Force redraw
    }
}
