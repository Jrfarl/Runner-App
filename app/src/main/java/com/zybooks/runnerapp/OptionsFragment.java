package com.zybooks.runnerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;


public class OptionsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_options, container, false);

        Button button = rootView.findViewById(R.id.generate_button);

        button.setOnClickListener(this::generateScore);

        EditText passwordEditText = rootView.findViewById(R.id.name_edit_text);

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() <5){
                    SharedPreferences sharedPref = requireContext().getSharedPreferences(
                            "gameoptions", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("name", String.valueOf(s));
                    editor.apply();
                }

            }
        });

        return rootView;
    }

    public void generateScore(View view){
        Scores score;
        Random rand = new Random();
        Resources res = view.getResources();
        int value = rand.nextInt(900) + 100;
        SharedPreferences sharedPref = requireContext().getSharedPreferences(
                "gameoptions", Context.MODE_PRIVATE);

        score = new Scores(value, sharedPref.getString("name", res.getString(R.string.default_score_name)));
        ScoresList.getInstance(requireContext()).checkScore(score);
    }

}