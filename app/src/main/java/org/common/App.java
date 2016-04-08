package org.common;

import android.content.Context;

import org.common.http.ApiService;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by peter on 2016/3/28.
 */
public class App extends org.commom.library.App {

    private Scheduler defaultSubscribeScheduler;

    private ApiService service;

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

    public ApiService getApiService() {
        if (service == null) {
            service = ApiService.Factory.create();
        }
        return service;
    }
}
