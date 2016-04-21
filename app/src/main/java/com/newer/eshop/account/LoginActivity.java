package com.newer.eshop.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.newer.eshop.R;

/**
 * Created by Mr_LUO on 2016/4/20.
 */
public class LoginActivity  extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
    }

    public  void  initView(){
        findViewById(R.id.tv_regist).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,RegistActivity.class));
    }
}
