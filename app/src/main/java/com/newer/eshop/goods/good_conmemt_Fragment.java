package com.newer.eshop.goods;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Conment;
import com.newer.eshop.bean.Goods;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class good_conmemt_Fragment extends Fragment implements HttpDataListener{

    int path;
    List<Conment> list;
    ListView listView;
    ArrayList<String> image_list;
    ArrayList<ImageView> list_image;

    public good_conmemt_Fragment(int path) {
        this.path = path;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0) {
                msg.obj=list;
            }
            //adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去请求网络我要一些什么数据呢？ 我评论的话肯定要商品ID知道吧，
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_good_conmemt, null);
        initID(v);
        NetConnection.getOneGoods(getContext(), "http://192.168.191.1:8080/Eshop/getconment?goodsid=1001", this);
        return v;
    }

    private void initID(View view) {
        listView = (ListView) view.findViewById(R.id.goods_conmemt_list);
        list=new ArrayList<>();
        image_list=new ArrayList<>();
        list_image=new ArrayList<>();
        CommentAdapter adapter=new CommentAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    public void succeseful(String str) {
        Gson gson;
        try {
            JSONObject object=new JSONObject(str);
            if(object.getString("status").equals(App.STATUS_SUCCESS)){
                gson=new Gson();
                list=gson.fromJson(object.getString("data"), new TypeToken<ArrayList<Conment>>() {
                }.getType());
                Message message=new Message();
                message.what=0;
                message.obj=list;
                handler.sendMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loser(String str) {
    }

    class CommentAdapter extends BaseAdapter{

        private final int Nochange = 0;
        private final int change = 1;
        private int count = 2;


        @Override
        public int getCount() {
            if(list==null){
                return 0;
            }else{
                return list.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if(list.get(position).getImgPath()==null){
                return Nochange;
            }else {
                return change;
            }
        }

        @Override
        public int getViewTypeCount() {
            return count;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type=getItemViewType(position);
            ViewHolder viewHolder = null;
            ViewHolderContent content=null;
            if(convertView == null){
                switch (type){
                    case Nochange:
                        content=new ViewHolderContent();
                        convertView=View.inflate(getContext(),R.layout.goods_commnet_content,null);
                        content.text_user=(TextView)convertView.findViewById(R.id.goods_comnet_user);
                        content.text_commnet=(TextView)convertView.findViewById(R.id.goods_commnet);
                        content.text_date=(TextView)convertView.findViewById(R.id.goods_commnet_date);
                        convertView.setTag(content);
                        break;
                    case change:
                        viewHolder=new ViewHolder();
                        convertView=View.inflate(getContext(),R.layout.goods_comment_imagecontent,null);
                        viewHolder.text_user=(TextView)convertView.findViewById(R.id.goods_comnet_contentuser);
                        viewHolder.text_commnet=(TextView)convertView.findViewById(R.id.goods_contentcommnet);
                        viewHolder.text_date=(TextView)convertView.findViewById(R.id.goods_commnet_contentdate);
                        viewHolder.view1=(ImageView)convertView.findViewById(R.id.goods_comment_contentimage1);
                        viewHolder.view2=(ImageView)convertView.findViewById(R.id.goods_comment_contentimage2);
                        viewHolder.view3=(ImageView)convertView.findViewById(R.id.goods_comment_contentimage3);
                        viewHolder.view4=(ImageView)convertView.findViewById(R.id.goods_comment_contentimage4);
                        viewHolder.view5=(ImageView)convertView.findViewById(R.id.goods_comment_contentimage5);
                        list_image.clear();
                        list_image.add(viewHolder.view1);
                        list_image.add(viewHolder.view2);
                        list_image.add(viewHolder.view3);
                        list_image.add(viewHolder.view4);
                        list_image.add(viewHolder.view5);
                        convertView.setTag(viewHolder);
                        break;
                }
            }else if(type==change){
                viewHolder=(ViewHolder)convertView.getTag();
            }else{
                content=(ViewHolderContent)convertView.getTag();
            }
            if(type==change){
                //viewHolder.text_user.setText(list.get(position).getUserId());
                viewHolder.text_commnet.setText("评价:" + list.get(position).getContent());
                viewHolder.text_date.setText("时间:" + list.get(position).getDate());
                String[] src=list.get(position).getImgPath().split(",");
                if(src.length!=0) {
                        for (int i = 0; i < src.length; i++) {
                            ImageLoader.getInstance().displayImage(
                                    "http://192.168.191.1:8080/Eshop/images/" + src[i] + ".jpg",
                                    list_image.get(i)
                            );
                        }
                    }
            }else {
                //content.text_user.setText(list.get(position).getUserId());
                content.text_commnet.setText("用户名:"+list.get(position).getContent());
                content.text_date.setText("时间:"+list.get(position).getDate());
            }
            return convertView;
        }


        class  ViewHolder{
            TextView text_user;
            TextView text_commnet;
            TextView text_date;
            ImageView view1;
            ImageView view2;
            ImageView view3;
            ImageView view4;
            ImageView view5;
        }
        class ViewHolderContent{
            TextView text_user;
            TextView text_commnet;
            TextView text_date;
        }
    }
}

