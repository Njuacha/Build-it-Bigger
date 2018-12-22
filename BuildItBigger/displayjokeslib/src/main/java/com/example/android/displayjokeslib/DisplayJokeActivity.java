package com.example.android.displayjokeslib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        TextView jokeTextView = findViewById(R.id.joke_text_view);

        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_TEXT)){
            String joke = intent.getStringExtra(Intent.EXTRA_TEXT);
            jokeTextView.setText(joke);
        }
    }
}
