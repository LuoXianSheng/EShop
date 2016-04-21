package com.newer.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.newer.eshop.account.LoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    /**
     * 按钮事件
     * @param  v
     */
    @Override
    public void onClick(View v) {
           startActivity(new Intent(this, LoginActivity.class));
    }
}
