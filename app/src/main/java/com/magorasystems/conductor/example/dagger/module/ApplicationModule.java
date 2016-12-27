package com.magorasystems.conductor.example.dagger.module;

import android.content.Context;

import com.magorasystems.conductor.example.application.ConductorExampleApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Valentin S. Bolkonsky on 27.12.2016.
 */
@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    ConductorExampleApplication provideContext(){
        return (ConductorExampleApplication) context;
    }
}
