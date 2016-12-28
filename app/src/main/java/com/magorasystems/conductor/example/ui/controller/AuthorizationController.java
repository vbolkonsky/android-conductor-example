package com.magorasystems.conductor.example.ui.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.bluelinelabs.conductor.changehandler.SimpleSwapChangeHandler;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.bluelinelabs.conductor.rxlifecycle2.ControllerEvent;
import com.magorasystems.conductor.example.R;
import com.magorasystems.conductor.example.application.ConductorExampleApplication;
import com.magorasystems.conductor.example.mvp.authorization.AuthorizationPresenter;
import com.magorasystems.conductor.example.mvp.authorization.AuthorizationView;
import com.magorasystems.conductor.example.mvp.authorization.AuthorizationViewState;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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


    @BindView(R.id.text_email)
    TextView textEmail;
    @BindView(R.id.text_password)
    TextView textPassword;
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

    @Override
    public void authorizationSuccess() {
        log.debug("authorizationSuccess");
        getRouter().pushController(RouterTransaction.with(new PagerController())
                .pushChangeHandler(new FadeChangeHandler())
                .popChangeHandler(new HorizontalChangeHandler()));
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

    @OnClick(R.id.button_sign_in)
    void onAuthorization() {
        final CharSequence email = textEmail.getText();
        final CharSequence password = textPassword.getText();
        if (email != null && password != null) {
            presenter.login(String.valueOf(email), String.valueOf(password));
        }
    }

    @OnClick(R.id.text_password_recover)
    void onGoToRestorePassword() {
        getRouter().pushController(RouterTransaction.with(new RestorePasswordController())
                .popChangeHandler(new VerticalChangeHandler())
                .pushChangeHandler(new SimpleSwapChangeHandler()));
    }
}
