package com.newer.eshop.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mr_LUO on 2016/4/20.
 */
public class LoginActivity  extends AppCompatActivity implements View.OnClickListener {
        private EditText edt_name,edt_psw;

         String Key ="Mytoken";

        SharedPreferences preferences;
        SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        preferences =getSharedPreferences("login_user_im",App.MODE_PRIVATE);
        editor =preferences.edit();
        initView();



    }

    public  void  initView(){
        findViewById(R.id.login_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
        findViewById(R.id.tv_regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
            }
        });
        findViewById(R.id.btn_login).setOnClickListener(this);
        edt_name= (EditText) findViewById(R.id.edt_name);
        edt_psw= (EditText) findViewById(R.id.edt_psw);
    }

    public  void  LoginToServer() {
        OkHttpClient okHttpClient =new OkHttpClient();
        String url="http://192.168.191.1:8080/Eshop/login";
        FormBody body =new FormBody.Builder()
                .add("phone",edt_name.getText().toString())
                .add("password",edt_psw.getText().toString())
                .build();
        Request request =new Request.Builder()
                .url(url)
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("登录失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//              System.out.println("登录成功");
              String data =response.body().string();
                System.out.println("++++++++" + data);
                try {
                    JSONObject jsonObject =new JSONObject(data);
                    if (jsonObject.getString("status").equals(App.STATUS_SUCCESS)){
                        Gson gson =new Gson();
                        User user =gson.fromJson(jsonObject.getString("data"),User.class);
                        System.out.println("---------------"+user.getToken());

                        editor.putString(Key,user.getToken());
                        editor.putString("name",user.getName());
                        editor.putString("address",user.getAddress());
                        editor.putString("phone", String.valueOf(user.getPhone()));
                        editor.commit();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(edt_name.getText().toString())||TextUtils.isEmpty(edt_psw.getText().toString())){
            Toast.makeText(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
        }

        switch (v.getId()){

            case  R.id.btn_login:
                LoginToServer();
                break;
        }
    }
}
