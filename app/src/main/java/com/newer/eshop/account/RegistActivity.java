package com.newer.eshop.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.newer.eshop.R;
import com.newer.eshop.bean.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
        EditText edt_username,edt_password,edt_password2,edt_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }


    public  void  initView(){

        edt_username= (EditText) findViewById(R.id.edt_username);
        edt_password= (EditText) findViewById(R.id.edt_password);
        edt_password2= (EditText) findViewById(R.id.edt_password2);
        edt_phone= (EditText) findViewById(R.id.edt_phone);
        findViewById(R.id.btn_regist).setOnClickListener(this);
        findViewById(R.id.regist_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    User user =new User();

    public  void  RegistToServer(){
        OkHttpClient  okHttpClient =new OkHttpClient();
        String url="http://192.168.191.1:8080/Eshop/reigst";
        FormBody body =new FormBody.Builder()
                .add("name",edt_username.getText().toString())
                .add("password",edt_password.getText().toString())
                .add("phone", edt_phone.getText().toString())
                .add("time",System.currentTimeMillis()+"")
                .build();
        Request request =new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              System.out.println("成功");
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!checkEdit()){
            return;
        }

        switch (v.getId()){
            case R.id.btn_regist:

                user.setName(edt_username.getText().toString());
                user.setPassword(edt_password.getText().toString());
                user.setPhone(edt_phone.getText().length());
                RegistToServer();

                break;

        }
    }
    private boolean checkEdit(){
        if (edt_username.getText().toString().trim().equals("")){
            Toast.makeText(RegistActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        }else  if (edt_password.getText().toString().trim().equals("")){
            Toast.makeText(RegistActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }else  if (!edt_password2.getText().toString().trim().equals(edt_password.getText().toString().trim())){
            Toast.makeText(RegistActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
        }else  if (edt_phone.getText().toString().trim().equals("")){

            Toast.makeText(RegistActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
        }

        else {
            return  true;
        }
            return  false;
    }



}
