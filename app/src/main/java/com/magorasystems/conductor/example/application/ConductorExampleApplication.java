package com.magorasystems.conductor.example.application;

import android.app.Application;
import android.content.Context;

import com.magorasystems.conductor.example.dagger.DaggerMainComponent;
import com.magorasystems.conductor.example.dagger.MainComponent;
import com.magorasystems.conductor.example.dagger.module.ApplicationModule;
import com.magorasystems.conductor.example.dagger.module.PresenterModule;


/**
 * @author Valentin S. Bolkonsky on 27.12.2016.
 */

public class ConductorExampleApplication extends Application {

    private MainComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerMainComponent
                .builder().applicationModule(new ApplicationModule(this))
                .presenterModule(new PresenterModule())
                .build();
    }

    public static ConductorExampleApplication get(Context context){
        return (ConductorExampleApplication)context.getApplicationContext();
    }

    public MainComponent getComponent(){
        return component;
    }
}
