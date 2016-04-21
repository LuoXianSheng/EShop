package com.newer.eshop;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Mr_LUO on 2016/4/20.
 */
public class App extends Application {

    public static final String STATUS_SUCCESS = "1";//请求成功码
    public static final String STATUS_LOSE = "0";//失败状态码

    @Override
    public void onCreate() {
        super.onCreate();

        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "Eshop/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .discCache(new UnlimitedDiscCache(cacheDir))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .build();
        ImageLoader.getInstance().init(config);//全局初始化此配置
    }
}
