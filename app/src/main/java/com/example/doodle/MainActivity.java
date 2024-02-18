package com.example.doodle;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageButton paletteBtn, eraseBtn, undoBtn, redoBtn, clearBtn;
    DrawingView drawingView;
    SeekBar seekBar;
    CardView cardView;
    LineImageView lineImageView;
    MaterialButton redColorBtn, yellowColorBtn, greenColorBtn, blueColorBtn, purpleColorBtn, blackColorBtn;
    private int paletteVisibility = View.GONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawing_view);
        seekBar = findViewById(R.id.seekBar);
        lineImageView = findViewById(R.id.lineImageView);
        cardView = findViewById(R.id.cardView);

        paletteBtn = findViewById(R.id.paletteBtn);
        eraseBtn = findViewById(R.id.eraseBtn);
        undoBtn = findViewById(R.id.undoBtn);
        redoBtn = findViewById(R.id.redoBtn);
        clearBtn = findViewById(R.id.clearBtn);

        redColorBtn = findViewById(R.id.redColorBtn);
        yellowColorBtn = findViewById(R.id.yellowColorBtn);
        greenColorBtn = findViewById(R.id.greenColorBtn);
        blueColorBtn = findViewById(R.id.blueColorBtn);
        purpleColorBtn = findViewById(R.id.purpleColorBtn);
        blackColorBtn = findViewById(R.id.blackColorBtn);

        VectorDrawable paletteVD = (VectorDrawable) ContextCompat.getDrawable(MainActivity.this, R.drawable.palette);
        VectorDrawable eraseVD = (VectorDrawable) ContextCompat.getDrawable(MainActivity.this, R.drawable.erase);
        VectorDrawable redoVD = (VectorDrawable) ContextCompat.getDrawable(MainActivity.this, R.drawable.redo);
        VectorDrawable undoVD = (VectorDrawable) ContextCompat.getDrawable(MainActivity.this, R.drawable.undo);

        // Drawing menu options
        paletteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTint(paletteVD, eraseVD, undoVD, redoVD);
                paletteVD.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconClicked));
                paletteBtn.setImageDrawable(paletteVD);

                paletteVisibility = (paletteVisibility == View.GONE) ? View.VISIBLE : View.GONE;
                cardView.setVisibility(paletteVisibility);
                drawingView.setLineColor(drawingView.lineColor);
                drawingView.setLineStroke(drawingView.strokeWidth);
            }
        });
        eraseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTint(paletteVD, eraseVD, undoVD, redoVD);
                eraseVD.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconClicked));
                eraseBtn.setImageDrawable(eraseVD);
                drawingView.setEraserColor(Color.WHITE);
                drawingView.setEraserStroke(15);
                //cardView.setVisibility(View.GONE);
            }
        });
        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTint(paletteVD, eraseVD, undoVD, redoVD);
                undoVD.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconClicked));
                undoBtn.setImageDrawable(undoVD);
                drawingView.undo();
                //cardView.setVisibility(View.GONE);

            }
        });
        redoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTint(paletteVD, eraseVD, undoVD, redoVD);
                redoVD.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconClicked));
                redoBtn.setImageDrawable(redoVD);
                drawingView.redo();
                //cardView.setVisibility(View.GONE);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });


        // Colors
        // Please define colors in resource file rather than directly passing color hex code in parseColor method.
        redColorBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor(getResources().getString(R.color.red)));
                drawingView.setLineColor(Color.parseColor(getResources().getString(R.color.red)));
            }
        });

        yellowColorBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor(getResources().getString(R.color.yellow)));
                drawingView.setLineColor(Color.parseColor(getResources().getString(R.color.yellow)));
            }
        });

        greenColorBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor(getResources().getString(R.color.green)));
                drawingView.setLineColor(Color.parseColor(getResources().getString(R.color.green)));
            }
        });

        blueColorBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor(getResources().getString(R.color.blue)));
                drawingView.setLineColor(Color.parseColor(getResources().getString(R.color.blue)));
            }
        });

        purpleColorBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor(getResources().getString(R.color.purple)));
                drawingView.setLineColor(Color.parseColor(getResources().getString(R.color.purple)));
            }
        });

        blackColorBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                lineImageView.setLineColor(Color.parseColor(getResources().getString(R.color.black)));
                drawingView.setLineColor(Color.parseColor(getResources().getString(R.color.black)));
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                lineImageView.setLineStroke((i + 1) * 10);
                drawingView.setLineStroke((i + 1) * 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    protected void resetTint(VectorDrawable paletteVD, VectorDrawable eraseVD,
                            VectorDrawable undoVD, VectorDrawable redoVD) {
        paletteVD.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconLight));
        eraseVD.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconLight));
        undoVD.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconLight));
        redoVD.setTint(ContextCompat.getColor(getApplicationContext(), R.color.iconLight));

        paletteBtn.setImageDrawable(paletteVD);
        eraseBtn.setImageDrawable(eraseVD);
        undoBtn.setImageDrawable(undoVD);
        redoBtn.setImageDrawable(redoVD);
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to clear this doodle?")
                .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        drawingView.clearDrawing();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(R.drawable.rounded_alert);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.getWindow().getAttributes().y = 60;
        alertDialog.show();
    }
}