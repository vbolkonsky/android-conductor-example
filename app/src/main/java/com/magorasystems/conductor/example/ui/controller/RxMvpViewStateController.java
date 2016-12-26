package com.magorasystems.conductor.example.ui.controller;

import android.os.Bundle;

import com.hannesdorfmann.mosby.conductor.viewstate.delegate.MvpViewStateConductorDelegateCallback;
import com.hannesdorfmann.mosby.conductor.viewstate.delegate.MvpViewStateConductorLifecycleListener;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

/**
 * Developed 2016.
 *
 * @author Valentin S.Bolkonsky
 */

public abstract class RxMvpViewStateController<V extends MvpView, P extends MvpPresenter<V>, VS extends ViewState<V>>
        extends RxMvpController<V, P> implements MvpViewStateConductorDelegateCallback<V, P, VS> {

    protected VS viewState;
    protected boolean restoringViewState = false;

    public RxMvpViewStateController(){
        this(null);
    }

    public RxMvpViewStateController(Bundle args){
        super(args);
    }

    @Override protected LifecycleListener getMosbyLifecycleListener() {
        return new MvpViewStateConductorLifecycleListener<>(this);
    }

    @Override public boolean isRestoringViewState() {
        return restoringViewState;
    }

    @Override public void setRestoringViewState(boolean restoringViewState) {
        this.restoringViewState = restoringViewState;
    }

    @Override public void setViewState(VS viewState) {
        this.viewState = viewState;
    }

    @Override public VS getViewState() {
        return viewState;
    }
}
