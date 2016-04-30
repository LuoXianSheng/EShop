package com.newer.eshop.shopingcat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.eshop.App;
import com.newer.eshop.MainActivity;
import com.newer.eshop.R;
import com.newer.eshop.bean.Cart;
import com.newer.eshop.bean.Goods;
import com.newer.eshop.bean.MyEvent;
import com.newer.eshop.goods.GoodsActivity;
import com.newer.eshop.me.order.AllOrderActivity;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class FragmentShopingCart extends Fragment implements HttpDataListener{

    ListView listView;
    String phone;
    ArrayList<Cart> list;
    ShapCar car;
    CheckBox checkBox;
    TextView text_add;
    HashMap<Integer,Boolean> map;
    boolean ischeckbox = false ;
    ImageButton button,good_shopcar;
    Button goods_car_delete,goods_car_count;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                msg.obj=list;
            }
            /*
            这个时候你的集合是空数据，你重新更新下适配器，将把新的数据布置上去
             */
            //初始化全选按钮
            car.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopingcart, null);
        phone=getActivity().getSharedPreferences("login_user_im", Context.MODE_PRIVATE).getString("phone",null);
        NetConnection.RequestShopCar(getContext(), "http://192.168.191.1:8080/Eshop/shopingcart",phone,this);
        initID(view);
        return view;
    }

    private void initID(View view) {
        good_shopcar=(ImageButton)view.findViewById(R.id.goods_shopcar);
        good_shopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AllOrderActivity.class));
            }
        });

        button=(ImageButton)view.findViewById(R.id.Shoping_cart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.Shoping_cart){
                   getActivity().finish();
                }
            }
        });

        goods_car_delete=(Button)view.findViewById(R.id.goods_car_delete);
        goods_car_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<map.size();i++){
                    list.remove(i);
                }
                car.notifyDataSetChanged();
            }
        });

        goods_car_count=(Button)view.findViewById(R.id.goods_car_count);
        goods_car_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        listView=(ListView)view.findViewById(R.id.goods_shopcar_list);

        checkBox=(CheckBox)view.findViewById(R.id.goods_shapcar_check);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (map == null) return;
                if(!ischeckbox){
                    car.ischecke();
                    ischeckbox=true;
                }else{
                    car.unIscheck();
                    ischeckbox=false;
                }
            }
        });
        text_add=(TextView)view.findViewById(R.id.goods_shopcar_add);
        list=new ArrayList<>();
        car=new ShapCar();
        listView.setAdapter(car);
    }

    @Override
    public void succeseful(String str) {
        Gson gson;
        try {
            JSONObject object=new JSONObject(str);
            if(object.getString("status").equals(App.STATUS_SUCCESS)){
                gson=new Gson();
                map=new HashMap<>();
                list = gson.fromJson(object.getString("data"), new TypeToken<ArrayList<Cart>>(){}.getType());
                Message message=new Message();
                message.obj=list;
                message.what=0;
                if(list==null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),"空的购物车",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                handler.sendMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loser(String str) {

    }

    class ShapCar extends BaseAdapter{

        public ShapCar(){

        }

        @Override
        public int getCount() {
            return list.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if(convertView == null){
                convertView=LayoutInflater.from(getContext()).inflate(R.layout.goos_shap_layout,null);
                viewHolder=new ViewHolder();
                viewHolder.text_name=(TextView)convertView.findViewById(R.id.shap_layout_name);
                viewHolder.text_id=(TextView)convertView.findViewById(R.id.shap_layout_id);
                viewHolder.text_price=(TextView)convertView.findViewById(R.id.shap_layout_price);
                viewHolder.text_count=(TextView)convertView.findViewById(R.id.shap_layout_count);
                viewHolder.text_total=(TextView)convertView.findViewById(R.id.shap_layou_total);
                viewHolder.imageView=(ImageView)convertView.findViewById(R.id.shap_layout_image);
                viewHolder.box=(CheckBox)convertView.findViewById(R.id.shap_layout_btn);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)convertView.getTag();
            }
            String[] str=list.get(position).getGoods().getImage_path().split(",");
            ImageLoader.getInstance().displayImage("http://192.168.191.1:8080/Eshop/images/" + str[0] + ".jpg"
                    , viewHolder.imageView);
            viewHolder.text_name.setText("商品名字:" + list.get(position).getGoods().getName());
            viewHolder.text_id.setText("商品编号：" + list.get(position).getGoods().getId());
            viewHolder.text_price.setText("价格：" + list.get(position).getGoods().getPrice());
            viewHolder.text_count.setText("数量：" + list.get(position).getCount());
            viewHolder.text_total.setText("小计：" + (list.get(position).getGoods().getPrice() *
                    list.get(position).getCount()) + "元");
            viewHolder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        map.remove(position);
                        map.put(position, true);
                    } else {
                        map.remove(position);
                        map.put(position, false);
                    }
                    total();//每一次checkBox变化的时候都去统计下钱
                    ckCheckBoxStatus();
                }
            });
            /**
             * 如果此映射包含指定的包含关系将返回true,
             */
            if (map.containsKey(position)) {
                viewHolder.box.setChecked(map.get(position));
            } else {
                viewHolder.box.setChecked(false);
            }
            return convertView;
        }

        private void ckCheckBoxStatus() {
            Set<Integer> set = map.keySet();
            if (set.isEmpty()) return;
            boolean isTrue = true;
            for (int i : set) {

                if (!map.get(i)) {
                    isTrue = false;
                    break;
                }
            }
            if (isTrue) {
                checkBox.setChecked(true);
                ischeckbox = true;
            } else {
                checkBox.setChecked(false);
                ischeckbox = false;
            }
        }

        private void total() {
            //map中为true的值来找到对应的key，这个key就是checkbox中选中的那项，有了这个key就可以去list中拿到price
            float sum = 0;
            Set<Integer> set = map.keySet();
            for (int i : set) {
                if (map.get(i)) {
                    sum += list.get(i).getGoods().getPrice() * list.get(i).getCount();
                }
            }
            text_add.setText(sum + "元");
        }

        public void ischecke(){
            map.clear();
            for (int i = 0; i < list.size(); i++) {
                map.put(i, true);
            }
            notifyDataSetChanged();
        }

        public void unIscheck() {
            map.clear();
            for (int i = 0; i < list.size(); i++) {
                map.put(i, false);
            }
            notifyDataSetChanged();
        }

        class ViewHolder{
            TextView text_name;
            TextView text_price;
            TextView text_id;
            TextView text_count;
            TextView text_total;
            ImageView imageView;
            CheckBox box;
        }
    }
}

