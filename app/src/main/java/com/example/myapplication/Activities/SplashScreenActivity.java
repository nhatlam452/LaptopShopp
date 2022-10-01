package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.myapplication.R;


public class SplashScreenActivity extends AppCompatActivity {
    private static final int splash_timeout = 3;
    ImageView img;
    Animation animation;
    final Rect viewRect = new Rect();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        img = findViewById(R.id.imageView);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.silde_down);
        img.startAnimation(animation);



    }
}