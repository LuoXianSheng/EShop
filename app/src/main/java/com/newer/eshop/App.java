package com.newer.eshop;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * Created by Mr_LUO on 2016/4/20.
 */
public class App extends Application {

    public static final int REQUESTCODE = 1;
    public static final int RESULTCODE = 1;

    public static final String DIALOG_TITLE = "正在加载...";

    public static final String SERVICE_URL = "http://192.168.191.1:8080/Eshop";
    public static final String SERVICE_CLASSIFY_IMAGES_URL = SERVICE_URL + "/images/classify";
    public static final String SERVICE_IMAGES_URL = SERVICE_URL + "/images/";

    public static final String STATUS_SUCCESS = "1";//请求成功码
    public static final String STATUS = "status";//请求码
    public static final String STATUS_LOSE = "0";//失败状态码
    public static final String GOODSCLASSIFY_URL = SERVICE_URL + "/goodsclassify";


    public static final int ADD_ADDRESS_STATUS = 1001;
    public static final int UPDATE_ADDRESS_STATUS = 1002;

    public static final String USER_SP_NAME = "login_user_im";

    private static DisplayImageOptions options = null;

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

    public static DisplayImageOptions initOptions() {
        //初始化显示图片的配置
        if (options == null) {
            synchronized (new String()) {
                if (options == null) {
                    options = new DisplayImageOptions.Builder()
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .build();
                }
            }
        }
        return options;
    }

}
