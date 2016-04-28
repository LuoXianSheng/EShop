package com.newer.eshop.net;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.newer.eshop.App;
import com.newer.eshop.myview.MyProDialog;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

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
                    client = new OkHttpClient().newBuilder()
                            .connectTimeout(5000, TimeUnit.MILLISECONDS)
                            .build();
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
        FormBody body = new FormBody.Builder()
                .add("type_1", type_1 + "")
                .build();
        addToEnqueue(context, url, body, listener);
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
        FormBody body = new FormBody.Builder()
                .add("type_1", type_1 + "")
                .add("type_2", type_2 + "")
                .build();
        addToEnqueue(context, url, body, listener);
    }

    /**
     * 请求购物车
     */
    public static void RequestShopCar(Context context, String url, String phone, final HttpDataListener listener) {
        FormBody body = new FormBody.Builder()
                .add("phone", phone)
                .build();
        addToEnqueue(context, url, body, listener);
    }

    /**
     * 点击加入购物车 将发送一个ID给服务器
     */
    public static void SendService(Context context, String url, String goodsid, String user,
                                   String count, final HttpDataListener listener) {
        FormBody body = new FormBody.Builder()
                .add("goodsid", goodsid)
                .add("phone", user)
                .add("count", count)
                .build();
        addToEnqueue(context, url, body, listener);
    }

    public static void showDialog(Context context) {
        MyProDialog myProDialog = new MyProDialog(context, App.DIALOG_TITLE);
        dialog = myProDialog.create();
        dialog.show();
    }


    /**
     * s删除商品
     * @param context
     * @param url
     * @param phone
     * @param data
     * @param listener
     */
    public static void deleteGoods(Context context, String url, String phone, String data, final HttpDataListener listener) {
        FormBody body = new FormBody.Builder()
                .add("phone", phone)
                .add("data", data)
                .build();
        addToEnqueue(context, url, body, listener);
    }

    /**
     * 购买商品
     * @param context
     * @param url
     * @param goodsid
     * @param count
     * @param listener
     */
    public static void buyGoods(Context context, String url, String phone, int type, String goodsid, String count, final HttpDataListener listener) {
        FormBody body = new FormBody.Builder()
                .add("phone", phone)
                .add("type", type + "")
                .add("goodsid", goodsid)
                .build();
        addToEnqueue(context, url, body, listener);
    }

    /**
     * 获取收货地址
     * @param context
     * @param url
     * @param phone
     * @param listener
     */
    public static void getAddress(Context context, String url, String phone, int type, final HttpDataListener listener) {
        FormBody body = new FormBody.Builder()
                .add("phone", phone)
                .add("type", type + "")
                .build();
        addToEnqueue(context, url, body, listener);
    }

    /**
     * 保存收货地址
     * @param context
     * @param url
     * @param userPhone
     * @param phone
     * @param name
     * @param address
     * @param listener
     */
    public static void saveAddress(Context context, String url, String userPhone, String phone,
                                   String name, String address, int type, final HttpDataListener listener) {
        FormBody body = new FormBody.Builder()
                .add("userPhone", userPhone)
                .add("phone", phone)
                .add("name", name)
                .add("address", address)
                .add("type", type + "")
                .build();
        addToEnqueue(context, url, body, listener);
    }

    /**
     * 修改默认地址
     * @param context
     * @param url
     * @param oldId 修改之前的默认地址的id
     * @param newId
     * @param listener
     */
    public static void updateAddressForType(Context context, String url, String oldId,
                                            String newId, final HttpDataListener listener) {
        FormBody body = new FormBody.Builder()
                .add("oldId", oldId)
                .add("newId", newId)
                .build();
        addToEnqueue(context, url, body, listener);
    }

    /**
     * 请求队列
     * @param context
     * @param url
     * @param body
     * @param listener
     */
    public static void addToEnqueue(Context context, String url, FormBody body, final HttpDataListener listener) {
        client = getOkHttpClientInstance();
        showDialog(context);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
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

}
