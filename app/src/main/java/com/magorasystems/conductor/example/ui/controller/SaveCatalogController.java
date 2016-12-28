package com.magorasystems.conductor.example.ui.controller;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.rxlifecycle2.ControllerEvent;
import com.magorasystems.conductor.example.R;
import com.magorasystems.conductor.example.application.ConductorExampleApplication;
import com.magorasystems.conductor.example.mvp.savecatalog.SaveCatalogPresenter;
import com.magorasystems.conductor.example.mvp.savecatalog.SaveCatalogView;

import java.util.concurrent.TimeUnit;

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
public class SaveCatalogController extends RxMvpController<SaveCatalogView, SaveCatalogPresenter>
        implements SaveCatalogView {

    Unbinder unbinder;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        final View view = inflater.inflate(R.layout.controller_save_catalog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @NonNull
    @Override
    public SaveCatalogPresenter createPresenter() {
        return ConductorExampleApplication.get(getActivity())
                .getComponent()
                .saveCatalogPresenter();
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

    @OnClick(R.id.button_back)
    void onBack() {
        getRouter().handleBack();
    }

    @OnClick(R.id.button_save)
    void onSave() {
        presenter.save();
    }
}
