package com.newer.eshop.goods;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Photo;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewActivity extends AppCompatActivity {

    private Handler handler;
    private GalleryImageAdapter adapter;
    private ArrayList<String> list;
    Gallery gallery;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_photo_view);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                gallery.setSelection(position);
                adapter.notifyDataSetChanged();
            }
        };
        list = new ArrayList<>();
        String[] path = getIntent().getStringExtra("path").split(",");
        int position = getIntent().getIntExtra("position", 0);
        for (int i = 1; i < path.length; i++) {
            list.add(path[i]);
        }
        gallery = (Gallery) findViewById(R.id.gallery);
        final PhotoView photoView = (PhotoView) findViewById(R.id.photoView);
        adapter = new GalleryImageAdapter(this, list);
        gallery.setAdapter(adapter);
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String path = list.get(position);
                ImageLoader.getInstance().displayImage(App.SERVICE_IMAGES_URL + path + ".jpg",
                        photoView, App.initOptions());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gallery.setSelection(position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(Photo photo) {
        Log.e("画廊", "到这了");
        ArrayList<String> paths = photo.getList();
        if (paths == null) return;
        for (String path : paths) {
            list.add(path);
        }
        position = photo.getPosition();
        handler.sendEmptyMessage(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
