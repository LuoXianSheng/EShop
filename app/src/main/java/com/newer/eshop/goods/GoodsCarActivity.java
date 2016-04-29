package com.newer.eshop.goods;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.account.LoginActivity;
import com.newer.eshop.bean.Address;
import com.newer.eshop.bean.Cart;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GoodsCarActivity extends AppCompatActivity implements HttpDataListener{

    ListView listView;
    ArrayList<Cart> list;
    CheckBox checkBox;
    HashMap<Integer, Boolean> map;
    TextView shopcar_count;
    ShaoMyadapter shaoMyadapter;
    String phone;
    boolean isCheckAll = false;//判断全选状态


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            checkBox.setChecked(false);//初始化全选按钮
            shopcar_count.setText(0.0 + "元");
            shaoMyadapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_car);
        initID();
    }

    /**
     * 初始化ID
     */
    private void initID() {
        listView=(ListView)findViewById(R.id.goods_shopcar_list);
        list = new ArrayList<>();
        shaoMyadapter=new ShaoMyadapter();
        listView.setAdapter(shaoMyadapter);
        checkBox=(CheckBox)findViewById(R.id.goods_shapcar_check);
        shopcar_count=(TextView)findViewById(R.id.goods_shopcar_count);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GoodsCarActivity.this, "我是:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        UserLogin();
    }


    /**
     * 商品适配器
     */
     class ShaoMyadapter extends BaseAdapter{


       public ShaoMyadapter(){
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
         public View getView(final int position, View view, ViewGroup parent) {
             final ViewHolder viewHolder;
             if(view==null){
                 view = LayoutInflater.from(GoodsCarActivity.this).inflate(R.layout.goos_shap_layout, null);
                 viewHolder=new ViewHolder();
                 viewHolder.imageView=(ImageView)view.findViewById(R.id.shap_layout_image);
                 viewHolder.textView1=(TextView)view.findViewById(R.id.shap_layout_name);
                 viewHolder.textView2=(TextView)view.findViewById(R.id.shap_layout_id);
                 viewHolder.textView3=(TextView)view.findViewById(R.id.shap_layout_price);
                 viewHolder.box=(CheckBox)view.findViewById(R.id.shap_layout_btn);
                 viewHolder.textView4= (TextView) view.findViewById(R.id.shap_layout_count);
                 viewHolder.shap_layou_total= (TextView) view.findViewById(R.id.shap_layou_total);
                 view.setTag(viewHolder);
             }else{
                 viewHolder=(ViewHolder)view.getTag();
             }
             String[] str=list.get(position).getGoods().getImage_path().split(",");
             ImageLoader.getInstance().displayImage("http://192.168.191.1:8080/Eshop/images/" + str[0] + ".jpg"
                     , viewHolder.imageView);
             viewHolder.textView1.setText("商品名字：" + list.get(position).getGoods().getName());
             viewHolder.textView2.setText("商品编号：" + list.get(position).getGoods().getId());
             viewHolder.textView3.setText("商品价格：" + list.get(position).getGoods().getPrice());
             viewHolder.textView4.setText("数量：" + list.get(position).getCount());
             viewHolder.shap_layou_total.setText("小计：" + (list.get(position).getGoods().getPrice() *
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
             return view;
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
                isCheckAll = true;
            } else {
                checkBox.setChecked(false);
                isCheckAll = false;
            }
        }

        private void total() {
            //map中为true的值来找到对应的key，这个key就是checkbox中选中的那项，有了这个key就可以去list中拿到price
            float sum = 0;
            Set<Integer> set = map.keySet();
            for (Integer i : set) {
                if (map.get(i)) {
                    sum += list.get(i).getGoods().getPrice() * list.get(i).getCount();
                }
            }
            shopcar_count.setText(sum + "元");
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
        /**
         * 保存所有控件的ID
         */
        class ViewHolder{
           ImageView imageView;
            TextView textView1;
            TextView textView2;
            TextView textView3;
            TextView textView4;
            TextView shap_layou_total;
             CheckBox box;
        }
     }

    /**
     * 请求成功
     * @param str
     */
    @Override
    public void succeseful(String str) {
        if (str.length() <= 0) return;
        try {
            JSONObject object = new JSONObject(str);
            String status = object.getString("status");
            map = new HashMap<>();
            if (status.equals(App.STATUS_SUCCESS)) {
                Gson gson = new Gson();
                list = new ArrayList<>();
                list = gson.fromJson(object.getString("data"), new TypeToken<ArrayList<Cart>>() {}.getType());
                for (int i = 0; i < list.size(); i++) {
                    map.put(i, false);
                }
            } else if (status.equals(App.STATUS_LOSE)) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GoodsCarActivity.this, "空的购物车！", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                map.clear();
                list.clear();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        NetConnection.RequestShopCar(GoodsCarActivity.this, "http://192.168.191.1:8080/Eshop/shopingcart",
                                phone, GoodsCarActivity.this);
                    }
                });
            }
            handler.sendEmptyMessage(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 请求失败
     * @param str
     */
    @Override
    public void loser(String str) {

    }

    /**
     * 判断当前是那个用户登入的,否则就将物品加入购物车
     */
    public void UserLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_user_im", MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", null);
        String name = sharedPreferences.getString("name", null);
        if (phone == null && name == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请先登入!")
                    .setMessage("温馨提示：请先登入!")
                    .setIcon(R.mipmap.ic_launcher)
                    .setNeutralButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent it = new Intent(GoodsCarActivity.this, LoginActivity.class);
                            startActivityForResult(it, 1);
                        }
                    })
                    .show();
        } else {
            NetConnection.RequestShopCar(GoodsCarActivity.this, "http://192.168.191.1:8080/Eshop/shopingcart",
                    phone, this);
        }
    }
    /**
     * 登入成功了，你要去请求购物车的数据
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences sharedPreferences = getSharedPreferences("login_user_im", MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", null);
        list.clear();
        shaoMyadapter.notifyDataSetChanged();
        NetConnection.RequestShopCar(GoodsCarActivity.this, "http://192.168.191.1:8080/Eshop/shopingcart", phone, this);
    }

    /**
     * 点击返回分类的首页
     * @param view
     */
    public void backs(View view){
        finish();
    }
    /**
     * checkBox的点击事件
     */
     public void check_text(View view){
         if (map == null) return;
         if (!isCheckAll) {
             shaoMyadapter.ischecke();
             isCheckAll = true;
         } else {
             shaoMyadapter.unIscheck();//取消全选
             isCheckAll = false;
         }

     }

    //删除商品
    public void delete(View v) {
        if (checkBox()) {
            Set<Integer> set = map.keySet();
            if (set.isEmpty()) return;
            SharedPreferences preferences = getSharedPreferences("login_user_im", MODE_PRIVATE);
            String phone = preferences.getString("phone", null);
            String url = App.SERVICE_URL + "/deletegoods";
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (Integer i : set) {
                if (map.get(i)) {
                    sb.append("{\"goodsid\":").append(list.get(i).getGoods().getId()).append("},");
                }
            }
            String data = sb.substring(0, sb.lastIndexOf(",")) + "]";
            NetConnection.deleteGoods(GoodsCarActivity.this, url, phone, data, this);
        }
    }

    //结算
    public void result(View v) {
        checkUser();
        if (checkBox()) {
            StringBuilder goodsids = new StringBuilder();
            StringBuilder counts = new StringBuilder();
            Set<Integer> set = map.keySet();
            for (Integer i : set) {
                if (map.get(i)) {
                    goodsids.append(list.get(i).getGoods().getId()).append(",");
                    counts.append(list.get(i).getCount()).append(",");
                }
            }
            Intent intent = new Intent(GoodsCarActivity.this, GoodsBuy.class);
            intent.putExtra("goodsids", goodsids.toString());
            intent.putExtra("counts", counts.toString());
            startActivityForResult(intent, 1);
        }
    }

    /**
     * 检测checkBox的选择状态，如果没有一个选择则删除、购买都不进行操作
     * @return
     */
    public boolean checkBox() {
        Set<Integer> set = map.keySet();
        if (set.isEmpty()) return false;
        for (int i : set) {
            if (map.get(i)) {

                return true;
            }
        }
        return false;
    }

    /**
     * 检测用户是否登录
     */
    public void checkUser() {
        SharedPreferences preferences = getSharedPreferences("login_user_im", MODE_PRIVATE);
        String token = preferences.getString("Mytoken", "");
        if ("".equals(token)) {
            startActivity(new Intent(GoodsCarActivity.this, LoginActivity.class));
        }
    }
}
