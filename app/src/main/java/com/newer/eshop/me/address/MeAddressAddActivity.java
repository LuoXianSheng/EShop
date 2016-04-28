package com.newer.eshop.me.address;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MeAddressAddActivity extends AppCompatActivity implements View.OnClickListener, HttpDataListener {

    private MaterialEditText name, phone, address;
    private Button btnSave;
    private String userPhone;
    private boolean isNull;//标志收货地址是否为空
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_address_add);

        userPhone = getIntent().getStringExtra("phone");
        isNull = getIntent().getBooleanExtra("isNull", false);
        initView();
    }

    private void initView() {
        name = (MaterialEditText) findViewById(R.id.address_add_name);
        phone = (MaterialEditText) findViewById(R.id.address_add_phone);
        address = (MaterialEditText) findViewById(R.id.address_add_address);
        btnSave = (Button) findViewById(R.id.address_add_save);
        btnSave.setOnClickListener(this);

        handler = new Handler();
    }

    @Override
    public void onClick(View v) {
        if (!checkEdtData()) return;
        int type = 0;
        if (isNull) type = 1;
        NetConnection.saveAddress(this, App.SERVICE_URL + "/saveaddress", userPhone, phone.getText().toString(),
                name.getText().toString(), address.getText().toString(), type, this);
    }

    public boolean checkEdtData() {
        if (TextUtils.isEmpty(name.getText())) {
            Toast.makeText(MeAddressAddActivity.this, "请输入姓名！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(phone.getText())) {
            Toast.makeText(MeAddressAddActivity.this, "请输入手机号码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(address.getText())) {
            Toast.makeText(MeAddressAddActivity.this, "请输入收货地址！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void succeseful(String str) {
        try {
            JSONObject object = new JSONObject(str);
            if (object.getString(App.STATUS).equals(App.STATUS_SUCCESS)) {
                Intent intent = new Intent();
                intent.putExtra("id", object.getInt("id"));
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("phone", phone.getText().toString());
                intent.putExtra("address", address.getText().toString());
                intent.putExtra("isNull", isNull);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MeAddressAddActivity.this, "服务器错误！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loser(String str) {

    }
}
