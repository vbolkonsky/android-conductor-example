package com.magorasystems.conductor.example.ui.controller;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.rxlifecycle2.ControllerEvent;
import com.bluelinelabs.conductor.rxlifecycle2.RxController;
import com.magorasystems.conductor.example.R;

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
public class RestorePasswordController extends RxController {

    Unbinder unbinder;

    @BindView(R.id.text_email)
    TextView textEmail;

    public RestorePasswordController() {
        super();
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        final View view = inflater.inflate(R.layout.controller_restore_password, container, false);
        unbinder = ButterKnife.bind(this, view);
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

    @OnClick(R.id.button_restore)
    void onRestorePassword() {
        final CharSequence email = textEmail.getText();
        if (email != null) {
            Toast.makeText(getActivity(), "Send to " + email, Toast.LENGTH_LONG).show();
        }
    }
}
