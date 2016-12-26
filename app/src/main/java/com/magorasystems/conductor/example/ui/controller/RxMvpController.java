package com.magorasystems.conductor.example.ui.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bluelinelabs.conductor.rxlifecycle2.RxController;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.conductor.delegate.MvpConductorDelegateCallback;
import com.hannesdorfmann.mosby.mvp.conductor.delegate.MvpConductorLifecycleListener;

/**
 * Developed 2016.
 *
 * @author Valentin S.Bolkonsky
 */

public abstract class RxMvpController<V extends MvpView, P extends MvpPresenter<V>> extends RxController
        implements MvpView, MvpConductorDelegateCallback<V, P> {

    protected P presenter;

    // Initializer block
    {
        addLifecycleListener(getMosbyLifecycleListener());
    }

    public RxMvpController() {
        this(null);
    }

    public RxMvpController(Bundle args) {
        super(args);
    }

    /**
     * This method is for internal purpose only.
     * <p><b>Do not override this until you have a very good reason</b></p>
     *
     * @return Mosby's lifecycle listener so that
     */
    protected LifecycleListener getMosbyLifecycleListener() {
        return new MvpConductorLifecycleListener<V, P>(this);
    }

    @Nullable
    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public V getMvpView() {
        return (V) this;
    }
}
