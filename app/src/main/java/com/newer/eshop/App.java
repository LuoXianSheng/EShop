package com.newer.eshop;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Mr_LUO on 2016/4/20.
 */
public class App extends Application {

    public static final String STATUS_SUCCESS = "1";//请求成功码
    public static final String STATUS_LOSE = "0";//失败状态码

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);//全局初始化此配置
    }
}
