package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.displayjokeslib.DisplayJokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity implements EndPointAsyncTask.EndPointListener {

    private EndPointAsyncTask mEndPointAysncTask;
    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;
    private String mJokeRetrieved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize async task
        mEndPointAysncTask = new EndPointAsyncTask();
        // Attach it's listener to this activity
        mEndPointAysncTask.endPointListener = this;


        //Initialize the interstitialAs object to be used latter
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitialAdUnitId));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //Set an Adlistener
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                Intent displayJokeIntent = new Intent(MainActivity.this,DisplayJokeActivity.class);
                displayJokeIntent.putExtra(Intent.EXTRA_TEXT,mJokeRetrieved);
                startActivity(displayJokeIntent);
            }
        });

        //Instantiate progress bar
        mProgressBar = findViewById(R.id.progressBar);



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
        // Show the progressbar
        mProgressBar.setVisibility(View.VISIBLE);

        mEndPointAysncTask.execute();

    }


    @Override
    public void jokeAvailable(String joke) {
        // Stop loading
        mProgressBar.setVisibility(View.INVISIBLE);
        // Show the ad
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", getString(R.string.failed_load_message));
        }

        mJokeRetrieved = joke;


    }
}
