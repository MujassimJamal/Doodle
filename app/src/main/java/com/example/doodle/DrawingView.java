package com.example.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

public class DrawingView extends View {
    private class PathWithStyle {
        Path path;
        Paint paint;

        PathWithStyle(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }

    private Path drawPath;
    private Paint drawPaint;

    private Stack<PathWithStyle> pathHistory = new Stack<>();
    private Stack<PathWithStyle> undonePaths = new Stack<>();
    protected int lineColor = Color.RED;
    protected float strokeWidth = 10;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setStrokeWidth(strokeWidth);
        drawPaint.setColor(lineColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PathWithStyle pathWithStyle : pathHistory) {
            canvas.drawPath(pathWithStyle.path, pathWithStyle.paint);
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
                Paint pathPaint = new Paint(drawPaint);
                pathHistory.push(new PathWithStyle(new Path(drawPath), pathPaint));
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

    public void setLineStroke(float width) {
        strokeWidth = width;
        drawPaint.setStrokeWidth(strokeWidth);
    }

    public void setEraserColor(int color) {
//        lineColor = color;
        drawPaint.setColor(color);
    }

    public void setEraserStroke(float width) {
//        strokeWidth = width;
        drawPaint.setStrokeWidth(width);
    }

    public Bitmap getDrawingBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return bitmap;
    }

}
