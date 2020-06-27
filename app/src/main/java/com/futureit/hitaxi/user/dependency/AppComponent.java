package com.futureit.hitaxi.user.dependency;

import com.futureit.hitaxi.user.MapsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by monir.sobuj on 5/17/17.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    final class Initializer {
        private Initializer(){}

    }

    void inject(MapsActivity mapsActivity);


}
