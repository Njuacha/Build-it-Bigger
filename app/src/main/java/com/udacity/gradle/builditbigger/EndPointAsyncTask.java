package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


public class EndPointAsyncTask extends AsyncTask<Void,Void,String> {

    private static MyApi myApiService = null;

    public interface EndPointListener{
        void jokeAvailable(String joke);
    }

    public EndPointListener endPointListener = null;

    @Override
    protected String doInBackground(Void... voids) {
        if (myApiService == null){ // So that it only does this ones
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.joke().execute().getData();
        }catch (IOException e){
            Log.d(getClass().getSimpleName(),e.getMessage());
            return "";
        }

    }

    @Override
    protected void onPostExecute(String s) {
        endPointListener.jokeAvailable(s);
    }
}
