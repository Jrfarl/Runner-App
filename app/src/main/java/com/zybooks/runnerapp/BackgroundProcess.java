package com.zybooks.runnerapp;

import android.os.SystemClock;
import java.util.Locale;
import java.util.Date;

public class BackgroundProcess {

    private boolean mRunning;
    private int mTime;
    private GameActivity mGameActivity = new GameActivity();
    private Date mStartTime;
    private Date mCurrentTime;
    private long mTimePastLong;
    private int mTimePastInt;




    public BackgroundProcess() {
        mRunning = false;
    }

    public boolean isRunning(){
        return mRunning;
    }

    public void start() {
        mStartTime = new Date();
        mRunning = true;
    }

    public void stop(){
        mRunning = false;
    }

    public int getmTime(){
        mCurrentTime = new Date();
        mTimePastLong = mCurrentTime.getTime()-mStartTime.getTime();
        mTimePastLong = mTimePastLong/1000;
        mTimePastInt = (int)mTimePastLong;
        mTime = mTimePastInt;
        return mTime;
    }

    public void setmCurrentTime(Date b){
        mCurrentTime = b;
    }
}
