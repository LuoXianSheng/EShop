package com.newer.eshop.goods;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Administrator on 2016/4/21.
 */
public class good_Request_Internet {

    C  c;

    public good_Request_Internet(C c){
        this.c=c;
    }

    /**
     * 封装请求数据的get方法
     * @param url
     */
     public void GetInternet(String url){

         OkHttpClient client=new OkHttpClient();
         Request request=new Request.Builder()
                 .url(url).build();
         client.newCall(request).enqueue(new Callback() {
             @Override
             public void onFailure(Call call, IOException e) {
                 System.out.println("请求失败!");
             }

             @Override
             public void onResponse(Call call, Response response) throws IOException {

//                   InputStream is=response.body().byteStream();
//                   byte[] bytes=new byte[1024];
//                   int len=-1;
//                   StringBuilder sb=new StringBuilder();
//                   while((len=is.read(bytes))!=-1){
//                       sb.append(new String(bytes,0,len));
//                       System.out.println();
//                   }
                 c.SuccesefuGet(response.body().string());
             }
         });
     }
}
