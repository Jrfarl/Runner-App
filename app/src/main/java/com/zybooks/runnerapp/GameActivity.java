package com.zybooks.runnerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.q42.android.scrollingimageview.ScrollingImageView;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    SoundPool soundPool;
    SoundPool.Builder soundPoolBuilder;

    AudioAttributes attributes;
    AudioAttributes.Builder attributesBuilder;

    int soundID_jump;
    int soundID_death;
    int jumpTime = 0;
    int scoreTime = 0;
    Date endTime;
    Date currentTime;
    Date startTime;
    long finalScoreLong;
    int finalScore;
    boolean first_run = true;
    boolean front_of_log = true;

    //--------------------------------------------------------------------------------+++
    private BackgroundProcess mBackgroundProcess;
    private Handler mHandler;
    //--------------------------------------------------------------------------------

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

        //---------------------------------------------------------------------------------+++


        mBackgroundProcess = new BackgroundProcess();
        mHandler = new Handler(Looper.getMainLooper());

        mBackgroundProcess.start();
        mHandler.post(mUpdateTimerRunnable);


        //-----------------------------------------------------------------------------------

        attributesBuilder = new AudioAttributes.Builder();
        attributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
        attributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        attributes = attributesBuilder.build();

        soundPoolBuilder = new SoundPool.Builder();
        soundPoolBuilder.setAudioAttributes(attributes);
        soundPool = soundPoolBuilder.build();

        soundID_jump = soundPool.load(this, R.raw.jump_sound, 1);
        soundID_death = soundPool.load(this, R.raw.death_sound, 1);
    }

    public void jump(View view){
        if(jumpTime == 0) {
            jumpTime=1;
            ImageView imageView = (ImageView) findViewById(R.id.squirrel_image);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.jump_anim);
            //Jump sound
            soundPool.play(soundID_jump,1,1,0,0,1);
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

        Resources res = view.getResources();

        SharedPreferences sharedPref = this.getSharedPreferences(
                "gameoptions", Context.MODE_PRIVATE);

        Scores score = new Scores(finalScore, sharedPref.getString("name",
                res.getString(R.string.default_score_name)));
        ScoresList.getInstance(this).checkScore(score);

        finish();
    }

    //-----------------------------------------------------------------------+++


    private final Runnable mUpdateTimerRunnable = new Runnable() {
        @Override
        public void run() {


            int timerTime = mBackgroundProcess.getmTime();


            if((!(jumpTime==1)) && (!(timerTime==0))){
                ImageView imageView = (ImageView) findViewById(R.id.log_image);
                //Death Sound
                soundPool.play(soundID_death,1,1,0,0,1);
                endGame(imageView);
            }
            else {
                currentTime = new Date();
                mBackgroundProcess.setmCurrentTime(currentTime);
                if(first_run == true){
                    mHandler.postDelayed(this, 1900); //1900
                    first_run = false;
                }
                else {
                    if(front_of_log==true) {
                        mHandler.postDelayed(this, 470); //+20
                        front_of_log = false;
                    }
                    else{
                        mHandler.postDelayed(this, 2541); // Too big:2562   Too small:2560  -20
                        front_of_log = true;
                    }
                }
            }
        }
    };

//-----------------------------------------------------------------------

}