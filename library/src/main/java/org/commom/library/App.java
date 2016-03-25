package org.commom.library;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by peter on 2016/3/20.
 */
public class App extends Application {

    private Scheduler defaultSubscribeScheduler;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }


    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}
