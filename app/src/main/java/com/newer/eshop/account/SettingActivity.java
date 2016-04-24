package com.newer.eshop.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.newer.eshop.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        findViewById(R.id.setting_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
