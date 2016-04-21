package com.newer.eshop.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mr_LUO on 2016/4/20.
 *
 * 网络连接处理类
 */
public class NetConnection {

    private HttpDataListener listener;

    /**
     * 获取一件商品
     * @param url
     * @param listener
     */
    public static void getOneGoods(String url, final HttpDataListener listener) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.loser("请求失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.succeseful(response.body().string());
            }
        });
    }
}
