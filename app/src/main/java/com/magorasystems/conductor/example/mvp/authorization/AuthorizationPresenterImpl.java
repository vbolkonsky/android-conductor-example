package com.magorasystems.conductor.example.mvp.authorization;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Developed 2016.
 *
 * @author Valentin S.Bolkonsky
 */

public class AuthorizationPresenterImpl extends MvpBasePresenter<AuthorizationView>
        implements AuthorizationPresenter {

    @Override
    public void login(String login, String password) {
        Observable.<Boolean>create(e -> e.onNext(Boolean.TRUE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::authorizationSuccess);
    }

    private void authorizationSuccess(boolean isSuccess) {
        if (!isViewAttached()) {
            return;
        }
        final AuthorizationView view = getView();
        if (view != null) {
            view.authorizationSuccess();
        }
    }
}
