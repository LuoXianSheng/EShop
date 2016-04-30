package com.newer.eshop.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.MyEvent;
import com.newer.eshop.bean.User;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //    private static final String APP_ID = "1105291740";
    private static final String APP_ID = "1105291740";
    private EditText edt_name, edt_psw;
    public static String openid;
    public static String access_token;
    public static String time;
    String Key = "Mytoken";
    public static String Appid;
    SharedPreferences preferences;
    Button btn_login;
    SharedPreferences.Editor editor;
    CheckBox ck;
    private Tencent mTencent;
    private MyListener iUiListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        EventBus.getDefault().register(this);

        preferences = getSharedPreferences("login_user_im", App.MODE_PRIVATE);
        editor = preferences.edit();
        initView();

        mTencent = Tencent.createInstance(APP_ID, getApplicationContext());
        iUiListener = new MyListener();



    }

    public void initView() {
        findViewById(R.id.login_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_regist).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
            }
        });
       btn_login = (Button) findViewById(R.id.btn_login);

        findViewById(R.id.btn_login_qq).setOnClickListener(this);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_psw = (EditText) findViewById(R.id.edt_psw);
         ck= (CheckBox) findViewById(R.id.checkBox);


        String name =preferences.getString("name2","");
        String psw =preferences.getString("pass","");

        edt_name.setText(name);
        edt_psw.setText(psw);


        btn_login.setOnClickListener(this);

//        if (TextUtils.isEmpty(edt_name.getText().toString()) || TextUtils.isEmpty(edt_psw.getText().toString())){
//            Toast.makeText(LoginActivity.this, "账号密码为空", Toast.LENGTH_SHORT).show();
//            btn_login.setClickable(false);
//        }else {
//            btn_login.setClickable(true);
//
//        }
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    LoginToServer();
//
//
//            }
//        });



    }

    public void LoginToServer() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://192.168.191.1:8080/Eshop/login";
        FormBody body = new FormBody.Builder()
                .add("phone", edt_name.getText().toString())
                .add("password", edt_psw.getText().toString())
                .build();
        Request request = new Request.Builder()
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
                String data = response.body().string();
                System.out.println("++++++++" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getString("status").equals(App.STATUS_SUCCESS)) {
                        Gson gson = new Gson();
                        User user = gson.fromJson(jsonObject.getString("data"), User.class);
                        System.out.println("---------------" + user.getToken());

                        editor.putString(Key, user.getToken());
                        editor.putString("name", user.getName());
                        editor.putString("address", user.getAddress());
                        editor.putString("phone", String.valueOf(user.getPhone()));
                        editor.commit();

                        setResult(RESULT_OK);

                        LoginActivity.this.finish();

                    }
                    else {
                        MyEvent myEvent=new MyEvent();
                        myEvent.setAction("response");
                        myEvent.setData(data);

                        EventBus.getDefault().post(myEvent);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String name =edt_name.getText().toString();
                String psw =edt_name.getText().toString();
                if (ck.isChecked()){
                    editor.putString("name2",name);
                    editor.putString("pass",psw);
                    editor.commit();

                }

            }


        });

    }







    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exit(MyEvent myEvent) {
        if (myEvent.getAction().equals("response")){
            setResult(RESULT_OK);

        }else if (myEvent.getAction().equals("exit")){
            EventBus.getDefault().unregister(this);
            LoginActivity.this.finish();

        }

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btn_login:

                if (TextUtils.isEmpty(edt_name.getText().toString()) || TextUtils.isEmpty(edt_psw.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();

                }


                    LoginToServer();


                break;
            case R.id.btn_login_qq:
                LoginToqq();
                break;

        }
    }

    private void LoginToqq() {
        mTencent.login(this, "all", iUiListener);
    }

    private class MyListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            //成功以后回调的方法
            /*{登录成功后调用public void onComplete(JSONObject arg0) 回传的JsonObject， 其中包含OpenId， AccessToken等重要数据。
                "ret":0,
                    "pay_token":"xxxxxxxxxxxxxxxx",
                    "pf":"openmobile_android",
                    "expires_in":"7776000",
                    "openid":"xxxxxxxxxxxxxxxxxxx",
                    "pfkey":"xxxxxxxxxxxxxxxxxxx",
                    "msg":"sucess",
                    "access_token":"xxxxxxxxxxxxxxxxxxxxx"
            }*/
            OkHttpClient okHttpClient =new OkHttpClient();
            String url="http://192.168.191.1:8080/Eshop/oauth2reigst";
            FormBody body =new FormBody.Builder()
                    .add("time",System.currentTimeMillis()+"")
                    .build();
            Request request = new Request.Builder()
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
                    String qqdata =response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(qqdata);
                        if(jsonObject.getString("status").equals(App.STATUS_SUCCESS)){


                            editor.putString(Key, jsonObject.getString("token"));
                            editor.putString("phone", jsonObject.getString("phone"));
                            editor.commit();

                            System.out.println("++++++++++++++"+jsonObject.getString("token"));
                            System.out.println("++++++++++++++++"+jsonObject.getString("phone"));


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });



            MyEvent myEvent=new MyEvent();
            myEvent.setAction("exit");
            EventBus.getDefault().post(myEvent);

        }

        @Override
        public void onError(UiError e) {
            System.out.println("onError:code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }
        @Override
        public void onCancel() {
            System.out.println("onCancel");
        }
    }

    //应用调用Andriod_SDK接口时，如果要成功接收到回调，需要在调用接口的Activity的onActivityResult方法中增加如下代码：
    //其中onActivityResultData接口中的listener为当前调用的Activity所实现的相应回调UIListener。
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,iUiListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
