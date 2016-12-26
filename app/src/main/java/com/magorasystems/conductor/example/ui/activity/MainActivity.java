package com.magorasystems.conductor.example.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.magorasystems.conductor.example.R;
import com.magorasystems.conductor.example.ui.controller.AuthorizationController;
import com.magorasystems.conductor.example.ui.controller.TextController;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.extern.slf4j.Slf4j;

/**
 * Developed 2016.
 *
 * @author Valentin S.Bolkonsky
 */
@Slf4j
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.controller_container)
    ViewGroup container;
    @BindView(R.id.controller_container_bottom)
    ViewGroup containerBottom;

    private Router router;
    private Router routerBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            final Bundle args = new Bundle();
            args.putInt(getString(R.string.key_layout), R.layout.controller_authorization);
            router.setRoot(RouterTransaction.with(new AuthorizationController(args)));
        }
        routerBottom = Conductor.attachRouter(this, containerBottom, savedInstanceState);
        if(!routerBottom.hasRootController()){
            final Bundle args = new Bundle();
            args.putString(getString(R.string.key_text_value), "bottom router");
            routerBottom.setRoot(RouterTransaction.with(new TextController(args)));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
