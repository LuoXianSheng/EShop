package com.newer.eshop.me.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.newer.eshop.R;
import com.newer.eshop.bean.MyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SubmitResultActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnBack, btnUp;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_me_submit_result);

        initToolbar();
        initView();
    }

    private void initView() {
        btnBack = (Button) findViewById(R.id.submit_result_back);
        btnUp = (Button) findViewById(R.id.submit_result_up);
        btnBack.setOnClickListener(this);
        btnUp.setOnClickListener(this);
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.submit_result_toolbar);
        imgBack = (ImageView) mToolbar.findViewById(R.id.submit_result_toolvar_back);
        imgBack.setOnClickListener(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit_result_up) {

        } else {
            MyEvent event = new MyEvent();
            event.setAction("updateCart");
            EventBus.getDefault().post(event);
            setResult(RESULT_OK);
            EventBus.getDefault().unregister(this);
            this.finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void meiyongde(Boolean t) {}
}
