package com.zybooks.runnerapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class ScoresFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scores, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.scores_list);
        Scores[] scoreArray = ScoresList.getInstance(requireContext()).getList();
        recyclerView.setAdapter(new ScoreAdapter(scoreArray));

        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        // Inflate the layout for this fragment
        return rootView;

    }

    private static class ScoreAdapter extends RecyclerView.Adapter<ScoreHolder> {

        private final Scores[] mScoreArray;

        public ScoreAdapter(Scores[] scoreArray) {
            mScoreArray = scoreArray;
        }

        @NonNull
        @Override
        public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.scores_list_item, parent, false);
            return new ScoreHolder(view);
        }

        @Override
        public void onBindViewHolder(ScoreHolder holder, final int position) {
            Scores score = mScoreArray[position];
            holder.getPositionTextView().setText(Integer.toString(position + 1));
            holder.getNameTextView().setText(score.getName());
            holder.getValueTextView().setText(Integer.toString(score.getValue()));
        }

        @Override
        public int getItemCount() {
            return mScoreArray.length;
        }
    }

    private static class ScoreHolder extends RecyclerView.ViewHolder {

        private final TextView mPositionTextView;
        private final TextView mNameTextView;
        private final TextView mValueTextView;

        public ScoreHolder(View view) {
            super(view);
            mPositionTextView = itemView.findViewById(R.id.score_placement);
            mNameTextView = itemView.findViewById(R.id.score_name);
            mValueTextView = itemView.findViewById(R.id.score_value);
        }

        public TextView getPositionTextView(){
            return mPositionTextView;
        }

        public TextView getNameTextView(){
            return mNameTextView;
        }

        public TextView getValueTextView(){
            return mValueTextView;
        }
    }
}