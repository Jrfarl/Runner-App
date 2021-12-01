package com.zybooks.runnerapp;

import android.content.Context;
import android.content.res.Resources;

public class ScoresList {

    private Scores scoreArray[];
    private static ScoresList instance;

    public static ScoresList getInstance(Context context){
        if(instance == null){
            instance = new ScoresList();
        }
        return instance;
    }

    private ScoresList(Context context){
        scoreArray = new Scores[10];
        Resources res = context.getResources();
        for(int i = 0; i < scoreArray.length; i ++){
            scoreArray[i] = new Scores(0, res.getString(R.string.default_score_name));
        }

    }

}
