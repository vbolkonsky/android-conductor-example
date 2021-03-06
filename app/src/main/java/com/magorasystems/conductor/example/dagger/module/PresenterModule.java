package com.magorasystems.conductor.example.dagger.module;

import com.magorasystems.conductor.example.application.ConductorExampleApplication;
import com.magorasystems.conductor.example.mvp.authorization.AuthorizationPresenter;
import com.magorasystems.conductor.example.mvp.authorization.AuthorizationPresenterImpl;
import com.magorasystems.conductor.example.mvp.savecatalog.SaveCatalogPresenter;
import com.magorasystems.conductor.example.mvp.savecatalog.SaveCatalogPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * @author Valentin S. Bolkonsky on 27.12.2016.
 */
@Module
public class PresenterModule {

    @Provides
    AuthorizationPresenter provideAuthorizationPresenter(ConductorExampleApplication context){
        return new AuthorizationPresenterImpl();
    }

    @Provides
    SaveCatalogPresenter provideSaveCatalogPresenter(ConductorExampleApplication context){
        return new SaveCatalogPresenterImpl();
    }
}
