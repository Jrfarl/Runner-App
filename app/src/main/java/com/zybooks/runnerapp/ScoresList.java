package com.zybooks.runnerapp;

import android.content.Context;
import android.content.res.Resources;

public class ScoresList {

    //Comment added to test branching

    private final Scores[] scoreArray;
    private static ScoresList instance;

    public static ScoresList getInstance(Context context){
        if(instance == null){
            instance = new ScoresList(context);
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

    public void checkScore(Scores newScore){
        for(int i = 0; i < scoreArray.length; i ++){
            if (newScore.getValue() > scoreArray[i].getValue()){
                addScore(newScore, i);
                return;
            }
        }
    }

    private void addScore(Scores newScore, int index) {
        Scores tempScore = scoreArray[index];
        scoreArray[index] = newScore;
        newScore = tempScore;

        for(int i = index; i < scoreArray.length + 1; i ++){
            tempScore = scoreArray[i+1];
            scoreArray[i+1] = newScore;
            newScore = tempScore;
        }

    }

    public Scores[] getList(){
        return scoreArray;
    }

}
