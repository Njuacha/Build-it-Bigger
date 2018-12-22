package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.displayjokeslib.DisplayJokeActivity;


public class MainActivityPaid extends AppCompatActivity implements EndPointAsyncTask.EndPointListener {

    private EndPointAsyncTask mEndPointAysncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_paid);
        // Initialize async task
        mEndPointAysncTask = new EndPointAsyncTask();
        // Attach it's listener to this activity
        mEndPointAysncTask.endPointListener = this;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Gets the joke from java library for jokes and passes as an intent extra to the android library activity to display
    // this joke
    public void tellJoke(View view) {
        mEndPointAysncTask.execute();
    }


    @Override
    public void jokeAvailable(String joke) {
        Intent displayJokeIntent = new Intent(MainActivityPaid.this,DisplayJokeActivity.class);
        displayJokeIntent.putExtra(Intent.EXTRA_TEXT,joke);
        startActivity(displayJokeIntent);
    }
}
