package com.newer.eshop.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Mr_LUO on 2016/4/20.
 *
 * 网络连接处理类
 */
public class NetConnection {

    public  static RequestQueue queue;//请求队列


    public  static  RequestQueue getInstance(Context contextnt){
        if (queue==null){
            queue= Volley.newRequestQueue(contextnt);
            queue.start();
        }
     return  queue;
    }
}
