package com.magorasystems.conductor.example.ui.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.rxlifecycle2.RxController;
import com.magorasystems.conductor.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import lombok.extern.slf4j.Slf4j;

/**
 * Developed 2016.
 *
 * @author Valentin S.Bolkonsky
 */
@Slf4j
public class TextController extends RxController {

    public final static String KEY_TEXT_VALUE = "key_text_value";
    @BindView(R.id.text_view)
    TextView textView;
    private Unbinder unbinder;

    public TextController() {
        super();
    }

    public TextController(@Nullable Bundle args) {
        super(args);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        final View view = inflater.inflate(R.layout.controller_text, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        textView.setText(getArgs().getString(view.getResources().getString(R.string.key_text_value)));
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        log.info("onDestroyView() called");
        unbinder.unbind();
        unbinder = null;
    }

    @OnClick(R.id.text_view)
    void onTextViewClick() {
        getRouter().pushController(RouterTransaction.with(new SaveCatalogController()));
    }
}
