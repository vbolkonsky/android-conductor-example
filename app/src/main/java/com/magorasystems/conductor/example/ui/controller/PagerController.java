package com.magorasystems.conductor.example.ui.controller;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.rxlifecycle2.ControllerEvent;
import com.bluelinelabs.conductor.rxlifecycle2.RxController;
import com.bluelinelabs.conductor.support.ControllerPagerAdapter;
import com.magorasystems.conductor.example.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PagerController extends RxController {

    private int[] PAGE_COLORS = new int[]{R.color.green_300, R.color.cyan_300, R.color.deep_purple_300, R.color.lime_300, R.color.red_300};

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    Unbinder unbinder;

    private final ControllerPagerAdapter pagerAdapter;

    public PagerController() {
        pagerAdapter = new ControllerPagerAdapter(this, false) {
            @Override
            public Controller getItem(int position) {
                return new ChildController(String.format(Locale.getDefault(), "Child #%d (Swipe to see more)", position), PAGE_COLORS[position], true);
            }

            @Override
            public int getCount() {
                return PAGE_COLORS.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Page " + position;
            }
        };
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        final View view = inflater.inflate(R.layout.controller_pager, container, false);
        unbinder = ButterKnife.bind(this, view);
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose(() -> log.info("Disposing from onCreateView)"))
                .compose(this.bindUntilEvent(ControllerEvent.DESTROY_VIEW))
                .subscribe(num -> {
                    log.info("Started in onCreateView(), running until onDestroyView(): {} ", num);
                });
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
        viewPager.setAdapter(null);
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
