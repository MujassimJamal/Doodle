package com.example.doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

public class DrawingView extends View {
    private Path drawPath;
    private Paint drawPaint;

    private int lineColor = Color.RED;
    private final Stack<Path> pathHistory = new Stack<>();
    private final Stack<Path> undonePaths = new Stack<>();

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(lineColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Path path : pathHistory) {
            canvas.drawPath(path, drawPaint);
        }
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                undonePaths.clear();
                drawPath.reset();
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                pathHistory.push(new Path(drawPath));
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void undo() {
        if (!pathHistory.isEmpty()) {
            undonePaths.push(pathHistory.pop());
            invalidate();
        }
    }

    public void redo() {
        if (!undonePaths.isEmpty()) {
            pathHistory.push(undonePaths.pop());
            invalidate();
        }
    }

    public void clearDrawing() {
        pathHistory.clear();
        undonePaths.clear();
        drawPath.reset();
        invalidate();
    }

    public void setLineColor(int color) {
        lineColor = color;
        drawPaint.setColor(lineColor);
    }
}
