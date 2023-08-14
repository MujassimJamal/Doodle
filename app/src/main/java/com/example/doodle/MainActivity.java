package com.example.doodle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    ImageButton pallete, erase, undo, redo, clear;
    DrawingView drawingView;
    SeekBar seekbar;

    CardView cardView;

    LineImageView lineImageView;
    MaterialButton redBtn, yellowBtn, greenBtn, blueBtn, purpleBtn, blackBtn;

    private int palleteVisibility = View.GONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawing_view);
        seekbar = findViewById(R.id.seekBar);
        lineImageView = findViewById(R.id.lineImageView);
        cardView = findViewById(R.id.cardView);

        pallete = findViewById(R.id.pallete);
        erase = findViewById(R.id.erase);
        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);
        clear = findViewById(R.id.clear);

        redBtn = findViewById(R.id.redColor);
        yellowBtn = findViewById(R.id.yellowColor);
        greenBtn = findViewById(R.id.greenColor);
        blueBtn = findViewById(R.id.blueColor);
        purpleBtn = findViewById(R.id.purpleColor);
        blackBtn = findViewById(R.id.blackColor);

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

                if (palleteVisibility == View.GONE) {
                    palleteVisibility = View.VISIBLE;
                } else {
                    palleteVisibility = View.GONE;
                }

                cardView.setVisibility(palleteVisibility);

            }
        });
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfillAll(brush, eraser, arrow_left, arrow_right);
                eraser.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconFocused));
                erase.setImageDrawable(eraser);

                // Hide CardView
                cardView.setVisibility(View.GONE);

            }
        });
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfillAll(brush, eraser, arrow_left, arrow_right);
                arrow_left.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconFocused));
                undo.setImageDrawable(arrow_left);

                drawingView.undo();

                cardView.setVisibility(View.GONE);

            }
        });
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unfillAll(brush, eraser, arrow_left, arrow_right);
                arrow_right.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconFocused));
                redo.setImageDrawable(arrow_right);

                drawingView.redo();

                cardView.setVisibility(View.GONE);

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
                cardView.setVisibility(View.GONE);
            }
        });

        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor("#FF0000"));
            }
        });

        // Colors
        yellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor("#FFE500"));
                drawingView.setLineColor(Color.parseColor("#FFE500"));
            }
        });

        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor("#00FF0A"));
                drawingView.setLineColor(Color.parseColor("#00FF0A"));
            }
        });

        blueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor("#00B0FF"));
                drawingView.setLineColor(Color.parseColor("#00B0FF"));
            }
        });

        purpleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor("#DA00FF"));
                drawingView.setLineColor(Color.parseColor("#DA00FF"));
            }
        });

        blackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor("#000000"));
                drawingView.setLineColor(Color.parseColor("#000000"));
            }
        });

        // Seekbar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                lineImageView.setLineStroke((i + 1) * 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to clear this doodle?")
                .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        drawingView.clearDrawing();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Handle Cancel button click
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(alertDialog.getWindow().getAttributes());
        layoutParams.gravity = Gravity.BOTTOM;
        alertDialog.getWindow().setAttributes(layoutParams);
    }
}