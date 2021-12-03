package com.zybooks.runnerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.q42.android.scrollingimageview.ScrollingImageView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    int jumpTime = 0;
    int scoreTime = 0;
    Date endTime;
    Date startTime;
    long finalScoreLong;
    int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        ConstraintLayout screen = (ConstraintLayout) findViewById(R.id.game_screen);

        screen.setOnClickListener(this::jump);

        ScrollingImageView scrollingBackground = (ScrollingImageView) findViewById(R.id.scrolling_background_game);
        scrollingBackground.start();
        roll();

        ImageView imageView = findViewById(R.id.squirrel_image);
        Glide.with(this).load(R.drawable.animated_squirrel).into(imageView);
    }

    public void jump(View view){
        if(jumpTime == 0) {
            jumpTime++;
            ImageView imageView = (ImageView) findViewById(R.id.squirrel_image);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.jump_anim);
            imageView.startAnimation(animation);
            Timer jumpTimer = new Timer();
            TimerTask task = new TimerTask(){
                @Override
                public void run(){
                    jumpTime=0;}
            };
            jumpTimer.schedule(task, 1000);
        }
    }

    public void roll() {

        startTime = new Date();
        ImageView imageView = (ImageView) findViewById(R.id.log_image);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.roll_anim);
        imageView.startAnimation(animation);
    }

    public void endGame(View view){

        ImageView imageView = (ImageView) findViewById(R.id.log_image);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.roll_anim);
        imageView.clearAnimation();
        endTime = new Date();
        finalScoreLong = endTime.getTime()-startTime.getTime();
        finalScore = (int)finalScoreLong;

        SharedPreferences sharedPref = this.getSharedPreferences(
                "gameoptions", Context.MODE_PRIVATE);

        Scores score = new Scores(finalScore, sharedPref.getString("name",
                String.valueOf(R.string.default_score_name)));
        ScoresList.getInstance(this).checkScore(score);

        finish();

    }
}