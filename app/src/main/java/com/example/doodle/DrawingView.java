package com.example.doodle;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
        drawPaint.setColor(color);
    }

    public void setEraserStroke(float width) {
        drawPaint.setStrokeWidth(width);
    }

    protected Bitmap getBitmap() {
        /// Get Bitmap
        Bitmap bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        this.draw(canvas);
        return bitmap;
    }

    public boolean saveBitmapToFile(Context applicationContext) {
        // Check if external storage is available
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(applicationContext, "External storage is not available", Toast.LENGTH_SHORT).show();
            return false;
        }

        File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File doodleFolder = new File(pictureFolder, "Doodle");

        if (!doodleFolder.exists()) {
            if (!doodleFolder.mkdirs()) {
                Toast.makeText(applicationContext, "Failed to create Doodle folder", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        // Set unique filename
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String timestamp = formatter.format(new Date());
        String filename = "Doodle_" + timestamp + ".png";
        File file = new File(doodleFolder, filename);

        // Save doodle to storage
        boolean success;
        try (FileOutputStream out = new FileOutputStream(file)) {
            Bitmap bitmap = this.getBitmap();
            success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }
}
