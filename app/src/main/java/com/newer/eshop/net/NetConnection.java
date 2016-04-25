package com.newer.eshop.net;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.newer.eshop.App;
import com.newer.eshop.myview.MyProDialog;

import java.io.IOException;
import java.util.Timer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mr_LUO on 2016/4/20.
 * <p/>
 * 网络连接处理类
 */
public class NetConnection {

    private static OkHttpClient client = null;
    private static AlertDialog dialog;

    public static synchronized OkHttpClient getOkHttpClientInstance() {
        if (client == null) {
            synchronized (new String()) {
                if (client == null) {
                    client = new OkHttpClient();
                }
            }
        }
        return client;
    }

    /**
     * 获取一件商品
     *
     * @param url
     * @param listener
     */
    public static void getOneGoods(Context context, String url, final HttpDataListener listener) {
        OkHttpClient client = getOkHttpClientInstance();
        showDialog(context);
        Request request = new Request.Builder()
                .url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.loser("请求失败！");
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.succeseful(response.body().string());
                dialog.dismiss();
            }
        });
    }

    /**
     * 获取分类信息
     *
     * @param url
     * @param type_1
     * @param listener
     */
    public static void getClassifyData(Context context, String url, int type_1, final HttpDataListener listener) {
        client = getOkHttpClientInstance();
        showDialog(context);
        FormBody body = new FormBody.Builder()
                .add("type_1", type_1 + "")
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                listener.loser("请求失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                listener.succeseful(response.body().string());
            }
        });
    }

    /**
     * 获取分类的所有商品
     *
     * @param url
     * @param type_1
     * @param type_2
     * @param listener
     */
    public static void getClassifyResult(Context context, String url, int type_1, int type_2, final HttpDataListener listener) {
        client = getOkHttpClientInstance();
        showDialog(context);
        FormBody body = new FormBody.Builder()
                .add("type_1", type_1 + "")
                .add("type_2", type_2 + "")
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                listener.loser("失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                listener.succeseful(response.body().string());
            }
        });
    }

    /**
     * 请求购物车
     */
    public static void RequestShopCar(Context context, String url, String body, final HttpDataListener listener) {
        client = getOkHttpClientInstance();
        showDialog(context);
        FormBody formBody = new FormBody.Builder()
                .add("phone", body)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                listener.loser("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                listener.succeseful(response.body().string());
            }
        });
    }

    /**
     * 点击加入购物车 将发送一个ID给服务器
     */
    public static void SendService(Context context, String url, String body, String user, final HttpDataListener listener) {
        client = getOkHttpClientInstance();
        showDialog(context);
        FormBody formBody = new FormBody.Builder()
                .add("goodsid", body)
                .add("phone", user)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
                listener.loser("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                listener.succeseful(response.body().string());
            }
        });
    }

    public static void showDialog(Context context) {
        MyProDialog myProDialog = new MyProDialog(context, App.DIALOG_TITLE);
        dialog = myProDialog.create();
        dialog.show();
    }
}
