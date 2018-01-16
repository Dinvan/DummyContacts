package com.info.dummycontacts;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by advanz101 on 16/1/18.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
    }
}
