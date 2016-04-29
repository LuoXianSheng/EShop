package com.newer.eshop.account;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.newer.eshop.R;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);


        findViewById(R.id.tv_user_name).setOnClickListener(this);
        findViewById(R.id.tv_numberphone).setOnClickListener(this);

    }





    @Override
    public void onClick(View v) {
            switch (v.getId()){

              case   R.id.tv_user_name:
                  AlertDialog   alertDialog =null;
                 AlertDialog.Builder builder =new AlertDialog.Builder(this);
                  builder.setTitle("修改昵称");
                  builder.setPositiveButton("确认",null);
                  builder.setNegativeButton("取消",null);

                 View Veditext =getLayoutInflater().inflate(R.layout.change_editext,null);
                    builder.setView(Veditext);
                    alertDialog=builder.create();
                  alertDialog.show();

                  break;
                case   R.id.tv_numberphone:
                    AlertDialog   alertDialog2 =null;

                    AlertDialog.Builder builder2 =new AlertDialog.Builder(this);
                    builder2.setTitle("修改号码");
                    builder2.setPositiveButton("确认",null);
                    builder2.setNegativeButton("取消",null);

                    View Veditext2=getLayoutInflater().inflate(R.layout.change_editext,null);
                    builder2.setView(Veditext2);
                    alertDialog2=builder2.create();
                    alertDialog2.show();

                    break;
            }

    }
}
