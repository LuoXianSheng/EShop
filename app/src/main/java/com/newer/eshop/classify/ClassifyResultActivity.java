package com.newer.eshop.classify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Goods;
import com.newer.eshop.goods.GoodsActivity;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassifyResultActivity extends AppCompatActivity implements HttpDataListener, RecyclerAdapter.OnItemClickLitener {

    private ArrayList<Goods> list;
    private int type_1, type_2;
    private Handler handler;

    private RecyclerAdapter recyclerAdapter;
    private Toolbar mToolbar;
    private ImageButton mFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_result);

        initToolbar();
        mFabButton = (ImageButton) findViewById(R.id.fabButton);
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ClassifyResultActivity.this, "浮动按钮", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        type_1 = intent.getIntExtra("type_1", -1);
        type_2 = intent.getIntExtra("type_2", -1);

        initRecyclerView();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initRecyclerView() {
        handler = new Handler();
        list = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerAdapter = new RecyclerAdapter(list);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
        recyclerAdapter.setOnItemClickLitener(this);
        getData();// 请求网络数据
    }

    private void hideViews() {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    private void getData() {
        NetConnection.getClassifyResult(App.SERVICE_URL + "/classifyresult", type_1, type_2, this);
    }

    @Override
    public void succeseful(String str) {
        list.add(new Goods());
        list.add(new Goods());//添加两个header，作占位子的
        try {
            JSONArray array = new JSONArray(str);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Goods goods = new Goods();
                goods.setId(object.getInt("id"));
                goods.setName(object.getString("name"));
                goods.setSell(object.getInt("sell"));
                goods.setPrice(object.getInt("price"));
                goods.setImage_path(object.getString("image_path"));
                list.add(goods);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    recyclerAdapter.notifyDataSetChanged();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loser(String str) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(ClassifyResultActivity.this, GoodsActivity.class);
        intent.putExtra("goodsId", list.get(position).getId());
        startActivity(intent);
    }
}
