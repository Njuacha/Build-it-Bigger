package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest implements EndPointAsyncTask.EndPointListener {

    @Test
    public void endPointAsyncTaskReturnsA_nonEmptyString() {
        EndPointAsyncTask endPointAsyncTask = new EndPointAsyncTask();
        endPointAsyncTask.endPointListener = this;
        endPointAsyncTask.execute();

    }

    @Override
    public void jokeAvailable(String joke) {
        assertEquals(true,!TextUtils.isEmpty(joke));
    }
}