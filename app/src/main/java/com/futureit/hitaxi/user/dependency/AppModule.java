package com.futureit.hitaxi.user.dependency;


import com.futureit.hitaxi.user.App;
import com.futureit.hitaxi.user.net.APIClients;
import com.futureit.hitaxi.user.net.APIServices;
import com.futureit.hitaxi.user.net.RequestServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by monir.sobuj on 5/17/17.
 */

@Singleton
@Module
public class AppModule {

    private App app;
    private RequestServices requestServices;

    AppModule(App app, RequestServices requestServices){
        this.app = app;
        this.requestServices = requestServices;
    }

    @Provides
    @Singleton
    App provideContext(){
        return app;
    }

    @Singleton
    @Provides
    RequestServices provideRequestServices(){ return requestServices;}

    @Singleton
    @Provides
    Realm provideRealm(){
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        //return Realm.getDefaultInstance();
        return Realm.getInstance(config);
    }

    @Singleton
    @Provides
    APIServices provideAPIServices(){
        return APIClients.getInstance().create(APIServices.class);
    }

}
