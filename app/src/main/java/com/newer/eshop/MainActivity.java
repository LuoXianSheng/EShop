package com.newer.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.newer.eshop.classify.FragmentClassify;
import com.newer.eshop.me.FragmentMe;
import com.newer.eshop.shopingcat.FragmentShopingCart;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_index, tv_classify, tv_shopingcart, tv_me;
    private Fragment fragmentIndex, fragmentClassify, fragmentShopingCart, fragmentMe;
    private FragmentManager manager;
    private ActionBar bar;

    DisplayMetrics dm = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Toast.makeText(MainActivity.this, "" + dm.heightPixels, Toast.LENGTH_SHORT).show();
        initActionBar();
        initView();
    }

    private void initActionBar() {
//        bar = getSupportActionBar();
//        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        bar.setCustomView(R.layout.actionbar);
//        bar.getCustomView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Action被点击了", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void initView() {
        manager = getSupportFragmentManager();
        tv_index = (TextView) findViewById(R.id.main_tv_index);
        tv_classify = (TextView) findViewById(R.id.main_tv_classify);
        tv_shopingcart = (TextView) findViewById(R.id.main_tv_shopingcart);
        tv_me = (TextView) findViewById(R.id.main_tv_me);

        tv_index.setOnClickListener(this);
        tv_classify.setOnClickListener(this);
        tv_shopingcart.setOnClickListener(this);
        tv_me.setOnClickListener(this);

        tv_index.performClick();//模拟一次点击相当于点击了第一项
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = manager.beginTransaction();//开启一个事物
        hideAllFragment(transaction);
        resetAllSelector();
        switch (v.getId()) {
            case R.id.main_tv_index:
//                bar.show();
                tv_index.setSelected(true);
                if (fragmentIndex == null) {
                    fragmentIndex = new FragmentIndex(dm.heightPixels);
                    transaction.add(R.id.main_frameLayout, fragmentIndex);
                } else {
                    transaction.show(fragmentIndex);
                }
                break;
            case R.id.main_tv_classify:
//                bar.hide();
                tv_classify.setSelected(true);
                if (fragmentClassify == null) {
                    fragmentClassify = new FragmentClassify();
                    transaction.add(R.id.main_frameLayout, fragmentClassify);
                } else {
                    transaction.show(fragmentClassify);
                }
                break;
            case R.id.main_tv_shopingcart:
//                bar.hide();
                tv_shopingcart.setSelected(true);
                if (fragmentShopingCart == null) {
                    fragmentShopingCart = new FragmentShopingCart();
                    transaction.add(R.id.main_frameLayout, fragmentShopingCart);
                } else {
                    transaction.show(fragmentShopingCart);
                }
                break;
            case R.id.main_tv_me:
//                bar.hide();
                tv_me.setSelected(true);
                if (fragmentMe == null) {
                    fragmentMe = new FragmentMe();
                    transaction.add(R.id.main_frameLayout, fragmentMe);
                } else {
                    transaction.show(fragmentMe);
                }
                break;
        }
        transaction.commit();//提交事物
    }

    /**
     * 隐藏所有Fragment
     *
     * @param transaction 碎片事物
     */
    public void hideAllFragment(FragmentTransaction transaction) {
        if (fragmentIndex != null) transaction.hide(fragmentIndex);
        if (fragmentClassify != null) transaction.hide(fragmentClassify);
        if (fragmentShopingCart != null) transaction.hide(fragmentShopingCart);
        if (fragmentMe != null) transaction.hide(fragmentMe);
    }

    /**
     * 重置所有选择器
     */
    public void resetAllSelector() {
        tv_index.setSelected(false);
        tv_classify.setSelected(false);
        tv_shopingcart.setSelected(false);
        tv_me.setSelected(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent it=getIntent();
        System.out.println(it.getIntExtra("fragment", 0));
    }

}