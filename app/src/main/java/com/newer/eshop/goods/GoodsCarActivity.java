package com.newer.eshop.goods;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.newer.eshop.R;
import com.newer.eshop.account.LoginActivity;
import com.newer.eshop.bean.Goods;
import com.newer.eshop.classify.ClassifyResultActivity;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

public class GoodsCarActivity extends AppCompatActivity implements HttpDataListener{

    ListView listView;
    ArrayList<Goods> list;
    CheckBox checkBox;
    HashMap<Integer,Boolean> map;
    TextView shopcar_count;
    ArrayList<Float> text_count;
    ShaoMyadapter shaoMyadapter;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           if(msg.what==0){
              shaoMyadapter=new ShaoMyadapter(list,GoodsCarActivity.this);
               listView.setAdapter(shaoMyadapter);

           }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_car);
        initID();
        UserLogin();
        map=new HashMap<>();
        text_count=new ArrayList<>();
    }

    /**
     * 初始化ID
     */
    private void initID() {
        listView=(ListView)findViewById(R.id.goods_shopcar_list);
        checkBox=(CheckBox)findViewById(R.id.goods_shapcar_check);
        shopcar_count=(TextView)findViewById(R.id.goods_shopcar_count);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GoodsCarActivity.this, "我是:" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 商品适配器
     */
     class ShaoMyadapter extends BaseAdapter{

        //商品的集合
        ArrayList<Goods> list;
        Context context;

       public ShaoMyadapter(ArrayList<Goods> list,Context context){
           this.list=list;
           this.context=context;
       }

         @Override
         public int getCount() {
             if(list == null){
                 return 0;
             }else{
                 return list.size();
             }
         }

         @Override
         public Object getItem(int position) {
             return null;
         }

         @Override
         public long getItemId(int position) {
             return 0;
         }

         @Override
         public View getView(final int position, View convertView, ViewGroup parent) {
             final View view;
             final ViewHolder viewHolder;
             if(convertView==null){
                 view=View.inflate(context,R.layout.goos_shap_layout,null);
                 viewHolder=new ViewHolder();
                 viewHolder.imageView=(ImageView)view.findViewById(R.id.shap_layout_image);
                 viewHolder.textView1=(TextView)view.findViewById(R.id.shap_layout_name);
                 viewHolder.textView2=(TextView)view.findViewById(R.id.shap_layout_id);
                 viewHolder.textView3=(TextView)view.findViewById(R.id.shap_layout_price);
                 viewHolder.box=(CheckBox)view.findViewById(R.id.shap_layout_btn);
                 view.setTag(viewHolder);
             }else{
                 view=convertView;
                 viewHolder=(ViewHolder)view.getTag();
             }
             String[] str=list.get(position).getImage_path().split(",");
             ImageLoader.getInstance().displayImage("http://192.168.191.1:8080/Eshop/images/" + str[0] + ".jpg"
                     , viewHolder.imageView);
             viewHolder.textView1.setText("商品名字:" + list.get(position).getName());
             viewHolder.textView2.setText("商品编号:" + list.get(position).getId());
             viewHolder.textView3.setText("商品价格:" + list.get(position).getPrice());
             viewHolder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     if (isChecked == true) {
                         map.remove(position);
                         map.put(position, true);
                         viewHolder.box.setChecked(map.get(position));
                         text_count.add(list.get(position).getPrice());
                         float s=0;
                         for(int i=0;i<text_count.size();i++){
                            s+=text_count.get(i);
                         }
                         shopcar_count.setText(s+"元");
                     } else {
                         map.remove(position);
                         map.put(position, false);
                         viewHolder.box.setChecked(map.get(position));
                         text_count.remove(list.get(position).getPrice());
                         float s=0;
                         for(int i=0;i<text_count.size();i++){
                             s+=text_count.get(i);
                         }
                         shopcar_count.setText(s+"元");
                     }
                 }
             });
             /**
              * 如果此映射包含指定的包含关系将返回true,
              */
             if(map.containsKey(position)){
                viewHolder.box.setChecked(map.get(position));
             }else{
                 viewHolder.box.setChecked(false);
             }
                 return view;
         }

       public void ischecke(){

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
             CheckBox box;
        }
     }
    /**
     * 请求成功
     * @param str
     */
    @Override
    public void succeseful(String str) {
        Gson gson=new Gson();
        list=new ArrayList<>();
        list=gson.fromJson(str,new TypeToken<ArrayList<Goods>>(){}.getType());
        Message message=new Message();
        message.obj=list;
        message.what=0;
        handler.sendMessage(message);
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
    public void UserLogin(){
        SharedPreferences sharedPreferences=getSharedPreferences("login_user_im", MODE_PRIVATE);
        String str=sharedPreferences.getString("phone", null);
        String name=sharedPreferences.getString("name",null);
        if(str==null && name==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("请先登入!")
                    .setMessage("温馨提示：请先登入!")
                    .setIcon(R.mipmap.ic_launcher)
                    .setNeutralButton("取消",null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent it = new Intent(GoodsCarActivity.this, LoginActivity.class);
                            startActivityForResult(it, 1);
                        }
                    })
                    .show();
        }else{
            NetConnection.RequestShopCar(GoodsCarActivity.this, "http://192.168.191.1:8080/Eshop/shopingcart", str, this);
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

        SharedPreferences sharedPreferences=getSharedPreferences("login_user_im", MODE_PRIVATE);
        String str=sharedPreferences.getString("phone",null);

        if(requestCode==1 && resultCode==RESULT_OK){
            NetConnection.RequestShopCar(GoodsCarActivity.this, "http://192.168.191.1:8080/Eshop/shopingcart", str, this);
        }else{
            Toast.makeText(GoodsCarActivity.this, "请先登入!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击返回分类的首页
     * @param view
     */
    public void backs(View view){
        Intent intent=new Intent();
        intent.setClass(GoodsCarActivity.this, ClassifyResultActivity.class);
        startActivity(intent);
    }
    /**
     * checkBox的点击事件
     */
     public void check_text(View view){
        //如果点击了的话就所有的checkbox所有的选项选中，否则就不选中
         if(checkBox.isChecked()){
             System.out.println(map.size());

         }else{
             System.out.println(map.size());
         }
     }
}
