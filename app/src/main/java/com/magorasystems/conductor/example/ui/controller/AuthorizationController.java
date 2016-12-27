package com.magorasystems.conductor.example.ui.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.rxlifecycle2.ControllerEvent;
import com.magorasystems.conductor.example.R;
import com.magorasystems.conductor.example.application.ConductorExampleApplication;
import com.magorasystems.conductor.example.mvp.authorization.AuthorizationPresenter;
import com.magorasystems.conductor.example.mvp.authorization.AuthorizationView;
import com.magorasystems.conductor.example.mvp.authorization.AuthorizationViewState;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;

/**
 * Developed 2016.
 *
 * @author Valentin S.Bolkonsky
 */
@Slf4j
public class AuthorizationController extends RxMvpViewStateController<AuthorizationView, AuthorizationPresenter, AuthorizationViewState> implements AuthorizationView {


    private Unbinder unbinder;


    public AuthorizationController(final Bundle args) {
        super(args);
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose(() -> log.info("Disposing from constructor"))
                .compose(this.bindUntilEvent(ControllerEvent.DESTROY))
                .subscribe(num -> {
                    log.info("Started in constructor, running until onDestroy(): {}", num);
                });
    }

    @NonNull
    @Override
    public AuthorizationPresenter createPresenter() {
        return ConductorExampleApplication.get(getActivity())
                .getComponent().authorizationPresenter();
    }

    @NonNull
    @Override
    public AuthorizationViewState createViewState() {
        return new AuthorizationViewState();
    }

    @Override
    public void onViewStateInstanceRestored(boolean instanceStateRetained) {

    }

    @Override
    public void onNewViewStateInstance() {

    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        final View view = inflater.inflate(getArgs().getInt(container.getResources().getString(R.string.key_layout)), container, false);
        unbinder = ButterKnife.bind(this, view);
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose(() -> log.info("Disposing from onCreateView)"))
                .compose(this.bindUntilEvent(ControllerEvent.DESTROY_VIEW))
                .subscribe(num -> {
                    log.info("Started in onCreateView(), running until onDestroyView(): {} ", num);
                });
        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        log.info("onAttach() called");
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose(() -> log.info("Disposing from onAttach()"))
                .compose(this.bindUntilEvent(ControllerEvent.DETACH))
                .subscribe(num -> {
                    log.info("Started in onAttach(), running until onDetach(): {} ", num);
                });
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        log.info("onDestroyView() called");
        unbinder.unbind();
        unbinder = null;
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        log.info("onDetach() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log.info("onDestroy() called");
    }
}
