package com.example.george.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

public class GeneralFragment extends Fragment {
    TextView progressPercentage;
    ProgressBar listProgressBar;
    ListActivity listActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_general, container, false);
        //buttons, switches
        Button learnButton = (Button) rootView.findViewById(R.id.learnButton);
        Button addButton = (Button) rootView.findViewById(R.id.addButton);
        Button resetButton = (Button) rootView.findViewById(R.id.resetLearningProgressButton);

        listActivity = (ListActivity) getActivity();
        learnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicFunctions.openActivityForResult(getActivity(), LearnActivity.class,
                        listActivity.getList_name());
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicFunctions.openActivityForResult(getActivity(), AddActivity.class,
                        listActivity.getList_name());
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(listActivity);
                dbHelper.resetLearningProcess(listActivity.getList_name());
                updateProgress(listActivity.getSize(), 0);
            }
        });

        //other
        progressPercentage = (TextView) rootView.findViewById(R.id.progress_percentage);
        listProgressBar = (ProgressBar) rootView.findViewById(R.id.listProgressBar);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        updateProgress(listActivity.getSize(), listActivity.getProgress());
    }

    public void updateProgress(int max, int progress) {
        //learning progress
        listProgressBar.setMax(max);
        listProgressBar.setProgress(progress);

        //total words percentage
        String progressPercentageText = progress + "/" + max;
        progressPercentage.setText(progressPercentageText);
    }


}
