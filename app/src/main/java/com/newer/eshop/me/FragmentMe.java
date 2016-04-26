package com.newer.eshop.me;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.account.LoginActivity;
import com.newer.eshop.account.SettingActivity;
import com.newer.eshop.goods.GoodsActivity;
import com.tencent.tauth.Tencent;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class FragmentMe extends Fragment implements View.OnClickListener {
    private static final String APP_ID = "1105291740";
    private Tencent mTencent;
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String Key = "Mytoken";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        Button login = (Button) view.findViewById(R.id.login);
        Button goods = (Button) view.findViewById(R.id.goods);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GoodsActivity.class));
            }
        });
        view.findViewById(R.id.me_top_setting).setOnClickListener(this);
        view.findViewById(R.id.me_top_login).setOnClickListener(this);
        view.findViewById(R.id.btn_login_out).setOnClickListener(this);
//       context =view.getContext().getApplicationContext();
//        mTencent = Tencent.createInstance(APP_ID, context);


        return view;

    }



    public  void  removesharepreference(){
            preferences=getContext().getSharedPreferences("login_user_im", App.MODE_PRIVATE);
        editor=preferences.edit();
        editor.remove(Key);
        editor.remove("name");
        editor.remove("address");
        editor.remove("phone");
        editor.commit();
        Toast.makeText(getContext(), "注销成功", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode == 1){
            Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.me_top_setting:
                startActivity(new Intent(getContext(),SettingActivity.class));
                break;
            case R.id.me_top_login:
                startActivity(new Intent(getContext(),LoginActivity.class));
                break;
            case R.id.btn_login_out:


                removesharepreference();
//                mTencent.logout(context);
                startActivity(new Intent(getContext(),LoginActivity.class));

                break;
        }

    }
}
