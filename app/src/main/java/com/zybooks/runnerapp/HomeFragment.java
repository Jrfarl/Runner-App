package com.zybooks.runnerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        Button button = rootView.findViewById(R.id.start_button);

        button.setOnClickListener(this::startGame);

        return rootView;
    }

    private void startGame(View view) {
        Intent intent = new Intent(HomeFragment.this.getActivity(), GameActivity.class);
        startActivity(intent);

    }

}