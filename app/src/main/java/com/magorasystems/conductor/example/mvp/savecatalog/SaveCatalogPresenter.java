package com.magorasystems.conductor.example.mvp.savecatalog;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Developed 2016.
 *
 * @author Valentin S.Bolkonsky
 */

public interface SaveCatalogPresenter extends MvpPresenter<SaveCatalogView> {

    void save();
}
