package com.futureit.hitaxi.user;

import android.app.Application;

import com.futureit.hitaxi.user.dependency.AppComponent;
import com.futureit.hitaxi.user.dependency.DaggerAppComponent;
import com.futureit.hitaxi.user.net.RequestServices;

import io.realm.Realm;


/**
 * Created by monir.sobuj on 28/11/2019.
 */

public class App extends Application {

    private static AppComponent appComponent;

    private static App mInstance;
    private RequestServices requestServices = new RequestServices();

    public static synchronized App getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        appComponent = DaggerAppComponent.create();
        //initialize Realm
        Realm.init(this);
    }

    public static AppComponent getComponent(){
        return appComponent;
    }

    public App getActivity(){
        return this;
    }




}
