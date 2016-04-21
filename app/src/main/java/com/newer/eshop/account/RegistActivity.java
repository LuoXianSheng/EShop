package com.newer.eshop.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.newer.eshop.R;
import com.newer.eshop.bean.User;
import com.newer.eshop.net.NetConnection;

import java.util.ArrayList;

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


    }
    User user =new User();

    public  void  RegistToServer(){
            String url="http://192.168.191.1:8080/Eshop/regist";//服务器地址
      StringRequest   registRequest= new StringRequest(Request.Method.GET, url + "?phone=11331&password=24422&time", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ArrayList<User> data =new ArrayList<>();

                Gson gson =new Gson();
                JsonParser parser= new JsonParser();
                JsonElement element= parser.parse(s);
                JsonArray jsonarray =element.getAsJsonArray();
                for(int i=0;i< jsonarray.size();i++){
                    element =jsonarray.get(i);
                    User user =gson.fromJson(element,User.class);
                    user.setName(user.getName());
                    user.setPassword(user.getPassword());
                    user.setPhone(user.getPhone());
                    data.add(user);
                }


            }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
              Toast.makeText(RegistActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
          }
      }

      );

        registRequest.setTag("GET");
        NetConnection.getInstance(this).add(registRequest);



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
