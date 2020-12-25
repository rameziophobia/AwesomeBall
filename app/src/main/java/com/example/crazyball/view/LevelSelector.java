package com.example.crazyball.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.crazyball.R;

public class LevelSelector extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);

    }
}