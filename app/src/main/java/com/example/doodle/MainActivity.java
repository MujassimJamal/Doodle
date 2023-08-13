package com.example.doodle;

import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton pallete, erase, undo, redo, clear;
    DrawingView drawingView;
    VectorDrawable brush, eraser, arrow_right, arrow_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawing_view);
        pallete = findViewById(R.id.pallete);
        erase = findViewById(R.id.erase);
        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);
        clear = findViewById(R.id.clear);

        VectorDrawable brush = (VectorDrawable) ContextCompat.getDrawable(MainActivity.this, R.drawable.brush);
        VectorDrawable eraser = (VectorDrawable) ContextCompat.getDrawable(MainActivity.this, R.drawable.eraser);
        VectorDrawable arrow_right = (VectorDrawable) ContextCompat.getDrawable(MainActivity.this, R.drawable.arrow_right);
        VectorDrawable arrow_left = (VectorDrawable) ContextCompat.getDrawable(MainActivity.this, R.drawable.arrow_left);

        pallete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfillAll(brush, eraser, arrow_left, arrow_right);
                brush.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconFocused));
                pallete.setImageDrawable(brush);

            }
        });
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfillAll(brush, eraser, arrow_left, arrow_right);
                eraser.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconFocused));
                erase.setImageDrawable(eraser);

            }
        });
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfillAll(brush, eraser, arrow_left, arrow_right);
                arrow_left.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconFocused));
                undo.setImageDrawable(arrow_left);

                drawingView.undo();

            }
        });
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfillAll(brush, eraser, arrow_left, arrow_right);
                arrow_right.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconFocused));
                redo.setImageDrawable(arrow_right);

                drawingView.redo();

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.clearDrawing();
            }
        });
    }

    protected void unfillAll(VectorDrawable brush, VectorDrawable eraser, VectorDrawable arrow_left, VectorDrawable arrow_right) {
        brush.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconLight));
        eraser.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconLight));
        arrow_left.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconLight));
        arrow_right.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconLight));
        pallete.setImageDrawable(brush);
        erase.setImageDrawable(eraser);
        undo.setImageDrawable(arrow_left);
        redo.setImageDrawable(arrow_right);

    }
}