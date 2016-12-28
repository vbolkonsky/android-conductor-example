package com.magorasystems.conductor.example.dagger;

import com.magorasystems.conductor.example.dagger.module.ApplicationModule;
import com.magorasystems.conductor.example.dagger.module.PresenterModule;
import com.magorasystems.conductor.example.mvp.authorization.AuthorizationPresenter;
import com.magorasystems.conductor.example.mvp.savecatalog.SaveCatalogPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Valentin S. Bolkonsky on 27.12.2016.
 */
@Component(modules = {ApplicationModule.class, PresenterModule.class})
@Singleton
public interface MainComponent {

    AuthorizationPresenter authorizationPresenter();

    SaveCatalogPresenter saveCatalogPresenter();

    //void inject(AuthorizationController controller);
}
